package repository;

import model.Topic;
import repository.interfaces.ITopicRepository;

import javax.persistence.EntityManager;

public class TopicRepository implements ITopicRepository{

    private EntityManager m_EntityManager;

    public TopicRepository(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public Topic getById(int id) {
        return m_EntityManager.find(Topic.class, id);
    }

    @Override
    public void delete(Topic topic) {
        m_EntityManager.remove(topic);
    }
}
