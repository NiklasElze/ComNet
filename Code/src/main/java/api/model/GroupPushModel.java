package api.model;

import java.util.List;

public class GroupPushModel {
    private int id;
    private String name;
    private List<Integer> memberIds;
    private List<Integer> administratorIds;

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

    public List<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Integer> memberIds) {
        this.memberIds = memberIds;
    }

    public List<Integer> getAdministratorIds() {
        return administratorIds;
    }

    public void setAdministratorIds(List<Integer> administratorIds) {
        this.administratorIds = administratorIds;
    }
}
