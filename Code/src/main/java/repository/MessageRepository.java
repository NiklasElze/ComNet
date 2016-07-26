package repository;

import model.Message;
import repository.interfaces.IMessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class MessageRepository implements IMessageRepository {

    private EntityManager m_EntityManager;

    public MessageRepository(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public Message getById(int id) {
        Query query = m_EntityManager.createQuery("select m from Message m where m.id = :id");
        query.setParameter("id", id);

        return query.getResultList().size() > 0
                ? (Message)query.getResultList().get(0)
                : null;
    }

    @Override
    public List<Message> getByStudentId(int id) {
        Query query = m_EntityManager.createQuery("select m from Message m where m.sender.userdata.id = :senderId");
        query.setParameter("senderId", id);

        return query.getResultList();
    }

    @Override
    public void add(Message message) {
        m_EntityManager.persist(message);
    }

    @Override
    public void delete(Message message) {
        m_EntityManager.remove(message);
    }
}
