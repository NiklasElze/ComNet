package repository;

import model.Login;
import repository.interfaces.ILoginRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class LoginRepository implements ILoginRepository{

    private EntityManager m_EntityManager;

    public LoginRepository(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public Login getByUsername(String username) {
        Query query = m_EntityManager.createQuery("select l from Login l where l.username = :username");
        query.setParameter("username", username);

        return query.getResultList().size() > 0
                ? (Login)query.getResultList().get(0)
                : null;
    }

    @Override
    public Login getBySessionId(String sessionId) {
        Query query = m_EntityManager.createQuery("select l from Login l where l.sessionId = :sessionId");
        query.setParameter("sessionId", sessionId);

        return query.getResultList().size() > 0
                ? (Login)query.getResultList().get(0)
                : null;
    }

    @Override
    public Login getByUserId(int id) {
        Query query = m_EntityManager.createQuery("select l from Login l where l.userId = :studentId");
        query.setParameter("studentId", id);

        return query.getResultList().size() > 0
                ? (Login)query.getResultList().get(0)
                : null;
    }

    @Override
    public List<Login> getAll() {
        Query query = m_EntityManager.createQuery("select l from Login l");

        return query.getResultList();
    }

    @Override
    public void add(Login login) {
        m_EntityManager.persist(login);
    }

    public void delete(Login login){
        m_EntityManager.remove(login);
    }
}
