package bll;

import bll.interfaces.ITopicDeletionService;
import model.Topic;
import repository.TopicRepository;
import repository.interfaces.ITopicRepository;

import javax.persistence.EntityManager;

public class TopicDeletionService implements ITopicDeletionService{

    private EntityManager m_EntityManager;

    public TopicDeletionService(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public void deleteTopic(Topic topic) {
        ITopicRepository topicRepository = new TopicRepository(m_EntityManager);
        TopicEntryDeletionService topicEntryDeletionService = new TopicEntryDeletionService(m_EntityManager);

        topicEntryDeletionService.deleteEntriesOfTopic(topic);
        topicRepository.delete(topic);
    }
}
