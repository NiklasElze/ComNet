package api.model;

import java.util.Collection;

public class UserPushModel {

    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private boolean accesDenied;
    private Collection<Integer> privilegeIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isAccesDenied() {
        return accesDenied;
    }

    public void setAccesDenied(boolean accesDenied) {
        this.accesDenied = accesDenied;
    }

    public Collection<Integer> getPrivilegeIds() {
        return privilegeIds;
    }

    public void setPrivilegeIds(Collection<Integer> privilegeIds) {
        this.privilegeIds = privilegeIds;
    }
}
