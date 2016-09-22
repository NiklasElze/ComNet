package model;

import common.JsonService;
import common.interfaces.JsonConvertable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tseminargroup", schema = "", catalog = "comnetdb")
public class SeminarGroup implements JsonConvertable{
    private int id;
    private String name;
    private SeminarGroup fatherGroup;
    private Collection<Student> students;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="seq_gen_seminargroup")
    @SequenceGenerator(name="seq_gen_seminargroup", sequenceName="entity_seq_seminargroup")
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
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "FatherGroup_Id", referencedColumnName = "Id", nullable = true)
    public SeminarGroup getFatherGroup(){
        return fatherGroup;
    }

    public void setFatherGroup(SeminarGroup seminarGroup){
        fatherGroup = seminarGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeminarGroup that = (SeminarGroup) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "seminarGroup")
    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public JsonObject toJson(){
        JsonObjectBuilder jsonBuilder =
                Json.createObjectBuilder()
                        .add("id", id)
                        .add("name", name);

        if (fatherGroup == null){
            jsonBuilder.add("fatherGroup", JsonValue.NULL);
        }
        else{
            jsonBuilder.add("fatherGroup", fatherGroup.toJson());
        }

        jsonBuilder.add("students", JsonService.getListAsJsonArray(students));

        return jsonBuilder.build();
    }
}
