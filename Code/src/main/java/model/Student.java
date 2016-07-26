package model;

import common.interfaces.JsonConvertable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tstudent", schema = "", catalog = "comnetdb")
public class Student implements Serializable, JsonConvertable{
    private User userdata;
    private SeminarGroup seminarGroup;

    @Id
    @OneToOne
    @JoinColumn(name = "Fk_User_Id", referencedColumnName = "Id", nullable = false)
    public User getUserdata(){
        return userdata;
    }

    public void setUserdata(User userdata){
        this.userdata = userdata;
    }

    @ManyToOne
    @JoinColumn(name = "Fk_SeminarGroup_Id", referencedColumnName = "Id", nullable = false)
    public SeminarGroup getSeminarGroup() {
        return seminarGroup;
    }

    public void setSeminarGroup(SeminarGroup seminarGroup) {
        this.seminarGroup = seminarGroup;
    }

    @Override
    public JsonObject toJson(){
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
                .add("id", userdata.getId())
                .add("firstname", userdata.getFirstname())
                .add("lastname", userdata.getLastname())
                .add("seminargroup", seminarGroupJson)
                .build();
    }
}
