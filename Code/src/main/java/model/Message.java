package model;

import common.interfaces.JsonConvertable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tmessage", schema = "", catalog = "comnetdb")
public class Message implements JsonConvertable, Comparable<Message>{
    private int id;
    private String text;
    private Timestamp createDate;
    private Student sender;
    private Conversation conversation;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="seq_gen_message")
    @SequenceGenerator(name="seq_gen_message", sequenceName="entity_seq_message")
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        if (createDate != null ? !createDate.equals(message.createDate) : message.createDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumns(@JoinColumn(name = "Fk_Sender_Id", referencedColumnName = "Fk_User_Id", nullable = false))
    public Student getSender() {
        return sender;
    }

    public void setSender(Student sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JoinTable(name = "tmessagelist", catalog = "comnetdb", schema = "", joinColumns = @JoinColumn(name = "Fk_Message_Id", referencedColumnName = "Id", nullable = false), inverseJoinColumns = @JoinColumn(name = "Fk_Conversation_Id", referencedColumnName = "Id", nullable = false))
    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("text", text)
                .add("createDate", createDate.getTime())
                .add("sender", sender.toJson())
                .build();
    }

    @Override
    public int compareTo(Message that) {
        if (this.getId() < that.getId()){
            return -1;
        }

        if (this.getId() > that.getId()){
            return 1;
        }

        return 0;
    }
}
