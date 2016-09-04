package api.service;

import api.model.Role;
import common.ErrorType;
import common.ServiceException;
import model.Privilege;
import model.User;

public class SecurityService {

    public static void authorizeUser(Role[] allowedRoles, User authenticatedUser) throws ServiceException {
        boolean isAuthorized = false;

        for (Role role : allowedRoles){
            for (Privilege privilege : authenticatedUser.getPrivileges()){
                if (role.getId() == privilege.getId()){
                    isAuthorized = true;
                    break;
                }
            }
        }

        if (!isAuthorized){
            throw new ServiceException(ErrorType.UNAUTHORIZED_ACTION);
        }
    }
}
