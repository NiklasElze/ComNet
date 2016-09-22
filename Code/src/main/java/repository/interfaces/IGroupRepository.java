package repository.interfaces;

import model.Group;

import java.util.List;

public interface IGroupRepository {
    List<Group> getByStudentId(int id);

    Group getById(int id);

    void add(Group group);
    void delete(Group group);
}
