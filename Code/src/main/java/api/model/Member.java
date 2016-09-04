package api.model;

import model.SeminarGroup;

public class Member {

    private int id;
    private String firstname;
    private String lastname;
    private SeminarGroup seminargroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public SeminarGroup getSeminargroup() {
        return seminargroup;
    }

    public void setSeminargroup(SeminarGroup seminargroup) {
        this.seminargroup = seminargroup;
    }
}
