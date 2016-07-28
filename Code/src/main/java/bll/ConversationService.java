package bll;

import api.model.ConversationPushModel;
import bll.interfaces.IConversationService;
import common.*;
import model.Conversation;
import model.Student;
import repository.ConversationRepository;
import repository.StudentRepository;
import repository.interfaces.IConversationRepository;
import repository.interfaces.IStudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConversationService implements IConversationService{
    @Override
    public List<Conversation> getConversationsOfStudent(int studentId) throws ServiceException {
        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()){

            try{
                IConversationRepository conversationRepository = new ConversationRepository(manager.getManager());

                return conversationRepository.getByStudentId(studentId);
            }
            catch(Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public Conversation getConversationByMembers(List<Integer> memberIds) throws ServiceException {
        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()){

            try{
                IConversationRepository conversationRepository = new ConversationRepository(manager.getManager());
                IStudentRepository studentRepository = new StudentRepository(manager.getManager());

                List<Student> members = new ArrayList<>();

                for (int memberId : memberIds){
                    Student member = studentRepository.getById(memberId);

                    if (member == null){
                        throw new ServiceException(ErrorType.MEMBER_NOT_FOUND);
                    }

                    members.add(member);
                }

                return conversationRepository.getByMembers(members);
            }
            catch (Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public Conversation getConversationById(int id) throws ServiceException {
        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()){

            try{
                IConversationRepository conversationRepository = new ConversationRepository(manager.getManager());

                return conversationRepository.getById(id);
            }catch (Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public void addOrUpdateConversation(ConversationPushModel model) throws ServiceException {
        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()){
            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    IConversationRepository conversationRepository = new ConversationRepository(manager.getManager());
                    IStudentRepository studentRepository = new StudentRepository(manager.getManager());

                    if (model.getId() > 0 ){
                        addConversation(model, conversationRepository, studentRepository);
                    }
                    else{
                        updateConversation(model, conversationRepository, studentRepository);
                    }

                    transaction.commit();
                }
                catch (ServiceException exception){
                    transaction.rollback();

                    throw exception;
                }
                catch (Exception exception){
                    transaction.rollback();

                    throw new ServiceException(ErrorType.INTERNAL_ERROR);
                }
            }
        }
    }

    private void addConversation(ConversationPushModel model, IConversationRepository conversationRepository, IStudentRepository studentRepository) throws ServiceException {
        Conversation conversation = new Conversation();

        Student author = studentRepository.getById(model.getAuthorId());

        conversation.setAuthor(author);

        for (int id : model.getMemberIds()){
            Student member = studentRepository.getById(id);

            if (member == null){
                throw new ServiceException(ErrorType.MEMBER_NOT_FOUND);
            }

            conversation.getMembers().add(member);
        }

        if (conversationExists(conversation.getMembers(), conversationRepository)){
            throw new ServiceException(ErrorType.CONVERSATION_ALREADY_EXISTS);
        }

        conversationRepository.add(conversation);
    }

    private void updateConversation(ConversationPushModel model, IConversationRepository conversationRepository, IStudentRepository studentRepository) throws ServiceException {
        Conversation conversation = conversationRepository.getById(model.getId());

        if (conversation == null){
            throw new ServiceException(ErrorType.CONVERSATION_NOT_FOUND);
        }

        Student author = studentRepository.getById(model.getAuthorId());

        if (author == null){
            throw new ServiceException(ErrorType.STUDENT_NOT_FOUND);
        }

        conversation.setAuthor(author);

        List<Student> newMembers = new ArrayList<>();

        for (int id : model.getMemberIds()){
            Student member = studentRepository.getById(id);

            if (member == null){
                throw new ServiceException(ErrorType.MEMBER_NOT_FOUND);
            }

            newMembers.add(member);
        }

        for (Student member : newMembers){
            if (!conversation.getMembers().contains(member)){
                conversation.getMembers().add(member);
            }
        }

        for (Student member : conversation.getMembers()){
            if (!newMembers.contains(member)){
                conversation.getMembers().remove(member);
            }
        }
    }

    private boolean conversationExists(Collection<Student> members, IConversationRepository conversationRepository){

        Conversation existingConversation = conversationRepository.getByMembers(members);

        return existingConversation != null;
    }
}
