package fu.de200109.dao;

import fu.de200109.pojo.Employee;
import fu.de200109.pojo.Project;
import fu.de200109.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class EmployeeDAO {

    public void save(Employee employee) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(employee);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Lấy toàn bộ danh sách nhân viên kèm theo dự án (Eager Fetch).
     * - Dùng LEFT JOIN FETCH để ngay cả nhân viên chưa được gán dự án nào vẫn được trả về.
     * - Dùng DISTINCT để loại bỏ các bản ghi Employee bị duplicate do cơ chế JOIN của SQL.
     * - Giúp truy xuất e.getProjects() an toàn mà KHÔNG bị LazyInitializationException ngay cả khi EntityManager đã đóng.
     */
    public List<Employee> findAllWithProjects() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.projects", Employee.class).getResultList();
        } finally {
            em.close();
        }
    }


    public Employee findById(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public void update(Employee employee) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            employee = em.merge(employee); // Gán lại kết quả sau khi merge
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
            Employee emp = em.find(Employee.class, id);
            if (emp != null) {
                em.remove(emp);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // TODO 5.6
    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);
            if (employee != null && project != null) {
                employee.assignToProject(project); // Gọi helper method đồng bộ
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close(); // Bắt buộc phải đóng EntityManager thủ công
        }
    }

    // TODO 5.8
    public List<Object[]> getProjectStats() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            String jpql = "SELECT p.projectName, COUNT(e), SUM(e.salary) " +
                    "FROM Project p JOIN p.employees e " +
                    "WHERE e.active = true " +
                    "GROUP BY p.projectName";
            return em.createQuery(jpql, Object[].class).getResultList();
        } finally {
            em.close();
        }
    }

    // TODO 5.9
    public void unassignEmployeeFromProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);
            if (employee != null && project != null) {
                employee.unassignFromProject(project);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }



}
