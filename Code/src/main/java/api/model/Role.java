package api.model;

public enum Role {
    STUDENT(1),
    ADMIN(2),
    PERSON_IN_AUTHORITY(3);

    private final int id;

    Role(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
}
