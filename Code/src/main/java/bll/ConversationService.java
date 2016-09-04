package bll;

import api.model.ConversationPushModel;
import api.model.Member;
import bll.interfaces.IConversationDeletionService;
import bll.interfaces.IConversationService;
import common.*;
import model.Conversation;
import model.Message;
import model.Student;
import repository.ConversationRepository;
import repository.StudentRepository;
import repository.interfaces.IConversationRepository;
import repository.interfaces.IStudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ConversationService implements IConversationService{
    @Override
    public List<Conversation> getConversationsOfStudent(int studentId) throws ServiceException {
        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()){

            try{
                IConversationRepository conversationRepository = new ConversationRepository(manager.getManager());

                List<Conversation> conversations = conversationRepository.getByStudentId(studentId);

                for (Conversation conversation : conversations){
                    Collections.sort((List<Message>)conversation.getMessages());
                }

                return conversations;
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

                Conversation conversation = conversationRepository.getByMembers(members);

                if (conversation == null){
                    conversation = new Conversation();
                    conversation.setId(-1);
                    conversation.setMembers(new ArrayList<Student>());
                    conversation.setMessages(new ArrayList<Message>());
                }

                Collections.sort((List<Message>)conversation.getMessages());

                return conversation;
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

                Conversation conversation = conversationRepository.getById(id);

                Collections.sort((List<Message>)conversation.getMessages());

                return conversation;
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
                        updateConversation(model, conversationRepository, studentRepository);
                    }
                    else{
                        addConversation(model, conversationRepository, studentRepository);
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

    @Override
    public void addMembersToConversation(int id, List<Member> members) throws ServiceException {
        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()){
            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    IConversationRepository conversationRepository = new ConversationRepository(manager.getManager());
                    IStudentRepository studentRepository = new StudentRepository(manager.getManager());

                    Conversation conversation = conversationRepository.getById(id);

                    if (conversation == null){
                        throw new ServiceException(ErrorType.CONVERSATION_NOT_FOUND);
                    }

                    for (Member newMember: members){
                        Student member = studentRepository.getById(newMember.getId());

                        if (member == null){
                            throw new ServiceException(ErrorType.STUDENT_NOT_FOUND);
                        }

                        if (conversation.getMembers().contains(member)){
                            throw new ServiceException(ErrorType.CONVERSATION_ALREADY_CONTAINS_MEMBER);
                        }

                        conversation.getMembers().add(member);
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

    @Override
    public void removeStudentFromConversation(int conversationId, int currentUserId) throws ServiceException {
        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()){
            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    IStudentRepository studentRepository = new StudentRepository(manager.getManager());
                    IConversationRepository conversationRepository = new ConversationRepository(manager.getManager());
                    IConversationDeletionService conversationDeletionService = new ConversationDeletionService(manager.getManager());

                    Student currentUser = studentRepository.getById(currentUserId);

                    if (currentUser == null){
                        throw new ServiceException(ErrorType.STUDENT_NOT_FOUND);
                    }

                    Conversation conversation = conversationRepository.getById(conversationId);

                    if (conversation == null){
                        throw new ServiceException(ErrorType.CONVERSATION_NOT_FOUND);
                    }

                    conversation.getMembers().remove(currentUser);

                    if (conversation.getMembers().size() <= 1){
                        conversationDeletionService.deleteConversation(conversation);
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

        ArrayList<Student> members = new ArrayList<>();

        for (int id : model.getMemberIds()){
            Student member = studentRepository.getById(id);

            if (member == null){
                throw new ServiceException(ErrorType.STUDENT_NOT_FOUND);
            }

            members.add(member);
        }

        conversation.setMembers(members);

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
