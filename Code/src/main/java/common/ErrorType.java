package common;

public enum ErrorType {
    NO_ERROR(0),

    /* 100-199 Login Errors */
    INVALID_LOGIN_DATA(100),
    LOGIN_NOT_FOUND(101),
    LOGIN_ALREADY_EXISTS(102),

    /* 200-299 Seminar Group Errors*/
    GROUP_CONTAINS_STUDENTS(200),
    GROUP_NOT_FOUND(201),

    /* 300-399 Student Errors*/
    STUDENT_NOT_FOUND(300),

    /* 400-499 User Errors */
    USER_NOT_FOUND(400),
    USER_IS_STUDENT(401),

    /* 500-599 Member Errors */
    MEMBER_NOT_FOUND(500),
    MEMBER_ALREADY_EXISTS(501),

    /* 600-699 Conversation Errors */
    CONVERSATION_ALREADY_EXISTS(600),
    CONVERSATION_NOT_FOUND(601),

    INVALID_ACTION(997),
    UNAUTHORIZED_ACTION(998),
    INTERNAL_ERROR(999);

    private final int id;

    ErrorType(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
}
