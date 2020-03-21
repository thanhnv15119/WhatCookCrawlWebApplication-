package dao;

import utils.DBUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDAO<T, PK extends Serializable> {

    protected Class<T> entityClass;

    public BaseDAO() {

        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperClass.getActualTypeArguments()[0];

    }

    public List<T> findByPaging(String nameQuery, int page, int size) {
        List<T> rs =null;
        try {
            EntityManager manager =DBUtils.getEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            rs = manager.createNamedQuery(nameQuery).setMaxResults(size).setFirstResult(page*size).getResultList();
            transaction.commit();
        } catch (Exception e) {

            System.out.println("findByPaging EROOR EXCEPTION: " + e.getMessage());
        }
        return rs;
    }


    public T insert(T t) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(t);
            manager.flush();
            transaction.commit();
            return t;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public T update(T t) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.merge(t);
            manager.flush();
            transaction.commit();
            return t;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public boolean delete(T t) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.remove(t);
            manager.flush();
            transaction.commit();
            return true;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public T findByID(PK id) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            T result = manager.find(entityClass, id);
            transaction.commit();
            return result;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public List<T> findByNameQuery(String namedQuery) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            List<T> result = manager.createNativeQuery(namedQuery, entityClass.getClass()).getResultList();
            transaction.commit();
            return result;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

}