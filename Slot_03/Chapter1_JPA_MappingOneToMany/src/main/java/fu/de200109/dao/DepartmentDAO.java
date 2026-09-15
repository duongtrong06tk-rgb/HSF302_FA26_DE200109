package fu.de200109.dao;

import fu.de200109.pojo.Department;
import fu.de200109.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class DepartmentDAO {

    public void save(Department department) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(department);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Department> findAll() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d", Department.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void update(Department department) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            department = em.merge(department); // Bắt buộc phải gán lại kết quả
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Department dept = em.find(Department.class, id);
            if (dept != null) {
                em.remove(dept);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
