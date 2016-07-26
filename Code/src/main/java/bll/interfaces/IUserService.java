package bll.interfaces;

import api.model.UserData;
import api.model.UserPushModel;
import common.ServiceException;
import model.User;

import java.util.List;

public interface IUserService {

    UserData getUserById(int id) throws ServiceException;

    List<User> getCustodians() throws ServiceException;

    User getUserBySessionId(String sessionId) throws ServiceException;

    void addOrUpdateUser(UserPushModel model) throws ServiceException;
    void deleteUser(int id) throws ServiceException;
}
