package repository;

import model.Student;
import repository.interfaces.IStudentRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class StudentRepository implements IStudentRepository{

    private EntityManager m_EntityManager;

    public StudentRepository(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public List<Student> getBySeminarGroupId(int id) {
        Query query = m_EntityManager.createQuery("select s from Student s where s.seminarGroup.id = :id");
        query.setParameter("id", id);

        return query.getResultList();
    }

    @Override
    public Student getById(int id) {
        Query query = m_EntityManager.createQuery("select s from Student s where s.userdata.id = :id");
        query.setParameter("id", id);

        return query.getResultList().size() > 0
                ? (Student)query.getResultList().get(0)
                : null;
    }

    @Override
    public List<Student> getAll() {
        Query query = m_EntityManager.createQuery("select s from Student s");

        return query.getResultList();
    }

    @Override
    public void add(Student student) {
        m_EntityManager.persist(student);
    }

    @Override
    public void delete(Student student) {
        m_EntityManager.remove(student);
    }
}
