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
    //TODO 2.6
    public Department findByIdWithEmployees(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d JOIN FETCH d.employees WHERE d.id = :id", Department.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    //TODO 2.9
    public List<Department> findAllWithEmployees() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            // Sử dụng JOIN FETCH d.employees để nạp sớm (Eager Load) danh sách nhân viên đi kèm
            // Dùng DISTINCT để tránh bị trùng lặp bản ghi phòng ban khi trả về kết quả
            return em.createQuery("SELECT DISTINCT d FROM Department d JOIN FETCH d.employees", Department.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
