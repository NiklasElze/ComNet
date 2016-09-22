package model;

import common.interfaces.JsonConvertable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;

@Entity
@Table(name = "tprivilege", schema = "", catalog = "comnetdb")
public class Privilege implements JsonConvertable{
    private int id;
    private String name;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="seq_gen_privilege")
    @SequenceGenerator(name="seq_gen_privilege", sequenceName="entity_seq_privilege")
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }

    @Override
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("name", name)
                .build();
    }
}
