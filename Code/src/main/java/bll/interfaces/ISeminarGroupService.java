package bll.interfaces;

import api.model.SeminarGroupPushModel;
import common.ServiceException;
import model.SeminarGroup;

import java.util.List;

public interface ISeminarGroupService {

    List<SeminarGroup> getContactListOfNewConversation(int currentUserId) throws ServiceException;
    List<SeminarGroup> getContactListOfConversation(int conversationId) throws ServiceException;
    List<SeminarGroup> getSeminarGroupList() throws ServiceException;

    void addOrUpdateSeminarGroup(SeminarGroupPushModel model) throws ServiceException;
    void deleteSeminarGroup(int id) throws ServiceException;
}
