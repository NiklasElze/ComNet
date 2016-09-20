package bll.interfaces;

import api.model.TopicEntryPushModel;
import common.ServiceException;
import model.TopicEntry;

import java.util.List;

public interface ITopicEntryService {
    List<TopicEntry> getTopicEntriesOfTopic(int topicId) throws ServiceException;

    void addTopicEntry(TopicEntryPushModel model) throws ServiceException;
}
