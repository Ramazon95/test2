package ru.ystu.cmis.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.ystu.cmis.config.SF;

import java.lang.reflect.ParameterizedType;

public class Repository <T> {
    protected final SessionFactory sf = SF.getInstance();
    private void txCommit(T el, String action){
        try{
            Session sess = sf.openSession();
            Transaction tx = sess.beginTransaction();
            try {
                if(action.equals("save"))
                    sess.save(el);
                else if (action.equals("update"))
                    sess.saveOrUpdate(el);
                else if(action.equals("delete"))
                    sess.delete(el);
                sess.flush();
                tx.commit();
            } catch (HibernateException err){
                tx.rollback();
            } finally {
                sess.close();
            }
        } catch (ExceptionInInitializerError err) {
            System.out.println(err.getMessage());
        }
    }
    public void create(T element) {txCommit(element, "save");}

    public void update(T element) {txCommit(element, "update");}

    private Class<T> getClassType(){
        ParameterizedType type = (ParameterizedType)getClass().getGenericSuperclass();
        System.out.println(type);
        Class<T> parameter = (Class<T>) type.getActualTypeArguments()[0];
        System.out.println(parameter);
        return parameter;
    }

    public void delete(Long id){
        try {
            Session sess = sf.openSession();
            Transaction tx = sess.beginTransaction();
            T element = sess.byId(getClassType()).load(id);
            sess.delete(element);
            sess.flush();
            tx.commit();
            sess.close();
        } catch (ExceptionInInitializerError err){}

    }

    public T byId(long id){
        try{
            Session sess = sf.openSession();
            T element = sess.byId(getClassType()).load(id);
            sess.close();
            return element;
        } catch (ExceptionInInitializerError err){
            return null;
        }
    }
}
