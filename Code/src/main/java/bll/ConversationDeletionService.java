package bll;

import bll.interfaces.IConversationDeletionService;
import common.ErrorType;
import model.Conversation;
import model.Message;
import repository.ConversationRepository;
import repository.MessageRepository;
import repository.interfaces.IConversationRepository;
import repository.interfaces.IMessageRepository;

import javax.persistence.EntityManager;

public class ConversationDeletionService implements IConversationDeletionService{

    private EntityManager m_EntityManager;

    public ConversationDeletionService(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public ErrorType deleteConversation(Conversation conversation) {

        IMessageRepository messageRepository = new MessageRepository(m_EntityManager);
        IConversationRepository conversationRepository = new ConversationRepository(m_EntityManager);

        for (Message message : conversation.getMessages()){
            messageRepository.delete(message);
        }

        conversationRepository.delete(conversation);

        return ErrorType.NO_ERROR;
    }
}
