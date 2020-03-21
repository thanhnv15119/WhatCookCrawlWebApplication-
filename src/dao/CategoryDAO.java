package dao;

import entity.Category;
import utils.DBUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

public class CategoryDAO extends BaseDAO<Category, Integer> {

    public CategoryDAO() {
    }

    public Category findByName(String name) {
        EntityManager em = DBUtils.getEntityManager();
        Category rs = null;
        try {
            EntityTransaction et = em.getTransaction();
            et.begin();
             rs = (Category) em.createNamedQuery("Category.findByName")
                    .setParameter("name", name)
                    .getSingleResult();
            et.commit();
        }catch (NoResultException e) {
            rs = null;
        } catch (NonUniqueResultException e) {

        }finally {
            if(em!= null) {
                em.close();
            }
        }
        return rs;
    }

    public List<Category> findAll() {
        EntityManager em = DBUtils.getEntityManager();
        List<Category> rs = null;
        try {
            EntityTransaction et = em.getTransaction();
            et.begin();
            rs = em.createNamedQuery("Category.findAll").getResultList();
            et.commit();
        }catch (NoResultException e) {
            rs = null;
        } finally {
            if(em!= null) {
                em.close();
            }
        }
        return rs;
    }

    public boolean save(Category category) {
        boolean rs = false;
        if (category != null) {
            Category checkDup2 = findByName(category.getName());
            if (checkDup2 == null) {
                insert(category);
                rs = true;
            }
        }
        return rs;
    }
}
