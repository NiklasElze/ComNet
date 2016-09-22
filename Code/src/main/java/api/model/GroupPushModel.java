package api.model;

import java.util.List;

public class GroupPushModel {
    private int id;
    private String name;
    private int creatorId;
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

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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
