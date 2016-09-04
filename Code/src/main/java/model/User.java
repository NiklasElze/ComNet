package model;

import common.JsonService;
import common.interfaces.JsonConvertable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tuser", schema = "", catalog = "comnetdb")
public class User implements JsonConvertable{
    private int id;
    private String firstname;
    private String lastname;
    private Collection<Privilege> privileges;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="seq_gen_user")
    @SequenceGenerator(name="seq_gen_user", sequenceName="entity_seq_user")
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "Lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) return false;
        if (lastname != null ? !lastname.equals(user.lastname) : user.lastname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    @ManyToMany
    @JoinTable(name = "tuserprivilege", catalog = "comnetdb", schema = "", joinColumns = @JoinColumn(name = "Fk_User_Id", referencedColumnName = "Id", nullable = false), inverseJoinColumns = @JoinColumn(name = "Fk_Privilege_Id", referencedColumnName = "Id", nullable = false))
    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("id", id)
                .add("firstname", firstname)
                .add("lastname", lastname);

        if (privileges != null){
            builder.add("privileges", JsonService.getListAsJsonArray(privileges));
        }
        else{
            builder.add("privileges", "[]");
        }

        return builder.build();
    }
}
