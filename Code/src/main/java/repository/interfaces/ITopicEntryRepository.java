package repository.interfaces;

import model.TopicEntry;

import java.util.List;

public interface ITopicEntryRepository {
    List<TopicEntry> getByStudentId(int studentId);
    List<TopicEntry> getByTopicId(int topicId);

    TopicEntry getById(int id);

    void add(TopicEntry entry);
    void delete(TopicEntry entry);
}
