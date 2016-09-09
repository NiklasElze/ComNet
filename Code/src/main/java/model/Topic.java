package model;

import common.JsonService;
import common.interfaces.JsonConvertable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "ttopic", schema = "", catalog = "comnetdb")
public class Topic implements JsonConvertable{
    private int id;
    private Group group;
    private Student creator;
    private Timestamp createDate;
    private Collection<TopicEntry> entries;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="seq_gen_topic")
    @SequenceGenerator(name="seq_gen_topic", sequenceName="entity_seq_topic")
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumns(@JoinColumn(name = "Fk_Group_Id", referencedColumnName = "Id", nullable = false))
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @ManyToOne
    @JoinColumns(@JoinColumn(name = "Fk_Group_Id", referencedColumnName = "Fk_User_Id", nullable = false))
    public Student getCreator() {
        return creator;
    }

    public void setCreator(Student creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "CreateDate")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @OneToMany
    @JoinColumns(@JoinColumn(name = "Fk_Topic_Id", referencedColumnName = "Id", nullable = false))
    public Collection<TopicEntry> getEntries() {
        return entries;
    }

    public void setEntries(Collection<TopicEntry> entries) {
        this.entries = entries;
    }

    @Override
    public JsonObject toJson() {
        JsonObject groupObject =
                Json.createObjectBuilder()
                        .add("id", group.getId())
                        .add("name", group.getName())
                        .build();

        JsonObject topicObject =
                Json.createObjectBuilder()
                        .add("id", id)
                        .add("group", groupObject)
                        .add("creator", creator.toJson())
                        .add("createDate", createDate.getTime())
                        .add("entries", JsonService.getListAsJsonArray(entries))
                        .build();

        return topicObject;
    }
}
