package repository;

import model.SeminarGroup;
import repository.interfaces.ISeminarGroupRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class SeminarGroupRepository implements ISeminarGroupRepository{

    private EntityManager m_EntityManager;

    public SeminarGroupRepository(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public List<SeminarGroup> getAll() {
        Query query = m_EntityManager.createQuery("select sg from SeminarGroup sg");

        return query.getResultList();
    }

    @Override
    public SeminarGroup getById(int id) {
        return m_EntityManager.find(SeminarGroup.class, id);
    }

    @Override
    public void add(SeminarGroup seminarGroup) {
        m_EntityManager.persist(seminarGroup);
    }

    @Override
    public void delete(SeminarGroup seminarGroup) {
        m_EntityManager.remove(seminarGroup);
    }
}
