package repository;

import model.TopicEntry;
import repository.interfaces.ITopicEntryRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class TopicEntryRepository implements ITopicEntryRepository{

    private EntityManager m_EntityManager;

    public TopicEntryRepository(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public List<TopicEntry> getByStudentId(int studentId) {
        Query query = m_EntityManager.createQuery("select te from TopicEntry te where te.sender.id = :studentId");
        query.setParameter("studentId", studentId);

        return query.getResultList();
    }

    @Override
    public List<TopicEntry> getByTopicId(int topicId) {
        Query query = m_EntityManager.createQuery("select te from TopicEntry te where te.topic.id = :topicId");
        query.setParameter("topicId", topicId);

        return query.getResultList();
    }

    @Override
    public TopicEntry getById(int id) {
        return m_EntityManager.find(TopicEntry.class, id);
    }

    @Override
    public void add(TopicEntry entry) {
        m_EntityManager.persist(entry);
    }

    @Override
    public void delete(TopicEntry entry) {
        m_EntityManager.remove(entry);
    }
}
