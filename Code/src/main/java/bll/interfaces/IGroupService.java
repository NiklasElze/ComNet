package bll.interfaces;

import common.ServiceException;
import model.Group;

import java.util.List;

public interface IGroupService {
    List<Group> getGroupsOfStudent(int studentId) throws ServiceException;

    Group getGroupById(int id) throws ServiceException;

    void addOrUpdateGroup(Group group);
    void deleteGroup(int id);
}

