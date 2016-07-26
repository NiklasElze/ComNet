package api.model;

public class SeminarGroupPushModel {

    private int id;
    private String name;
    private Integer fatherGroupId;

    public SeminarGroupPushModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFatherGroupId() {
        return fatherGroupId;
    }

    public void setFatherGroupId(Integer fatherGroupId) {
        this.fatherGroupId = fatherGroupId;
    }
}
