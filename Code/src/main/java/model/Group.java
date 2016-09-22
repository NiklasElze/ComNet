package model;

import common.JsonService;
import common.interfaces.JsonConvertable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tgroup", schema = "", catalog = "comnetdb")
public class Group implements JsonConvertable{
    private int id;
    private String name;
    private Student creator;
    private Collection<Topic> topics;
    private Collection<Student> members;
    private Collection<Student> administrators;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="seq_gen_group")
    @SequenceGenerator(name="seq_gen_group", sequenceName="entity_seq_group")
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
    @JoinColumns(@JoinColumn(name = "Fk_Creator_Id", referencedColumnName = "Fk_User_Id", nullable = true))
    public Student getCreator() {
        return creator;
    }

    public void setCreator(Student creator) {
        this.creator = creator;
    }

    @OneToMany
    @JoinTable(name = "tgrouptopiclist", catalog = "comnetdb", schema = "", joinColumns = @JoinColumn(name = "Fk_Group_Id", referencedColumnName = "Id", nullable = false), inverseJoinColumns = @JoinColumn(name = "Fk_Topic_Id", referencedColumnName = "Id", nullable = false))
    public Collection<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Collection<Topic> topics) {
        this.topics = topics;
    }

    @ManyToMany
    @JoinTable(name = "tgroupmemberlist", catalog = "comnetdb", schema = "", joinColumns = @JoinColumn(name = "Fk_Group_Id", referencedColumnName = "Id", nullable = false), inverseJoinColumns = @JoinColumn(name = "Fk_Student_Id", referencedColumnName = "Fk_User_Id", nullable = false))
    public Collection<Student> getMembers() {
        return members;
    }

    public void setMembers(Collection<Student> members) {
        this.members = members;
    }

    @ManyToMany
    @JoinTable(name = "tgroupadministrators", catalog = "comnetdb", schema = "", joinColumns = @JoinColumn(name = "Fk_Group_Id", referencedColumnName = "Id", nullable = false), inverseJoinColumns = @JoinColumn(name = "Fk_Admin_Id", referencedColumnName = "Fk_User_Id", nullable = false))
    public Collection<Student> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(Collection<Student> administrators) {
        this.administrators = administrators;
    }

    @Override
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("name", name)
                .add("creator", creator.toJson())
                .add("topics", JsonService.getListAsJsonArray(topics))
                .add("members", JsonService.getListAsJsonArray(members))
                .add("administrators", JsonService.getListAsJsonArray(administrators))
                .build();
    }
}
