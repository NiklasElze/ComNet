package api.model;

import model.User;

import java.security.Principal;

public class CustomPrincipal implements Principal {

    private final User m_AuthorizedUser;

    public CustomPrincipal(User authorizedUser){
        m_AuthorizedUser = authorizedUser;
    }

    @Override
    public String getName() {
        return null;
    }

    public User getAuthorizedUser() {
        return m_AuthorizedUser;
    }
}
