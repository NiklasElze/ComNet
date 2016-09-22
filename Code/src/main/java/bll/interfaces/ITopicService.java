package bll.interfaces;

import api.model.TopicPushModel;
import common.ServiceException;
import model.Topic;

import java.util.List;

public interface ITopicService {
    List<Topic> getTopicsOfGroup(int groupId) throws ServiceException;

    Topic getTopicById(int id) throws ServiceException;

    void addOrUpdateTopic(TopicPushModel model) throws ServiceException;
    void deleteTopic(int topicId) throws ServiceException;
}
