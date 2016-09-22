package bll.interfaces;

import api.model.GroupPushModel;
import common.ServiceException;
import model.Group;

import java.util.List;

public interface IGroupService {
    List<Group> getGroupsOfStudent(int studentId) throws ServiceException;

    Group getGroupById(int id) throws ServiceException;

    void addOrUpdateGroup(GroupPushModel model) throws ServiceException;
    void deleteGroup(int id) throws ServiceException;
}

