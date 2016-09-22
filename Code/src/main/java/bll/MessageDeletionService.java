package bll;

import bll.interfaces.IConversationDeletionService;
import bll.interfaces.IMessageDeletionService;
import common.ErrorType;
import common.ServiceException;
import model.Conversation;
import model.Message;
import model.Student;
import repository.MessageRepository;
import repository.interfaces.IMessageRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class MessageDeletionService implements IMessageDeletionService{

    private EntityManager m_EntityManager;

    public MessageDeletionService(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public void deleteMessagesOfStudent(Student student) throws ServiceException {
        try {
            IMessageRepository messageRepository = new MessageRepository(m_EntityManager);
            IConversationDeletionService conversationDeletionService = new ConversationDeletionService(m_EntityManager);

            List<Message> messagesOfStudent = messageRepository.getByStudentId(student.getUserdata().getId());
            List<Conversation> conversationsToDelete = new ArrayList<Conversation>();

            for (Message message : messagesOfStudent) {

                if (!studentIsLastInConversation(message.getConversation(), student)) {
                    message.setSender(null);

                    if (message.getConversation().getMembers().contains(student)){
                        message.getConversation().getMembers().remove(student);
                    }
                } else {
                    if (!conversationsToDelete.contains(message.getConversation())) {
                        conversationsToDelete.add(message.getConversation());
                    }

                    messageRepository.delete(message);
                }
            }

            for (Conversation conversation : conversationsToDelete) {
                conversationDeletionService.deleteConversation(conversation);
            }
        }
        catch (ServiceException exception){
            throw exception;
        }
        catch (Exception exception){
            throw new ServiceException(ErrorType.INTERNAL_ERROR);
        }
    }

    private boolean studentIsLastInConversation(Conversation conversation, Student student){
        return conversation.getMembers().size() == 1 && conversation.getMembers().contains(student);
    }
}
