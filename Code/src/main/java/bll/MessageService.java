package bll;

import api.model.MessagePushModel;
import bll.interfaces.IMessageService;
import common.*;
import model.Conversation;
import model.Message;
import model.Student;
import repository.ConversationRepository;
import repository.MessageRepository;
import repository.StudentRepository;
import repository.interfaces.IConversationRepository;
import repository.interfaces.IMessageRepository;
import repository.interfaces.IStudentRepository;

import java.sql.Timestamp;
import java.util.List;

public class MessageService implements IMessageService{

    @Override
    public List<Message> getMessagesOfConversation(int conversationId) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()) {

            try {
                IMessageRepository messageRepository = new MessageRepository(manager.getUnwrappedManager());

                return messageRepository.getByConversationId(conversationId);
            } catch (Exception exception) {
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public void addMessage(MessagePushModel model) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){
            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    IMessageRepository messageRepository = new MessageRepository(manager.getUnwrappedManager());
                    IConversationRepository conversationRepository = new ConversationRepository(manager.getUnwrappedManager());
                    IStudentRepository studentRepository = new StudentRepository(manager.getUnwrappedManager());

                    Conversation conversation = conversationRepository.getById(model.getConversationId());

                    if (conversation == null){
                        throw new ServiceException(ErrorType.CONVERSATION_NOT_FOUND);
                    }

                    Student sender = studentRepository.getById(model.getSenderId());

                    if (sender == null){
                        throw new ServiceException(ErrorType.STUDENT_NOT_FOUND);
                    }

                    Message message = new Message();
                    message.setSender(sender);
                    message.setConversation(conversation);
                    message.setText(model.getText());
                    message.setCreateDate(new Timestamp(System.currentTimeMillis()));

                    messageRepository.add(message);
                    transaction.commit();
                }
                catch(ServiceException exception){
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
}
