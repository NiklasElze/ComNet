package common;

import javax.persistence.EntityTransaction;

public class MyEntityTransaction implements AutoCloseable{

    private EntityTransaction m_Transaction;

    public MyEntityTransaction(EntityTransaction transaction){
        m_Transaction = transaction;
    }

    public boolean isActive(){
        return m_Transaction.isActive();
    }

    public void begin(){
        m_Transaction.begin();
    }

    public void commit(){
        m_Transaction.commit();
    }

    public void rollback(){
        m_Transaction.rollback();
    }

    @Override
    public void close() {
        try{
            if (m_Transaction.isActive()){
                m_Transaction.rollback();
            }
        }
        catch (Exception ex){

        }
    }
}
