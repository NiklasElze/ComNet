package api.model;

import common.JsonService;
import model.Privilege;
import model.SeminarGroup;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.util.List;

public class Identity {

    private int id;
    private String firstname;
    private String lastname;
    private String sessionId;
    private SeminarGroup seminarGroup;
    private List<Privilege> privileges;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setSeminarGroup(SeminarGroup seminarGroup) {
        this.seminarGroup = seminarGroup;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public JsonObject toJson(){
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("id", id)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("sessionId", sessionId);

        if (privileges != null){
            jsonBuilder.add("privileges", JsonService.getListAsJsonArray(privileges));
        }
        else{
            jsonBuilder.add("privileges", JsonValue.NULL);
        }

        if (seminarGroup != null){
            jsonBuilder.add("seminarGroup", seminarGroup.toJson());
        }
        else{
            jsonBuilder.add("seminarGroup", JsonValue.NULL);
        }

        return jsonBuilder.build();
    }
}
