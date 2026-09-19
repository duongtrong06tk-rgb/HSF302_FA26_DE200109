package fu.de200109;

import fu.de200109.dao.EmployeeDAO;
import fu.de200109.pojo.Employee;
import fu.de200109.pojo.Gender;
import fu.de200109.pojo.Project;
import fu.de200109.util.JPAUtil;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // Khai báo biến lưu ID để dùng sau khi session đóng
        Long nv1Id = null, nv2Id = null, nv3Id = null;
        Long proAId = null, proBId = null;

        System.out.println("====== BẮT ĐẦU CHẠY DEMO LAB MANY-TO-MANY (MANUAL JPA) ======");

        try {
            tx.begin();

            // 1. Tạo và persist các Project gốc
            Project proA = new Project("PRJ001", "Project A - FPT Smart City", new BigDecimal("50000"), LocalDate.now());
            Project proB = new Project("PRJ002", "Project B - EduNext Upgrade", new BigDecimal("75000"), LocalDate.now());
            em.persist(proA);
            em.persist(proB);

            // 2. Tạo và persist 3 Employee (Điền đủ thông tin theo TODO 5.7)
            Employee nv1 = new Employee("Nguyen Van Một", new BigDecimal("1500"), LocalDate.of(2025, 1, 1), "nv1@fpt.edu.vn", Gender.MALE);
            Employee nv2 = new Employee("Tran Thi Hai", new BigDecimal("2000"), LocalDate.of(2024, 5, 12), "nv2@fpt.edu.vn", Gender.FEMALE);
            Employee nv3 = new Employee("Le Bao Ba", new BigDecimal("1800"), LocalDate.of(2026, 2, 20), "nv3@fpt.edu.vn", Gender.OTHER);
            em.persist(nv1);
            em.persist(nv2);
            em.persist(nv3);

            tx.commit(); // Khởi tạo dữ liệu gốc thành công

            // Lưu lại ID để thao tác qua DAO
            nv1Id = nv1.getId(); nv2Id = nv2.getId(); nv3Id = nv3.getId();
            proAId = proA.getId(); proBId = proB.getId();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        // 3. Phân công chéo thông qua DAO (TODO 5.7)
        // NV1 -> A+B, NV2 -> B, NV3 -> A
        employeeDAO.assignEmployeeToProject(nv1Id, proAId);
        employeeDAO.assignEmployeeToProject(nv1Id, proBId);
        employeeDAO.assignEmployeeToProject(nv2Id, proBId);
        employeeDAO.assignEmployeeToProject(nv3Id, proAId);

        // 4. In danh sách project của từng nhân viên (Sử dụng EmployeeDAO với JOIN FETCH để chống LazyInitializationException)
        System.out.println("\n--- DANH SÁCH DỰ ÁN CỦA NHÂN VIÊN (Fetch an toàn qua EmployeeDAO) ---");
        List<Employee> allEmps = employeeDAO.findAllWithProjects();
        // Chú ý: EntityManager bên trong findAllWithProjects() ĐÃ ĐƯỢC ĐÓNG trong khối finally!
        // Nhưng nhờ có 'LEFT JOIN FETCH e.projects', dữ liệu projects đã được nạp sẵn, không gây lỗi Lazy:
        for (Employee e : allEmps) {
            System.out.print("Nhân viên " + e.getFullName() + " tham gia: ");
            e.getProjects().forEach(p -> System.out.print("[" + p.getProjectName() + "] "));
            System.out.println();
        }

        // Đóng EntityManagerFactory khi chương trình kết thúc để giải phóng tài nguyên
        JPAUtil.close();
    }
}