package repository;

import model.Topic;
import repository.interfaces.ITopicRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class TopicRepository implements ITopicRepository{

    private EntityManager m_EntityManager;

    public TopicRepository(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public List<Topic> getByGroupId(int groupId) {
        Query query = m_EntityManager.createQuery("select t from Topic t where t.group.id = :groupId");
        query.setParameter("groupId", groupId);

        return query.getResultList();
    }

    @Override
    public Topic getById(int id) {
        return m_EntityManager.find(Topic.class, id);
    }

    @Override
    public void add(Topic topic) {
        m_EntityManager.persist(topic);
    }

    @Override
    public void delete(Topic topic) {
        m_EntityManager.remove(topic);
    }
}
