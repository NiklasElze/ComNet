package common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class MyEntityManager implements AutoCloseable{

    private EntityManagerFactory m_EntityManagerFactory;

    private EntityManager m_EntityManager;

    private MyEntityTransaction m_Transaction;

    public MyEntityManager(EntityManagerFactory factory) {
        m_EntityManagerFactory = factory;
        m_EntityManager = m_EntityManagerFactory.createEntityManager();
    }

    public EntityManager getManager() {
        if (m_EntityManager != null) {
            return m_EntityManager;
        }

        m_EntityManager = m_EntityManagerFactory.createEntityManager();
        return m_EntityManager;
    }

    public MyEntityTransaction beginTransaction(){
        m_Transaction = new MyEntityTransaction(m_EntityManager.getTransaction());
        m_Transaction.begin();

        return m_Transaction;
    }

    @Override
    public void close() {
        try{
            if (m_EntityManager != null && !m_EntityManager.isOpen()){
                m_EntityManager.close();
            }
        }
        catch (Exception ex){

        }
    }
}


