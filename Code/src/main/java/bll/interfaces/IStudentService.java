package bll.interfaces;

import api.model.StudentData;
import api.model.StudentPushModel;
import common.ServiceException;
import model.Student;

import java.util.List;

public interface IStudentService {
    List<Student> getStudentsOfSeminarGroup(int seminarGroupId) throws ServiceException;
    List<Student> getStudentList() throws ServiceException;
    StudentData getStudentData(int id) throws ServiceException;

    void createOrUpdateStudent(StudentPushModel model) throws ServiceException;
    void deleteStudent(int id) throws ServiceException;
}
