package api.model;

import common.JsonService;
import common.interfaces.JsonConvertable;
import model.Privilege;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Collection;

public class UserData implements JsonConvertable {

    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private Collection<Privilege> privileges;
    private boolean accessDenied;

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

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public boolean isAccessDenied() {
        return accessDenied;
    }

    public void setAccessDenied(boolean accessDenied) {
        this.accessDenied = accessDenied;
    }

    @Override
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("username", username)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("accessDenied", accessDenied)
                .add("privileges", JsonService.getListAsJsonArray(privileges))
                .build();
    }
}
