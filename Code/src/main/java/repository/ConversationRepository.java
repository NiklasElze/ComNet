package repository;

import model.Conversation;
import model.Student;
import repository.interfaces.IConversationRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

public class ConversationRepository implements IConversationRepository {

    private EntityManager m_EntityManager;

    public ConversationRepository(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public List<Conversation> getByStudentId(int id) {
        Query query = m_EntityManager.createQuery("select c from Conversation c where" +
                " (select s from Student s where s.userdata.id = :id) member of c.members");
        query.setParameter("id", id);

        return query.getResultList();
    }

    @Override
    public Conversation getByMembers(Collection<Student> members) {
        Query query = m_EntityManager.createQuery("select c from Conversation c where" +
                " (select count(m.id) from c.members m where m in (:members)) = :count" +
                " and (select count(m.id) from c.members m where m not in (:members)) = 0");
        query.setParameter("members", members);
        query.setParameter("count", (long)members.size());

        return query.getResultList().size() > 0
                ? (Conversation)query.getResultList().get(0)
                : null;
    }

    @Override
    public Conversation getById(int id) {
        return m_EntityManager.find(Conversation.class, id);
    }

    @Override
    public void add(Conversation conversation) {
        m_EntityManager.persist(conversation);
    }

    @Override
    public void delete(Conversation conversation) {
        m_EntityManager.remove(conversation);
    }
}
