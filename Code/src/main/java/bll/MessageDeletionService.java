package bll;

import bll.interfaces.IConversationDeletionService;
import bll.interfaces.IMessageDeletionService;
import common.ErrorType;
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
    public ErrorType deleteMessagesOfStudent(Student student) {
        IMessageRepository messageRepository = new MessageRepository(m_EntityManager);
        IConversationDeletionService conversationDeletionService = new ConversationDeletionService(m_EntityManager);

        List<Message> messagesOfStudent = messageRepository.getByStudentId(student.getUserdata().getId());
        List<Conversation> conversationsToDelete = new ArrayList<Conversation>();

        for (Message message : messagesOfStudent){

            if (messageBelongsToGroupConversation(message)){
                setSenderToDeleted(message);
            }
            else{
                if (!conversationsToDelete.contains(message.getConversation())){
                    conversationsToDelete.add(message.getConversation());
                }
            }
        }

        for (Conversation conversation : conversationsToDelete){
            conversationDeletionService.deleteConversation(conversation);
        }

        return ErrorType.NO_ERROR;
    }

    private boolean messageBelongsToGroupConversation(Message message){
        return message.getConversation().getMembers().size() > 2;
    }

    private void setSenderToDeleted(Message message){
        message.setSender(null);
    }
}
