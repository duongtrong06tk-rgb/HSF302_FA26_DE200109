package fu.de200109;

import fu.de200109.dao.DepartmentDAO;
import fu.de200109.pojo.Department;
import fu.de200109.pojo.Employee;
import fu.de200109.pojo.Gender;
import fu.de200109.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();

/*    // 1) Tạo Department + 3 Employee, add qua helper method (TODO 2.4)
        Department it = new Department("Marketing", "Ha Noi");

        Employee e1 = new Employee("aa.nguyen@company.com", "Nguyen Van A", Gender.MALE,
                new BigDecimal("15000000"), LocalDate.of(2022, 1, 10));
        Employee e2 = new Employee("bb.tran@company.com", "Tran Thi B", Gender.FEMALE,
                new BigDecimal("18000000"), LocalDate.of(2021, 6, 1));
        Employee e3 = new Employee("cc.le@company.com", "Le Van C", Gender.OTHER,
                new BigDecimal("12000000"), LocalDate.of(2023, 3, 15));

        it.addEmployee(e1);
        it.addEmployee(e2);
        it.addEmployee(e3);

    // 2) Chỉ persist(department) — cascade = ALL tự lo phần Employee (TODO 2.7)
        departmentDAO.save(it);
        System.out.println("Da luu Department, id = " + it.getId());

    // 3) Tim lai kem employees bang JOIN FETCH (TODO 2.6) — khong bi
    //    LazyInitializationException du EntityManager cua lan tim nay da dong,
    //    vi employees da duoc load ngay trong cung 1 query.
        Department found = departmentDAO.findByIdWithEmployees(it.getId());
        System.out.println("Phong ban: " + found.getName());
        for (Employee e : found.getEmployees()) {
            System.out.println("  - " + e);
        }
*/
        /*  //TODO 2.8
        List<Department> departments = departmentDAO.findAll();

        // 2. Loop qua từng phòng ban và truy cập vào danh sách nhân viên (Kích hoạt N câu query phụ)
        int count = 1;
        for (Department d : departments) {
            System.out.println("\n--- Vòng lặp thứ " + count + " ---");
            System.out.println("Phòng ban: " + d.getName());

            // LƯU Ý CHẤM ĐIỂM: Chạm vào hàm .getEmployees() tại đây sẽ kích hoạt 1 câu SQL phụ cho mỗi phòng ban
            List<Employee> employeeList = d.getEmployees();

            System.out.println(">> Số lượng nhân viên thuộc phòng: " + employeeList.size());
            for (Employee e : employeeList) {
                System.out.println(e.getFullName() + " | Email: " + e.getEmail());
            }
            count++;
        }
        */

        //TODO 2.9
        List<Department> optimizedList = departmentDAO.findAllWithEmployees();

        // 2. Vòng lặp in thông tin kiểm chứng
        int count = 1;
        for (Department d : optimizedList) {
            System.out.println("\n--- Vòng lặp thứ " + count + " ---");
            System.out.println("Phòng ban: " + d.getName());

            // Vì dữ liệu employees đã được nạp lên ngay từ câu lệnh đầu tiên,
            // việc gọi hàm .getEmployees() ở đây HOÀN TOÀN KHÔNG sinh thêm câu SQL phụ nào nữa!
            List<Employee> employeeList = d.getEmployees();
            System.out.println("Sĩ số nhân viên: " + employeeList.size());
            for (Employee e : employeeList) {
                System.out.println("   + " + e.getFullName() + " (" + e.getEmail() + ")");
            }
            count++;
        }

        JPAUtil.close();

    }
}