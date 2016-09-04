package api.model;

import java.util.List;

public class ConversationPushModel {

    private int id;
    private List<Integer> memberIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Integer> memberIds) {
        this.memberIds = memberIds;
    }
}
