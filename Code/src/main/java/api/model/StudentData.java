package api.model;

import common.interfaces.JsonConvertable;
import model.SeminarGroup;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class StudentData implements JsonConvertable {
    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private SeminarGroup seminarGroup;
    private boolean accessDenied;

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

    public boolean isAccessDenied() {
        return accessDenied;
    }

    public void setAccessDenied(boolean accessDenied) {
        this.accessDenied = accessDenied;
    }

    public SeminarGroup getSeminarGroup() {
        return seminarGroup;
    }

    public void setSeminarGroup(SeminarGroup seminarGroup) {
        this.seminarGroup = seminarGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder jsonBuilder =
                Json.createObjectBuilder()
                        .add("id", seminarGroup.getId())
                        .add("name", seminarGroup.getName());

        if (seminarGroup.getFatherGroup() == null){
            jsonBuilder.add("fatherGroup", JsonValue.NULL);
        }
        else{
            jsonBuilder.add("fatherGroup", getSeminarGroup().getFatherGroup().toJson());
        }

        JsonObject seminarGroupJson = jsonBuilder.build();

        return Json.createObjectBuilder()
                .add("id", id)
                .add("username", username)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("accessDenied", accessDenied)
                .add("seminarGroup", seminarGroupJson)
                .build();
    }
}
