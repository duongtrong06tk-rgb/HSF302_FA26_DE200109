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
        DepartmentDAO deptDAO = new DepartmentDAO();

        System.out.println("=== BẮT ĐẦU KIỂM THỬ TODO 2.5 (CRUD) ===");

        // 1. TEST hàm save()
        System.out.println("\n1. Kiểm thử hàm SAVE:");
        Department testDept = new Department("R&D Department", "Tòa nhà Alpha");

        // Thêm 1 nhân viên mẫu để đảm bảo cấu trúc dữ liệu không lỗi
        Employee sampleEmp = new Employee("dev.de200109@company.com", "Dev FPT",
                Gender.MALE, new BigDecimal("2000"), LocalDate.now());
        testDept.addEmployee(sampleEmp);

        deptDAO.save(testDept);
        Long generatedId = testDept.getId();
        System.out.println(">> Lưu thành công! ID tự sinh của phòng ban mới là: " + generatedId);


        // 2. TEST hàm findAll()
        System.out.println("\n2. Kiểm thử hàm FIND ALL:");
        List<Department> list = deptDAO.findAll();
        System.out.println(">> Số lượng phòng ban tìm thấy trong DB: " + list.size());
        for (Department d : list) {
            System.out.println("   - " + d);
        }


        // 3. TEST hàm update()
        System.out.println("\n3. Kiểm thử hàm UPDATE:");
        // Thay đổi thông tin trực tiếp trên đối tượng testDept vừa tạo
        testDept.setName("Research & Development");
        testDept.setLocation("Tòa nhà Delta - Lầu 3");

        deptDAO.update(testDept);
        System.out.println(">> Cập nhật thành công! Kiểm tra lại thông tin phòng ban.");


        // 4. TEST hàm delete()
        System.out.println("\n4. Kiểm thử hàm DELETE:");
        // Tiến hành xóa phòng ban vừa tạo dựa vào ID của nó
        deptDAO.delete(generatedId);
        System.out.println(">> Đã gọi lệnh xóa phòng ban ID: " + generatedId);

        // Kiểm tra xem phòng ban thực sự đã biến mất khỏi DB chưa
        List<Department> afterDeleteList = deptDAO.findAll();
        boolean exists = false;
        for (Department d : afterDeleteList) {
            if (d.getId().equals(generatedId)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            System.out.println(">> Kết quả: Xóa thành công! Thực thể không còn tồn tại trong DB.");
        } else {
            System.out.println(">> Kết quả thất bại: Phòng ban vẫn tồn tại.");
        }

        // Đóng EntityManagerFactory giải phóng bộ nhớ kết nối SQL Server
        JPAUtil.close();
        System.out.println("\n=== KẾT THÚC KIỂM THỬ CRUD THÀNH CÔNG ===");
    }
}