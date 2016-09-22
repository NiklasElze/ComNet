package model;

import common.interfaces.JsonConvertable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ttopicentry", schema = "", catalog = "comnetdb")
public class TopicEntry implements JsonConvertable{
    private int id;
    private Topic topic;
    private Student sender;
    private String text;
    private Timestamp createDate;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="seq_gen_topicentry")
    @SequenceGenerator(name="seq_gen_topicentry", sequenceName="entity_seq_topicentry")
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinTable(name = "ttopicentrylist", catalog = "comnetdb", schema = "", joinColumns = @JoinColumn(name = "Fk_TopicEntry_Id", referencedColumnName = "Id", nullable = false), inverseJoinColumns = @JoinColumn(name = "Fk_Topic_Id", referencedColumnName = "Id", nullable = false))
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @ManyToOne
    @JoinColumns(@JoinColumn(name = "Fk_Sender_Id", referencedColumnName = "Fk_User_Id", nullable = true))
    public Student getSender() {
        return sender;
    }

    public void setSender(Student sender) {
        this.sender = sender;
    }

    @Basic
    @Column(name = "Text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "CreateDate")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Override
    public JsonObject toJson() {
        JsonObject groupObject =
                Json.createObjectBuilder()
                        .add("id", topic.getGroup().getId())
                        .add("name", topic.getGroup().getName())
                        .add("creator", topic.getGroup().getCreator().toJson())
                        .build();

        JsonObject topicObject =
                Json.createObjectBuilder()
                        .add("id", topic.getId())
                        .add("name", topic.getName())
                        .add("group", groupObject)
                        .add("creator", topic.getCreator().toJson())
                        .add("createDate", topic.getCreateDate().getTime())
                        .build();

        JsonObject entryObject =
                Json.createObjectBuilder()
                        .add("id", id)
                        .add("topic", topicObject)
                        .add("sender", sender.toJson())
                        .add("text", text)
                        .add("createDate", createDate.getTime())
                        .build();

        return entryObject;
    }
}
