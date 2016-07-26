package common;

import org.hibernate.ejb.HibernatePersistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import java.util.HashMap;

public class EntityManagerHandler {

    private static EntityManagerFactory m_Factory;

    private static MyEntityManager m_Manager;

    public static void initialize(){

        if (m_Factory == null){
            PersistenceProvider provider = new HibernatePersistence();
            m_Factory = provider.createEntityManagerFactory("NewPersistenceUnit", new HashMap());
        }
    }

    public static EntityManagerFactory getFactory() {
        return m_Factory;
    }

    public static MyEntityManager createEntityManager() {
        return new MyEntityManager(m_Factory);
    }
}
