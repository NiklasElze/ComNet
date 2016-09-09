package repository;

import model.Group;
import repository.interfaces.IGroupRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GroupRepository implements IGroupRepository{

    private EntityManager m_EntityManager;

    public GroupRepository(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public List<Group> getByStudentId(int id) {
        Query query = m_EntityManager.createQuery("select g from Group g where" +
                " (select s from Student s where s.userdata.id = :id) member of g.members");
        query.setParameter("id", id);

        return query.getResultList();
    }

    @Override
    public Group getById(int id) {
        return m_EntityManager.find(Group.class, id);
    }

    @Override
    public void add(Group group) {
        m_EntityManager.persist(group);
    }

    @Override
    public void remove(Group group) {
        m_EntityManager.remove(group);
    }
}
