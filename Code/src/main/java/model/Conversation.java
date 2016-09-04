package model;

import common.JsonService;
import common.interfaces.JsonConvertable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tconversation", schema = "", catalog = "comnetdb")
public class Conversation implements JsonConvertable{
    private int id;
    private Collection<Student> members;
    private Collection<Message> messages;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="seq_gen_conversation")
    @SequenceGenerator(name="seq_gen_conversation", sequenceName="entity_seq_conversation")
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conversation that = (Conversation) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @ManyToMany
    @JoinTable(name = "tmemberlist", catalog = "comnetdb", schema = "", joinColumns = @JoinColumn(name = "Fk_Conversation_Id", referencedColumnName = "Id", nullable = false), inverseJoinColumns = @JoinColumn(name = "Fk_Student_Id", referencedColumnName = "Fk_User_Id", nullable = false))
    public Collection<Student> getMembers() {
        return members;
    }

    public void setMembers(Collection<Student> members) {
        this.members = members;
    }

    @OneToMany
    @JoinTable(name = "tmessagelist", catalog = "comnetdb", schema = "", joinColumns = @JoinColumn(name = "Fk_Conversation_Id", referencedColumnName = "Id", nullable = false), inverseJoinColumns = @JoinColumn(name = "Fk_Message_Id", referencedColumnName = "Id", nullable = false))
    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    @Override
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("members", JsonService.getListAsJsonArray(members))
                .add("messages", JsonService.getListAsJsonArray(messages))
                .build();
    }
}
