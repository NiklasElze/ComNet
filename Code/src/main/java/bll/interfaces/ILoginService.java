package bll.interfaces;

import api.model.Identity;

public interface ILoginService {

    Identity loginUser(String username, String password) throws Exception;

    boolean sessionIdIsValid(String sessionId);
}
