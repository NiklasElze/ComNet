package api.model;

import common.ErrorType;
import model.Student;

import java.util.List;

public class GetStudentListServiceResponse {

    private List<Student> studentList;
    private ErrorType errorType;

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}
