package repository;

import model.Conversation;
import repository.interfaces.IConversationRepository;

import javax.persistence.EntityManager;

/**
 * Created by Niklas on 09.07.2016.
 */
public class ConversationRepository implements IConversationRepository {

    private EntityManager m_EntityManager;

    public ConversationRepository(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public void delete(Conversation conversation) {
        m_EntityManager.remove(conversation);
    }
}
