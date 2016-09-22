package repository.interfaces;

import model.Student;

import java.util.List;

public interface IStudentRepository {

    List<Student> getBySeminarGroupId(int id);

    Student getById(int id);

    List<Student> getAll();

    void add(Student student);
    void delete(Student student);
}
