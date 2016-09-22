package repository.interfaces;

import model.Topic;

import java.util.List;

public interface ITopicRepository {
    List<Topic> getByGroupId(int groupId);

    Topic getById(int id);

    void add(Topic topic);
    void delete(Topic topic);
}
