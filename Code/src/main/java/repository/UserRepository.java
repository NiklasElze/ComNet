package repository;

import model.User;
import repository.interfaces.IUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserRepository implements IUserRepository {

    private EntityManager m_EntityManager;

    public UserRepository(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public List<User> getAll() {
        Query query = m_EntityManager.createQuery("select u from User u");

        return query.getResultList();
    }

    @Override
    public User getById(int id) {
        return m_EntityManager.find(User.class, id);
    }

    @Override
    public void add(User user) {
        m_EntityManager.persist(user);
    }

    @Override
    public void delete(User user) {
        m_EntityManager.remove(user);
    }
}
