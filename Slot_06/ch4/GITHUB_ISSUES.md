# Danh Sách GitHub Issues - HSF302 Exercise 2 (Many-To-Many)

Tập tin này tổng hợp toàn bộ các GitHub Issues theo chuẩn yêu cầu của môn học HSF302, phục vụ cho việc quản lý tiến độ và truy vết (traceability) trên GitHub repository.

---

## Issue: [EX2-TODO 1] Khởi tạo nhánh exercise2 và cấu hình môi trường chạy độc lập theo Spring Profiles

### 📌 Mô tả yêu cầu (Description)
Khởi tạo nhánh `exercise2` và cấu hình môi trường chạy độc lập theo Spring Profiles (`ex1` / `ex2`) nhằm cô lập dữ liệu và runner của Exercise 1 với Exercise 2.

### 🎯 Mục tiêu (Objectives)

- Phân tách môi trường chạy giữa Exercise 1 và Exercise 2 trên cùng một project và cơ sở dữ liệu (`HSF302_CH4`).
- Cấu hình Spring Profile (`@Profile("ex1")`) cho `ExerciseRunner` cũ để tránh việc các câu lệnh sửa/xoá dữ liệu ở Part E của Exercise 1 gây sai lệch kết quả cho Exercise 2.
- Kích hoạt mặc định `spring.profiles.active=ex2` trong file cấu hình để sẵn sàng triển khai runner mới cho Exercise 2.
- Đảm bảo dữ liệu nền tảng (`DataInitializer`: 4 departments, 10 students) vẫn luôn được khởi tạo tự động khi khởi động ứng dụng.

### ✅ Danh sách công việc (Checklist)

- [x] Tạo và chuyển sang nhánh mới `exercise2` từ nhánh `main`.
- [x] Thêm annotation `@Profile("ex1")` vào `ExerciseRunner.java` để ngăn runner cũ tự kích hoạt khi chạy Exercise 2.
- [x] Bổ sung cấu hình `spring.profiles.active=ex2` vào file `application.properties`.
- [x] Chạy thử ứng dụng Spring Boot, xác nhận log console chỉ khởi tạo dữ liệu seed ban đầu (`Seeded 4 departments, 10 students`) và không còn in các kết quả chạy của Exercise 1.

### 🔗 Liên kết Commit (Related Commits)

- Khởi tạo nhánh và cấu hình profile: `37f26d2` (`chore(setup): create exercise2 branch and profile-based runners ExerciseRunner cũ chỉ chạy với profile ex1, mặc định active profile ex2 Refs: EX2 TODO 1`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình Terminal/Console sau khi chạy lệnh Spring Boot Application.
2. Vùng cần làm nổi bật:
   - Dòng log Spring Profiles đang active: "The following profiles are active: ex2"
   - Dòng log dữ liệu seed thành công: ">>> Seeded 4 departments, 10 students"
   - Hoàn toàn KHÔNG xuất hiện log của các TODO cũ từ Exercise 1.
-->

---

## Issue: [EX2-TODO 2] Thiết kế Entity Course đóng vai trò Inverse Side trong quan hệ Many-To-Many với Student

### 📌 Mô tả yêu cầu (Description)
Xây dựng Entity `Course` đại diện cho bảng `courses`, cấu hình quan hệ nhiều-nhiều với `Student` ở phía inverse side thông qua thuộc tính `mappedBy`, và cài đặt `equals/hashCode` chuẩn Hibernate theo Business Key.

### 🎯 Mục tiêu (Objectives)

- Ánh xạ Entity `Course` với bảng `courses` trong CSDL SQL Server gồm đầy đủ các thuộc tính nghiệp vụ (`id`, `code`, `name`, `credits`, `capacity`, `semester`).
- Thiết lập quan hệ `@ManyToMany(mappedBy = "courses")` với `Student`, xác định rõ `Course` là **Inverse Side** (không quản lý bảng trung gian, chỉ phục vụ điều hướng và truy vấn dữ liệu từ khóa học sang sinh viên).
- Cài đặt `equals()` và `hashCode()` dựa trên **Business Key (`code`)** thay vì khóa chính `id`, đảm bảo tính toàn vẹn khi lưu trữ Entity trong `Set`/`HashSet` trước và sau khi persist.
- Áp dụng các Best Practice chuyên sâu của Hibernate: sử dụng getter (`other.getCode()`) để an toàn với Hibernate Proxy khi quan hệ ở chế độ Lazy loading, tránh dùng `@Data` để loại trừ nguy cơ `StackOverflowError` và `LazyInitializationException`.

### ✅ Danh sách công việc (Checklist)

- [x] Tạo file `src/main/java/com/hsf302/ch4/pojo/Course.java`.
- [x] Khai báo các cột dữ liệu với ràng buộc: `code` (unique, nullable = false, max 10), `name` (nullable = false, max 100), `credits`, `capacity`, `semester`.
- [x] Khai báo collection `Set<Student> students = new HashSet<>()` với annotation `@ManyToMany(mappedBy = "courses")`.
- [x] Override phương thức `equals(Object o)` và `hashCode()` sử dụng `code` làm định danh nghiệp vụ.
- [x] Override `toString()` chỉ in thông tin chi tiết của môn học, tuyệt đối không in collection `students` để tránh lỗi vòng lặp hoặc Lazy load ngoài Session.

### 🔗 Liên kết Commit (Related Commits)

- Triển khai Entity Course: `dfbf77a` (`feat(entity): add Course entity as inverse side Course dùng @ManyToMany(mappedBy = 'courses'), equals/hashCode theo code Refs: EX2 TODO 2`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình cấu trúc file Course.java trong IDE (làm nổi bật annotation @Entity, @Table, @ManyToMany(mappedBy = "courses")).
2. Chụp đoạn code override equals() và hashCode() theo business key "code".
3. (Tùy chọn) Chụp cấu trúc bảng "courses" được sinh tự động trong SSMS (SQL Server Management Studio) hoặc log DDL create table courses trong console khi chạy app.
-->

---

## Issue: [EX2-TODO 3] Cấu hình Student là Owning Side với @JoinTable và triển khai helper methods đồng bộ 2 chiều

### 📌 Mô tả yêu cầu (Description)
Cập nhật Entity `Student` làm Owning Side quản trị bảng trung gian `student_courses` trong mối quan hệ nhiều-nhiều với `Course`, xây dựng các helper methods `enroll`/`unenroll` đảm bảo đồng bộ trạng thái 2 chiều trong bộ nhớ Java và thiết lập `equals/hashCode` chuẩn theo Business Key.

### 🎯 Mục tiêu (Objectives)

- Khai báo quan hệ `@ManyToMany` trên thuộc tính `courses` trong `Student.java`, định nghĩa tường minh bảng liên kết trung gian `student_courses` thông qua annotation `@JoinTable`.
- Chỉ định chính xác 2 cột khóa ngoại: `joinColumns = @JoinColumn(name = "student_id")` (trỏ về `students.id`) và `inverseJoinColumns = @JoinColumn(name = "course_id")` (trỏ về `courses.id`), ngăn chặn triệt để lỗi trùng tên cột `id` (`MappingException`).
- Tuân thủ nguyên tắc không sử dụng Cascade (`CascadeType.REMOVE` hoặc `PERSIST`) trên mối quan hệ Many-To-Many để tránh xóa nhầm dữ liệu độc lập của Course khi xóa Student.
- Triển khai các phương thức tiện ích đồng bộ hai chiều `enroll(Course)` và `unenroll(Course)` để đảm bảo tính nhất quán dữ liệu giữa cả 2 phía trong cùng một Persistence Context / Transaction.
- Cài đặt `equals()` và `hashCode()` dựa trên định danh nghiệp vụ duy nhất `studentCode`.

### ✅ Danh sách công việc (Checklist)

- [x] Thêm trường `Set<Course> courses = new HashSet<>()` vào `Student.java` với cấu hình `@ManyToMany` và `@JoinTable(name = "student_courses", joinColumns = ..., inverseJoinColumns = ...)`.
- [x] Triển khai helper method `enroll(Course c)`: thêm khóa học vào `courses` của Student và thêm Student vào `students` của Course.
- [x] Triển khai helper method `unenroll(Course c)`: xóa khóa học khỏi `courses` của Student và xóa Student khỏi `students` của Course.
- [x] Override `equals(Object o)` và `hashCode()` theo thuộc tính duy nhất `studentCode` (dùng getter `other.getStudentCode()`).
- [x] Bảo toàn phương thức `toString()` hiện tại (không in `courses` và `department` để chống `LazyInitializationException` và vòng lặp vô tận).
- [x] Chạy ứng dụng và xác nhận Hibernate DDL tự động sinh bảng trung gian `student_courses` với khóa chính ghép `(student_id, course_id)` và 2 foreign key constraints tương ứng.

### 🔗 Liên kết Commit (Related Commits)

- Cấu hình Student owning side & helpers: `d2583fb` (`feat(entity): map many-to-many between Student and Course (Student là owning side với @JoinTable student_courses, helper enroll/unenroll Refs: EX2 TODO 3)`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp đoạn code cấu hình @ManyToMany và @JoinTable trong Student.java (thấy rõ 2 cột student_id và course_id).
2. Chụp 2 helper methods enroll(Course c) và unenroll(Course c).
3. Chụp console log hiển thị các câu lệnh DDL Hibernate sinh bảng student_courses và 2 constraint foreign key (FK).
4. (Tùy chọn) Chụp cấu trúc bảng student_courses và các Foreign Keys trong SQL Server Object Explorer / SSMS.
-->

---

## Issue: [TODO 4] Khởi tạo CourseRepository và khung kiến trúc phân tầng Service Layer

### 📌 Mô tả yêu cầu (Description)
Thiết lập tầng dữ liệu `CourseRepository` kế thừa `JpaRepository` cùng bộ khung Service Layer tách biệt (`CourseService` và `EnrollmentService`) tuân thủ kiến trúc nhiều tầng (Layered Architecture) và nguyên tắc quản lý giao dịch `@Transactional(readOnly = true)`.

### 🎯 Mục tiêu (Objectives)

- Xây dựng interface `CourseRepository` kế thừa `JpaRepository<Course, Long>` để thừa hưởng các thao tác CRUD cơ bản và chuẩn bị cho các phương thức truy vấn mở rộng.
- Thiết kế phân tách ranh giới nghiệp vụ (Separation of Concerns) rõ ràng giữa 2 dịch vụ:
  - `CourseService` / `CourseServiceImpl`: Xử lý các nghiệp vụ vòng đời, truy vấn và thống kê trực tiếp trên đối tượng `Course`.
  - `EnrollmentService` / `EnrollmentServiceImpl`: Xử lý các logic nghiệp vụ tác động lên mối quan hệ đăng ký nhiều-nhiều giữa `Student` và `Course` (yêu cầu phối hợp cả `StudentRepository` và `CourseRepository`).
- Tuân thủ Best Practice của Spring Enterprise: khai báo `@Transactional(readOnly = true)` ở cấp độ class của Service Implementation nhằm tối ưu hóa hiệu năng đọc dữ liệu của Hibernate Session (bỏ qua Dirty Checking) và tiêm phụ thuộc qua Constructor sử dụng `@RequiredArgsConstructor` của Lombok.

### ✅ Danh sách công việc (Checklist)

- [x] Tạo interface `src/main/java/com/hsf302/ch4/repository/CourseRepository.java` kế thừa `JpaRepository<Course, Long>`.
- [x] Tạo interface `src/main/java/com/hsf302/ch4/service/CourseService.java` định nghĩa các hàm nghiệp vụ khóa học.
- [x] Tạo interface `src/main/java/com/hsf302/ch4/service/EnrollmentService.java` định nghĩa các hàm nghiệp vụ đăng ký/hủy đăng ký.
- [x] Tạo class `src/main/java/com/hsf302/ch4/service/CourseServiceImpl.java` cài đặt `CourseService`, inject `CourseRepository`.
- [x] Tạo class `src/main/java/com/hsf302/ch4/service/EnrollmentServiceImpl.java` cài đặt `EnrollmentService`, inject cả `StudentRepository` và `CourseRepository`.
- [x] Gắn annotation `@Service`, `@RequiredArgsConstructor`, và `@Transactional(readOnly = true)` (từ package `org.springframework.transaction.annotation`) trên các lớp implementation.

### 🔗 Liên kết Commit (Related Commits)

- Khởi tạo Repository và khung Service: `2928e5a` (`feat(repository): add CourseRepository and enrollment service skeleton Refs: EX2 TODO 4`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp ảnh cấu trúc cây thư mục (Project Tree) trong IDE thể hiện các file mới tạo: CourseRepository.java, CourseService.java, EnrollmentService.java, CourseServiceImpl.java, EnrollmentServiceImpl.java.
2. Chụp code của EnrollmentServiceImpl.java thể hiện việc inject cả 2 repository (StudentRepository & CourseRepository) kèm annotation @Transactional(readOnly = true).
-->

---

## Issue: [EX2-TODO 5] Khởi tạo dữ liệu mẫu CourseDataInitializer và thiết lập khung thực thi Exercise2Runner

### 📌 Mô tả yêu cầu (Description)
Triển khai bộ khởi tạo dữ liệu mẫu `CourseDataInitializer` (`@Order(2)`) để nạp danh sách khóa học và đăng ký môn học cho sinh viên thông qua cơ chế Transactional Dirty Checking, đồng thời thiết lập khung thực thi `Exercise2Runner` (`@Order(3)`, `@Profile("ex2")`) phân chia rõ ràng các giai đoạn Part B, C, D, E và Bonus.

### 🎯 Mục tiêu (Objectives)

- Khởi tạo 6 môn học mẫu (`PRJ301`, `HSF302`, `SWP391`, `AIL303`, `IAA202`, `MKT101`) vào bảng `courses` thông qua `CourseRepository.saveAll()`.
- Sử dụng helper method `s.enroll(c)` trong một Transaction duy nhất (`@Transactional` trên method `run()` của `CourseDataInitializer`) để tự động đồng bộ 18 lượt đăng ký vào bảng trung gian `student_courses` nhờ cơ chế Dirty Checking của JPA/Hibernate mà không cần gọi `studentRepository.save()`.
- Hiểu rõ vai trò của `@Transactional` trong việc nạp Lazy Collection `courses` của Student, tránh triệt để lỗi `LazyInitializationException` khi thao tác trong quá trình khởi tạo dữ liệu.
- Thiết lập khung điều khiển `Exercise2Runner` với thứ tự ưu tiên `@Order(3)` và gắn profile `@Profile("ex2")`, chỉ phụ thuộc vào các Service Interfaces (`CourseService`, `EnrollmentService`, `StudentService`), chuẩn bị cấu trúc modular cho các phần kiểm thử tiếp theo.

### ✅ Danh sách công việc (Checklist)

- [x] Tạo file `src/main/java/com/hsf302/ch4/runner/CourseDataInitializer.java` với các annotation `@Component`, `@Order(2)`, `@RequiredArgsConstructor`.
- [x] Triển khai method `run(String... args)` có `@Transactional`, kiểm tra điều kiện dữ liệu trước khi seed (`count() > 0`).
- [x] Lưu 6 đối tượng `Course` mẫu và thực hiện đăng ký môn học cho sinh viên theo đúng kịch bản đề bài (sinh viên `IA003` chưa đăng ký môn nào; môn `MKT101` chưa có người học).
- [x] Tạo file `src/main/java/com/hsf302/ch4/runner/Exercise2Runner.java` với các annotation `@Component`, `@Order(3)`, `@Profile("ex2")`, `@RequiredArgsConstructor`.
- [x] Tiêm các Service interfaces (`CourseService`, `EnrollmentService`, `StudentService`) vào `Exercise2Runner` và dựng khung các hàm `partB()`, `partC()`, `partD()`, `bonus()`, `partE()`.
- [x] Chạy ứng dụng và xác nhận console in thông điệp khởi tạo thành công (`>>> Seeded 6 courses`), kiểm tra bảng `student_courses` có đúng 18 bản ghi trong CSDL.

### 🔗 Liên kết Commit (Related Commits)

- Khởi tạo dữ liệu mẫu và runner Exercise 2: `6d4cca4` (`feat(data): seed courses and student enrollments (Thêm CourseDataInitializer @Order(2) và khung Exercise2Runner @Order(3) Refs: EX2 TODO 5)`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp log Console khi ứng dụng khởi động thành công với 2 dòng seed dữ liệu:
   - ">>> Seeded 4 departments, 10 students"
   - ">>> Seeded 6 courses"
2. Chụp kết quả truy vấn đếm số lượng dòng trong SQL Server Management Studio:
   - Query: SELECT COUNT(*) FROM student_courses; (kết quả trả về: 18)
3. Chụp bảng kết quả liên kết giữa sinh viên và mã môn học:
   - Query: SELECT s.student_code, c.code FROM student_courses sc JOIN students s ON s.id = sc.student_id JOIN courses c ON c.id = sc.course_id ORDER BY s.student_code, c.code;
-->

---

## Issue: [EX2-TODO 6] Thao tác CRUD cơ bản và sắp xếp khóa học sử dụng Built-in Methods của JpaRepository

### 📌 Mô tả yêu cầu (Description)
Triển khai các phương thức nghiệp vụ đọc dữ liệu cơ bản trên `CourseService` (`count()`, `findAllOrderByCode()`, `findById()`) bằng cách tận dụng các phương thức có sẵn của `JpaRepository` kết hợp với đối tượng `Sort`.

### 🎯 Mục tiêu (Objectives)

- Tận dụng các phương thức tích hợp sẵn (built-in methods) của Spring Data JPA (`count()`, `findAll(Sort)`, `findById()`) mà không cần viết câu lệnh truy vấn thủ công.
- Sử dụng đối tượng `Sort.by("code")` để truy xuất toàn bộ danh sách khóa học được sắp xếp tăng dần theo mã môn học (`code ASC`) trực tiếp từ tầng cơ sở dữ liệu.
- Xử lý giá trị trả về an toàn với `Optional<Course>` khi tìm kiếm theo khóa chính `id`, tránh phát sinh lỗi `NullPointerException` với trường hợp tồn tại (ID 2L) và không tồn tại (ID 99L).
- Thực thi kiểm thử chức năng trong `Exercise2Runner.todo6()` và xác thực dữ liệu in ra console khớp chính xác với 6 khóa học đã khởi tạo.

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo các phương thức `long count()`, `List<Course> findAllOrderByCode()`, `Optional<Course> findById(Long id)` trong `CourseService`.
- [x] Triển khai cài đặt các phương thức trên trong `CourseServiceImpl` gọi tương ứng `courseRepository.count()`, `courseRepository.findAll(Sort.by("code"))`, và `courseRepository.findById(id)`.
- [x] Viết hàm kiểm thử `todo6()` trong `Exercise2Runner.java` thực hiện:
  - In tổng số môn học: `Total courses: 6`.
  - In danh sách tất cả môn học sắp xếp theo mã: `AIL303`, `HSF302`, `IAA202`, `MKT101`, `PRJ301`, `SWP391`.
  - Tìm kiếm theo `id = 2L` (thành công) và `id = 99L` (trả về `Not found`).
- [x] Kích hoạt gọi `todo6()` trong method `partB()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Triển khai CRUD cơ bản và sắp xếp khóa học: `6d4cca4` (`feat(builtin): count, sort and find courses by id Refs: EX2 TODO 6`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo6():
   - Dòng log tiêu đề: "===== TODO 6: count, findAll(Sort), findById ====="
   - Dòng "Total courses: 6"
   - Danh sách 6 khóa học đã được sắp xếp tăng dần theo code: AIL303 -> SWP391 (6 record(s))
   - Log kiểm tra findById:
     + findById(2): HSF302 | Hibernate & Spring Framework ...
     + findById(99): Not found
-->

---

## Issue: [EX2-TODO 7] Điều hướng quan hệ Many-To-Many hai chiều và xử lý nạp Lazy Collection an toàn trong Service Transaction

### 📌 Mô tả yêu cầu (Description)
Triển khai điều hướng 2 chiều giữa `Student` (Owning side) và `Course` (Inverse side) thông qua các phương thức `getCoursesOfStudent()` và `getStudentsOfCourse()`, đảm bảo nạp tập hợp Lazy collection bên trong phạm vi Session của `@Transactional(readOnly = true)` để ngăn chặn lỗi `LazyInitializationException`.

### 🎯 Mục tiêu (Objectives)

- Khai báo phương thức `Optional<Course> findByCode(String code)` trên `CourseRepository` để tìm kiếm khóa học theo mã định danh duy nhất.
- Triển khai phương thức `getCoursesOfStudent(String studentCode)` điều hướng từ Owning side: truy xuất sinh viên và duyệt qua collection `student.getCourses()`, sắp xếp danh sách môn học theo `code`.
- Triển khai phương thức `getStudentsOfCourse(String courseCode)` điều hướng từ Inverse side: truy xuất khóa học và duyệt qua collection `course.getStudents()`, sắp xếp danh sách sinh viên theo `fullName`.
- Hiểu rõ cơ chế quản lý Persistence Context của Spring: việc truy cập Lazy Collection bên trong Transaction của Service đảm bảo Session vẫn đang mở, cho phép Hibernate tự động thực thi thêm câu lệnh SELECT JOIN bảng trung gian `student_courses` mà không gây lỗi.
- Xây dựng các hàm tiện ích kiểm tra tham số đầu vào (`getStudent()`, `getCourse()`) ném ngoại lệ rõ ràng khi mã sinh viên / khóa học rỗng hoặc không tồn tại.

### ✅ Danh sách công việc (Checklist)

- [x] Bổ sung phương thức `Optional<Course> findByCode(String code)` vào `CourseRepository`.
- [x] Khai báo 2 phương thức `List<Course> getCoursesOfStudent(String studentCode)` và `List<Student> getStudentsOfCourse(String courseCode)` trong `EnrollmentService`.
- [x] Cài đặt 2 phương thức trên trong `EnrollmentServiceImpl`, sử dụng Java Stream API (`stream().sorted(...).toList()`) để sắp xếp kết quả trả về.
- [x] Viết các phương thức helper dùng chung `getStudent(String studentCode)` và `getCourse(String courseCode)` để tái sử dụng và kiểm soát ngoại lệ.
- [x] Triển khai method `todo7()` trong `Exercise2Runner.java` thực hiện:
  - In danh sách các môn học của sinh viên `SE001`: `HSF302`, `PRJ301`, `SWP391` (3 môn).
  - In danh sách các sinh viên tham gia khóa học `AIL303`: `Hoang Van Em`, `Pham Thi Dung`, `Tran Thi Binh`, `Vo Thi Hoa` (4 sinh viên).
- [x] Kích hoạt gọi `todo7()` trong `partB()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Điều hướng quan hệ 2 chiều giữa Student và Course: `7d37621` (`feat(enrollment): navigate courses of student and students of course Refs: EX2 TODO 7`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo7():
   - Dòng tiêu đề: "===== TODO 7: navigate student.getCourses() / course.getStudents() ====="
   - Dòng log mục (a): "-- (a) Courses of SE001:" kèm 3 môn HSF302, PRJ301, SWP391 (-> 3 record(s))
   - Dòng log mục (b): "-- (b) Students of AIL303:" kèm 4 sinh viên Hoang Van Em, Pham Thi Dung, Tran Thi Binh, Vo Thi Hoa (-> 4 record(s))
-->

---

## Issue: [EX2-TODO 8] Truy vấn Derived Query Methods tìm kiếm và đếm số lượng khóa học theo học kỳ

### 📌 Mô tả yêu cầu (Description)
Triển khai các phương thức truy vấn dẫn xuất (Derived Query Methods) trên `CourseRepository` để tìm kiếm khóa học theo mã, lọc danh sách khóa học theo học kỳ kết hợp sắp xếp mã môn học tăng dần, và đếm số lượng khóa học theo học kỳ mà không cần viết câu lệnh JPQL.

### 🎯 Mục tiêu (Objectives)

- Ứng dụng quy ước đặt tên của Spring Data JPA để tự động sinh câu lệnh SQL từ tên phương thức:
  - `findByCode(String code)`: Tìm kiếm khóa học theo mã (độc nhất).
  - `findBySemesterOrderByCodeAsc(String semester)`: Lọc các khóa học mở trong học kỳ chỉ định và sắp xếp tăng dần theo `code`.
  - `countBySemester(String semester)`: Đếm tổng số môn học được mở trong một học kỳ.
- Ủy quyền các phương thức từ Repository lên `CourseService` / `CourseServiceImpl` để cung cấp API nhất quán cho Runner.
- Kiểm thử các trường hợp dữ liệu biên trong `Exercise2Runner.todo8()`:
  - Tìm mã tồn tại (`HSF302`) và mã không tồn tại (`XXX000`).
  - Lấy danh sách khóa học học kỳ `SU26` (kết quả mong đợi: `IAA202`, `MKT101`).
  - Đếm tổng số môn học kỳ `FA26` (kết quả mong đợi: `4`).

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo các method `findBySemesterOrderByCodeAsc(String semester)` và `countBySemester(String semester)` trong `CourseRepository`.
- [x] Khai báo các method tương ứng trong interface `CourseService`.
- [x] Triển khai cài đặt các method trong `CourseServiceImpl`.
- [x] Viết hàm kiểm thử `todo8()` trong `Exercise2Runner.java` thực hiện:
  - Kiểm tra tìm kiếm theo mã: `(a) HSF302: Hibernate & Spring Framework` và `(a) XXX000: Not found`.
  - Kiểm tra lọc theo học kỳ `SU26`: in danh sách gồm `IAA202` và `MKT101` (2 records).
  - Kiểm tra đếm số môn học kỳ `FA26`: `(c) Courses in FA26: 4`.
- [x] Kích hoạt gọi `todo8()` trong method `partC()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Truy vấn khóa học theo mã và học kỳ bằng derived query: `ea77a25` (`feat(derived): find courses by code and semester Refs: EX2 TODO 8`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo8():
   - Dòng tiêu đề: "===== TODO 8: findByCode, findBySemester, countBySemester ====="
   - Dòng log mục (a):
     + (a) HSF302: Hibernate & Spring Framework
     + (a) XXX000: Not found
   - Dòng log mục (b): "-- (b) Semester SU26:" gồm 2 môn IAA202 và MKT101 (-> 2 record(s))
   - Dòng log mục (c): "(c) Courses in FA26: 4"
-->

---

## Issue: [EX2-TODO 9] Truy vấn Nested Property qua Collection Many-To-Many (Courses_Code) trên StudentRepository

### 📌 Mô tả yêu cầu (Description)
Triển khai các phương thức truy vấn dẫn xuất (Derived Query Methods) trên `StudentRepository` đi xuyên qua tập hợp collection `courses` bằng cú pháp nested property (`Courses_Code`), cho phép Spring Data JPA tự động sinh câu lệnh SQL `JOIN` qua bảng trung gian `student_courses` để tìm kiếm và đếm số lượng sinh viên theo môn học.

### 🎯 Mục tiêu (Objectives)

- Nắm vững cú pháp truy vấn thuộc tính lồng nhau qua Collection trong Spring Data JPA (`Courses_Code` tương ứng với `student.courses` trỏ sang `course.code`, ký tự `_` giúp phân tách thuộc tính rõ ràng).
- Hiểu cơ chế sinh SQL tự động của Hibernate: tự động thực hiện 2 câu lệnh `JOIN` (từ `students` sang `student_courses`, rồi sang `courses`) kết hợp điều kiện `WHERE courses.code = ?` mà không cần viết câu lệnh JPQL hay Native SQL.
- Xây dựng 3 phương thức nghiệp vụ trên `EnrollmentService`:
  - `findStudentsInCourse(String courseCode)`: Lấy danh sách tất cả sinh viên đăng ký khóa học, sắp xếp theo họ tên (`full_name ASC`).
  - `countStudentsInCourse(String courseCode)`: Đếm tổng số sinh viên đăng ký môn học trực tiếp ở mức CSDL (`COUNT`).
  - `findActiveStudentsInCourse(String courseCode)`: Lọc sinh viên đăng ký môn học đồng thời đang ở trạng thái hoạt động (`active = true`).
- Kiểm thử trong `Exercise2Runner.todo9()`:
  - Sinh viên học `PRJ301`: `Le Van Cuong`, `Nguyen Van An`, `Tran Thi Binh`, `Vo Thi Hoa` (4 sinh viên).
  - Số lượng sinh viên học `HSF302`: `5`.
  - Sinh viên active học `PRJ301`: `Nguyen Van An`, `Tran Thi Binh`, `Vo Thi Hoa` (đã lọc bỏ sinh viên inactive `Le Van Cuong`).

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo các method trong `StudentRepository`:
  - `List<Student> findByCourses_CodeOrderByFullNameAsc(String courseCode)`
  - `long countByCourses_Code(String courseCode)`
  - `List<Student> findByCourses_CodeAndActiveTrueOrderByFullNameAsc(String courseCode)`
- [x] Khai báo các method tương ứng trong `EnrollmentService`.
- [x] Cài đặt các method trên trong `EnrollmentServiceImpl` gọi các method tương ứng từ `studentRepository`.
- [x] Viết hàm kiểm thử `todo9()` trong `Exercise2Runner.java` thực hiện:
  - In danh sách sinh viên học `PRJ301`.
  - In số lượng sinh viên học `HSF302`.
  - In danh sách sinh viên đang active học `PRJ301`.
- [x] Kích hoạt gọi `todo9()` trong `partC()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Truy vấn sinh viên theo mã môn học qua collection courses: `4f1533f` (`feat(derived): find students by enrolled course code Refs: EX2 TODO 9`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo9():
   - Dòng tiêu đề: "===== TODO 9: derived query through collection courses ====="
   - Dòng log mục (a): "-- (a) Students of PRJ301:" gồm 4 sinh viên Le Van Cuong, Nguyen Van An, Tran Thi Binh, Vo Thi Hoa (-> 4 record(s))
   - Dòng log mục (b): "(b) Students of HSF302: 5"
   - Dòng log mục (c): "-- (c) Active students of PRJ301:" gồm 3 sinh viên Nguyen Van An, Tran Thi Binh, Vo Thi Hoa (-> 3 record(s))
2. (Tùy chọn) Chụp câu lệnh Hibernate SQL tự sinh trong console thấy rõ việc JOIN 3 bảng: students -> student_courses -> courses với điều kiện c.code = ?.
-->

---

## Issue: [EX2-TODO 10] Truy vấn từ Inverse Side (Course.students) và loại bỏ trùng lặp với từ khóa Distinct

### 📌 Mô tả yêu cầu (Description)
Triển khai các phương thức truy vấn dẫn xuất từ phía Inverse Side (`CourseRepository`) đi qua quan hệ `students` và điều hướng sâu 2 cấp tới chuyên ngành (`Students_Department_Code`), áp dụng từ khóa `Distinct` để loại bỏ các bản ghi khóa học trùng lặp do phép tích Descartes trong câu lệnh `JOIN`.

### 🎯 Mục tiêu (Objectives)

- Hiểu rõ tính năng của Spring Data JPA: Inverse Side (`mappedBy`) vẫn hoàn toàn hỗ trợ viết Derived Query Methods tự động sinh `JOIN` mà không cần cấu hình thêm.
- Nắm vững kỹ thuật truy vấn lồng sâu 2 cấp qua các quan hệ: `Students_Department_Code` (`Course` -> `students` -> `department` -> `code`).
- Hiểu bản chất sinh SQL khi `JOIN` qua bảng trung gian: khi một môn học có nhiều sinh viên cùng thuộc một khoa theo học, bảng kết quả `JOIN` sẽ sinh ra nhiều dòng chứa cùng một môn học đó.
- Ứng dụng từ khóa `Distinct` (`findDistinctBy...`) để sinh câu lệnh `SELECT DISTINCT` trong SQL, loại bỏ trùng lặp ngay ở tầng cơ sở dữ liệu.
- So sánh ưu thế:
  - TODO 7 (Object Navigation): Phải nạp đối tượng `Student` trước rồi mới gọi `.getCourses()` (phát sinh thêm câu lệnh truy vấn).
  - TODO 10 (Derived Query): Truy vấn trực tiếp từ bảng `courses` thông qua điều kiện mã sinh viên chỉ với duy nhất một câu lệnh SQL.
- Kiểm thử các trường hợp trong `Exercise2Runner.todo10()`:
  - (a) Khóa học của sinh viên `SE002`: `AIL303`, `HSF302`, `PRJ301` (3 môn).
  - (b1) Khóa học sinh viên khoa AI theo học (Không dùng Distinct): trả về 6 bản ghi (môn `AIL303` xuất hiện 3 lần do có 3 sinh viên khoa AI học).
  - (b2) Khóa học sinh viên khoa AI theo học (Có Distinct): loại trùng chính xác còn 4 bản ghi (`AIL303`, `HSF302`, `PRJ301`, `SWP391`).

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo các method trong `CourseRepository`:
  - `List<Course> findByStudents_StudentCodeOrderByCodeAsc(String studentCode)`
  - `List<Course> findByStudents_Department_CodeOrderByCodeAsc(String deptCode)`
  - `List<Course> findDistinctByStudents_Department_CodeOrderByCodeAsc(String deptCode)`
- [x] Khai báo các method trong `CourseService`:
  - `List<Course> findCoursesOfStudent(String studentCode)`
  - `List<Course> findCoursesOfDepartment(String deptCode, boolean distinct)`
- [x] Cài đặt các method trên trong `CourseServiceImpl`, phân nhánh gọi có hoặc không có `Distinct` theo cờ boolean.
- [x] Viết hàm kiểm thử `todo10()` trong `Exercise2Runner.java` thực hiện:
  - In danh sách khóa học của `SE002`.
  - In danh sách khóa học của khoa AI khi không dùng Distinct (6 records).
  - In danh sách khóa học của khoa AI khi dùng Distinct (4 records).
- [x] Kích hoạt gọi `todo10()` trong `partC()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Truy vấn khóa học từ inverse side và loại trùng với Distinct: `e1711fc` (`feat(derived): find courses by student and department with distinct Refs: EX2 TODO 10`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo10():
   - Dòng tiêu đề: "===== TODO 10: derived query from inverse side, Distinct ====="
   - Dòng log mục (a): "-- (a) Courses of SE002:" gồm 3 môn AIL303, HSF302, PRJ301 (-> 3 record(s))
   - Dòng log mục (b1): "-- (b1) Courses of AI students - no Distinct:" có 6 dòng (AIL303 bị lặp lại 3 lần) (-> 6 record(s))
   - Dòng log mục (b2): "-- (b2) Courses of AI students - Distinct:" còn đúng 4 dòng độc nhất AIL303, HSF302, PRJ301, SWP391 (-> 4 record(s))
2. (Tùy chọn) Chụp câu lệnh Hibernate SQL tự sinh trong console thấy rõ từ khóa "select distinct c1_0.id..." khi gọi method có Distinct.
-->

---

## Issue: [EX2-TODO 11] Kiểm tra tập hợp rỗng với IsEmpty và kiểm tra tồn tại liên kết với existsBy trên quan hệ Many-To-Many

### 📌 Mô tả yêu cầu (Description)
Triển khai các phương thức truy vấn dẫn xuất kiểm tra quan hệ rỗng (`IsEmpty`) để tìm sinh viên chưa đăng ký môn học và khóa học chưa có sinh viên, đồng thời tối ưu hóa việc kiểm tra sinh viên đã đăng ký khóa học cụ thể hay chưa bằng phương thức kiểm tra tồn tại `existsBy...And...`.

### 🎯 Mục tiêu (Objectives)

- Ứng dụng từ khóa `IsEmpty` trên collection thuộc cả hai phía:
  - Phía Owning (`StudentRepository`): `findByCoursesIsEmptyOrderByFullNameAsc()` kiểm tra sinh viên có tập hợp môn học rỗng.
  - Phía Inverse (`CourseRepository`): `findByStudentsIsEmpty()` kiểm tra khóa học chưa có sinh viên nào đăng ký.
- Hiểu bản chất câu lệnh SQL sinh bởi Hibernate cho `IsEmpty`: sử dụng mệnh đề hiệu năng cao `WHERE NOT EXISTS (SELECT 1 FROM student_courses WHERE ...)` thay vì phép JOIN thông thường.
- Triển khai phương thức kiểm tra quan hệ tồn tại `existsByStudentCodeAndCourses_Code(studentCode, courseCode)`: Hibernate chỉ lấy tối đa 1 dòng (`TOP 1` / `LIMIT 1`) và trả về `boolean`, tối ưu vượt trội so với việc phải nạp toàn bộ Entity lên bộ nhớ chỉ để kiểm tra tồn tại.
- Kiểm thử các trường hợp trong `Exercise2Runner.todo11()`:
  - (a) Sinh viên chưa đăng ký môn học nào: `Do Van Nam` (`IA003`).
  - (b) Khóa học chưa có ai đăng ký: `MKT101`.
  - (c) Kiểm tra sinh viên `SE001` đăng ký môn `AIL303` (`false`) và `SE002` đăng ký môn `AIL303` (`true`).

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo trong `StudentRepository`:
  - `List<Student> findByCoursesIsEmptyOrderByFullNameAsc()`
  - `boolean existsByStudentCodeAndCourses_Code(String studentCode, String courseCode)`
- [x] Khai báo trong `CourseRepository`:
  - `List<Course> findByStudentsIsEmpty()`
- [x] Khai báo và cài đặt trong `CourseService` / `CourseServiceImpl`:
  - `List<Course> findCoursesWithoutStudents()`
- [x] Khai báo và cài đặt trong `EnrollmentService` / `EnrollmentServiceImpl`:
  - `List<Student> findStudentsWithoutCourses()`
  - `boolean isEnrolled(String studentCode, String courseCode)`
- [x] Viết hàm kiểm thử `todo11()` trong `Exercise2Runner.java` thực hiện:
  - In danh sách sinh viên chưa học môn nào: `Do Van Nam`.
  - In danh sách môn học chưa có sinh viên: `MKT101`.
  - Kiểm tra trạng thái đăng ký của `SE001` và `SE002` với môn `AIL303`.
- [x] Kích hoạt gọi `todo11()` trong `partC()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Kiểm tra tập hợp rỗng và sự tồn tại của quan hệ đăng ký: `361fe11` (`feat(derived): find unenrolled students and empty courses Refs: EX2 TODO 11`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo11():
   - Dòng tiêu đề: "===== TODO 11: IsEmpty, existsBy...And... ====="
   - Dòng log mục (a): "-- (a) Students without courses:" gồm sinh viên Do Van Nam (-> 1 record(s))
   - Dòng log mục (b): "-- (b) Courses without students:" gồm môn MKT101 (-> 1 record(s))
   - Dòng log mục (c):
     + SE001 enrolled AIL303? false
     + SE002 enrolled AIL303? true
2. (Tùy chọn) Chụp câu lệnh Hibernate SQL tự sinh trong console thấy rõ mệnh đề "where not exists (select ... from student_courses ...)" khi gọi findBy...IsEmpty().
-->

---

## Issue: [EX2-TODO 12] Truy vấn tùy chỉnh JPQL JOIN trên quan hệ Many-To-Many lọc sinh viên theo điều kiện điểm GPA

### 📌 Mô tả yêu cầu (Description)
Triển khai phương thức truy vấn tùy biến sử dụng JPQL với annotation `@Query` thực hiện phép `JOIN` trực tiếp qua đường dẫn thuộc tính collection `s.courses` để tìm kiếm danh sách sinh viên có điểm GPA đạt chuẩn trong một khóa học cụ thể.

### 🎯 Mục tiêu (Objectives)

- Hiểu rõ cú pháp JPQL khi làm việc với quan hệ nhiều-nhiều: thực hiện `JOIN` qua đường dẫn trường thực thể `JOIN s.courses c` mà không cần viết điều kiện `ON` hay nhắc tới tên bảng trung gian `student_courses` trong CSDL.
- Kết hợp mệnh đề lọc đa điều kiện: `c.code = :code AND s.gpa >= :minGpa` cùng mệnh đề sắp xếp điểm giảm dần `ORDER BY s.gpa DESC`.
- Sử dụng annotation `@Param` để liên kết tham số an toàn, tránh tấn công SQL/JPQL Injection.
- Thực hiện kiểm tra tính hợp lệ của tham số nghiệp vụ ở tầng Service: ném `IllegalArgumentException` nếu `minGpa` nằm ngoài khoảng `[0, 4]`.
- Kiểm thử trong `Exercise2Runner.todo12()` với môn học `HSF302` và `minGpa = 3.5`:
  - Kết quả mong đợi gồm 3 sinh viên: `Tran Thi Binh` (3.8), `Pham Ngoc Mai` (3.6), `Pham Thi Dung` (3.5).

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo method `findGoodStudentsInCourse` trong `StudentRepository` với annotation `@Query("SELECT s FROM Student s JOIN s.courses c WHERE c.code = :code AND s.gpa >= :minGpa ORDER BY s.gpa DESC")`.
- [x] Khai báo method `List<Student> findGoodStudentsInCourse(String courseCode, double minGpa)` trong `EnrollmentService`.
- [x] Cài đặt method trong `EnrollmentServiceImpl` có validate điều kiện `minGpa in [0, 4]`.
- [x] Viết hàm kiểm thử `todo12()` trong `Exercise2Runner.java` thực hiện in danh sách sinh viên học môn `HSF302` có GPA >= 3.5.
- [x] Kích hoạt gọi `todo12()` trong `partD()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Truy vấn sinh viên giỏi theo khóa học bằng JPQL JOIN: `6ec338e` (`feat(query): find good students of course with jpql join Refs: EX2 TODO 12`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo12():
   - Dòng tiêu đề: "===== TODO 12: JPQL JOIN s.courses ====="
   - Dòng log kết quả: "-- HSF302 & GPA >= 3.5:" gồm 3 sinh viên theo thứ tự GPA giảm dần:
     + Tran Thi Binh (3.8)
     + Pham Ngoc Mai (3.6)
     + Pham Thi Dung (3.5)
     + -> 3 record(s)
2. (Tùy chọn) Chụp câu lệnh Hibernate SQL tự sinh trong console thể hiện phép JOIN students -> student_courses -> courses với mệnh đề WHERE c.code = ? and s.gpa >= ? order by s.gpa desc.
-->

---

## Issue: [EX2-TODO 13] Thống kê khóa học với LEFT JOIN, GROUP BY và DTO Constructor Expression

### 📌 Mô tả yêu cầu (Description)
Triển khai phương thức thống kê tổng hợp số lượng sinh viên đăng ký, số chỗ còn trống và điểm GPA trung bình cho từng khóa học bằng câu truy vấn JPQL sử dụng `LEFT JOIN`, `GROUP BY` kết hợp ánh xạ dữ liệu trực tiếp vào Record DTO (`CourseStatDTO`).

### 🎯 Mục tiêu (Objectives)

- Định nghĩa DTO dạng Java Record `CourseStatDTO(code, name, capacity, enrolled, avgGpa)` bổ sung method tính toán tiện ích `remaining()` (số chỗ còn trống = `capacity - enrolled`).
- Nắm vững cú pháp Constructor Expression trong JPQL: `SELECT new com.hsf302.ch4.dto.CourseStatDTO(...)` để ánh xạ kết quả truy vấn tổng hợp trực tiếp sang DTO bất biến, kiểu dữ liệu tham số constructor khớp chính xác (`COUNT` -> `Long`, `AVG` -> `Double`, `capacity` -> `Integer`).
- Phân biệt bản chất giữa `LEFT JOIN` và `INNER JOIN`: `LEFT JOIN c.students s` đảm bảo giữ lại toàn bộ các khóa học trong kết quả kể cả khóa học chưa có sinh viên nào đăng ký (`MKT101`).
- Hiểu lý do sử dụng `COUNT(s)` thay vì `COUNT(*)`: với `LEFT JOIN`, khóa học không có sinh viên sẽ có `s = null`; `COUNT(s)` trả về 0 (chính xác), trong khi `COUNT(*)` sẽ đếm dòng `null` đó thành 1 (sai nghiệp vụ).
- Đảm bảo tuân thủ ràng buộc SQL chuẩn: toàn bộ các cột không nằm trong hàm tổng hợp (`COUNT`, `AVG`) đều phải xuất hiện trong mệnh đề `GROUP BY c.code, c.name, c.capacity`.
- Kiểm thử trong `Exercise2Runner.todo13()`:
  - `AIL303`: 4/4 (free 0) | avg GPA 3.500
  - `HSF302`: 5/6 (free 1) | avg GPA 3.440
  - `IAA202`: 2/4 (free 2) | avg GPA 2.500
  - `MKT101`: 0/4 (free 4) | avg GPA null (xử lý hiển thị an toàn khi khóa học chưa có sinh viên)
  - `PRJ301`: 4/5 (free 1) | avg GPA 3.350
  - `SWP391`: 3/4 (free 1) | avg GPA 3.567

### ✅ Danh sách công việc (Checklist)

- [x] Tạo record `src/main/java/com/hsf302/ch4/dto/CourseStatDTO.java` với các trường cần thiết và hàm `remaining()`.
- [x] Khai báo query method `getCourseStats()` trong `CourseRepository` với JPQL Constructor Expression `SELECT new ... LEFT JOIN ... GROUP BY ... ORDER BY c.code`.
- [x] Khai báo method `List<CourseStatDTO> getStatistics()` trong `CourseService`.
- [x] Triển khai method trong `CourseServiceImpl` gọi `courseRepository.getCourseStats()`.
- [x] Viết hàm in thống kê `printCourseStats()` và kiểm thử `todo13()` trong `Exercise2Runner.java`.
- [x] Kích hoạt gọi `todo13()` trong `partD()` của `Exercise2Runner` và kiểm tra log định dạng bảng.

### 🔗 Liên kết Commit (Related Commits)

- Thống kê khóa học với LEFT JOIN và DTO projection: `f4af776` (`feat(query): add course statistics with dto projection Refs: EX2 TODO 13`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo13():
   - Dòng tiêu đề: "===== TODO 13: course statistics (LEFT JOIN + GROUP BY + DTO) ====="
   - Danh sách bảng thống kê 6 khóa học được căn chỉnh đẹp mắt:
     + AIL303 | Machine Learning | 4/4 (free 0) | avg GPA 3.500
     + HSF302 | Hibernate & Spring Framework | 5/6 (free 1) | avg GPA 3.440
     + IAA202 | Risk Management in Information Systems | 2/4 (free 2) | avg GPA 2.500
     + MKT101 | Marketing Principles | 0/4 (free 4) | avg GPA null
     + PRJ301 | Java Web Application Development | 4/5 (free 1) | avg GPA 3.350
     + SWP391 | Software Development Project | 3/4 (free 1) | avg GPA 3.567
-->

---

## Issue: [EX2-TODO 14] Thống kê tổng số tín chỉ theo sinh viên sử dụng GROUP BY, SUM và lọc nhóm với HAVING

### 📌 Mô tả yêu cầu (Description)
Triển khai phương thức tính tổng số tín chỉ và số môn học đã đăng ký của từng sinh viên bằng câu truy vấn JPQL tổng hợp, gom nhóm theo sinh viên (`GROUP BY`) và áp dụng mệnh đề `HAVING` để lọc các sinh viên có tổng số tín chỉ đạt mức tối thiểu (`minCredits`).

### 🎯 Mục tiêu (Objectives)

- Định nghĩa DTO dạng Java Record `StudentCreditDTO(studentCode, fullName, courseCount, totalCredits)` để chứa dữ liệu tổng hợp.
- Phân biệt sự khác biệt bản chất giữa mệnh đề `WHERE` và `HAVING`:
  - `WHERE`: Lọc từng dòng dữ liệu trước khi gom nhóm.
  - `HAVING`: Lọc trên kết quả của các hàm tổng hợp (`SUM(c.credits) >= :minCredits`) sau khi đã gom nhóm.
- Nắm vững quy tắc kiểu dữ liệu trong Hibernate 6: hàm `SUM` trên cột kiểu `Integer` (`c.credits`) sẽ trả về giá trị kiểu `Long`, do đó tham số `:minCredits` và trường `totalCredits` trong DTO cần khai báo kiểu `Long` để tránh lỗi lệch kiểu.
- Sắp xếp kết quả theo thứ tự tổng số tín chỉ giảm dần (`ORDER BY SUM(c.credits) DESC`), nếu bằng nhau sắp xếp theo họ tên sinh viên tăng dần (`s.fullName ASC`).
- Kiểm thử trong `Exercise2Runner.todo14()` với `minCredits = 7`:
  - `SE001` | Nguyen Van An: 3 môn | 10 tín chỉ
  - `AI003` | Vo Thi Hoa: 3 môn | 10 tín chỉ
  - `SE002` | Tran Thi Binh: 3 môn | 9 tín chỉ
  - `SE004` | Nguyen Thi Mai: 2 môn | 7 tín chỉ

### ✅ Danh sách công việc (Checklist)

- [x] Tạo record `src/main/java/com/hsf302/ch4/dto/StudentCreditDTO.java`.
- [x] Khai báo query method `getCreditSummary(@Param("minCredits") long minCredits)` trong `StudentRepository` với cú pháp JPQL Constructor Expression kết hợp `GROUP BY` và `HAVING`.
- [x] Khai báo method `List<StudentCreditDTO> getCreditSummary(int minCredits)` trong `EnrollmentService`.
- [x] Cài đặt method trong `EnrollmentServiceImpl` kèm validate `minCredits >= 0`.
- [x] Viết hàm kiểm thử `todo14()` trong `Exercise2Runner.java` thực hiện in danh sách sinh viên có tổng tín chỉ >= 7.
- [x] Kích hoạt gọi `todo14()` trong `partD()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Thống kê tín chỉ sinh viên với GROUP BY và HAVING: `c653916` (`feat(query): summarize student credits with group by having Refs: EX2 TODO 14`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo14():
   - Dòng tiêu đề: "===== TODO 14: total credits per student (GROUP BY + HAVING) ====="
   - Danh sách 4 sinh viên có tổng tín chỉ >= 7 theo thứ tự giảm dần:
     + SE001 | Nguyen Van An | 3 course(s) | 10 credits
     + AI003 | Vo Thi Hoa | 3 course(s) | 10 credits
     + SE002 | Tran Thi Binh | 3 course(s) | 9 credits
     + SE004 | Nguyen Thi Mai | 2 course(s) | 7 credits
2. (Tùy chọn) Chụp câu lệnh Hibernate SQL tự sinh thấy rõ mệnh đề "group by s1_0.student_code, s1_0.full_name having sum(c1_1.credits)>=? order by sum(c1_1.credits) desc, s1_0.full_name".
-->

---

## Issue: [EX2-TODO 15] Đếm kích thước tập hợp Collection với hàm JPQL SIZE() không cần GROUP BY

### 📌 Mô tả yêu cầu (Description)
Triển khai các phương thức truy vấn JPQL sử dụng hàm tích hợp `SIZE()` trên tập hợp collection để tìm các khóa học đã đủ hoặc vượt sĩ số tối đa (`SIZE(c.students) >= c.capacity`) và tìm các sinh viên đăng ký nhiều hơn N khóa học (`SIZE(s.courses) > :n`).

### 🎯 Mục tiêu (Objectives)

- Hiểu rõ cơ chế hoạt động của hàm `SIZE()` trong JPQL: là hàm chuyên dụng để lấy số lượng phần tử của một collection relationship mà không cần viết mệnh đề `JOIN` hay `GROUP BY`.
- Hiểu cách Hibernate biên dịch `SIZE()` sang câu lệnh SQL native: Hibernate tự động sinh câu truy vấn con (correlated subquery):
  - Phía Course: `(SELECT COUNT(*) FROM student_courses sc WHERE sc.course_id = c.id) >= c.capacity`
  - Phía Student: `(SELECT COUNT(*) FROM student_courses sc WHERE sc.student_id = s.id) > ?`
- Triển khai 2 chức năng nghiệp vụ:
  - `findFullCourses()`: Tìm các lớp học đã đầy chỗ, sắp xếp tăng dần theo mã môn học (`code ASC`).
  - `findStudentsWithMoreThan(int n)`: Tìm các sinh viên đăng ký nhiều hơn N môn học, sắp xếp theo họ tên (`fullName ASC`).
- Kiểm thử trong `Exercise2Runner.todo15()`:
  - (a) Khóa học đã kín chỗ: `AIL303` (sĩ số 4/4).
  - (b) Sinh viên đăng ký nhiều hơn 2 môn (`n = 2`): `Nguyen Van An` (3 môn), `Tran Thi Binh` (3 môn), `Vo Thi Hoa` (3 môn).

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo method `findFullCourses()` trong `CourseRepository` với `@Query("SELECT c FROM Course c WHERE SIZE(c.students) >= c.capacity ORDER BY c.code")`.
- [x] Khai báo method `findStudentsWithMoreThanNCourses(@Param("n") int n)` trong `StudentRepository` với `@Query("SELECT s FROM Student s WHERE SIZE(s.courses) > :n ORDER BY s.fullName")`.
- [x] Khai báo và cài đặt method `findFullCourses()` trong `CourseService` / `CourseServiceImpl`.
- [x] Khai báo và cài đặt method `findStudentsWithMoreThan(int n)` trong `EnrollmentService` / `EnrollmentServiceImpl` có validate `n >= 0`.
- [x] Viết hàm kiểm thử `todo15()` trong `Exercise2Runner.java` thực hiện:
  - In danh sách khóa học đầy chỗ: `AIL303`.
  - In danh sách sinh viên đăng ký nhiều hơn 2 khóa học: `Nguyen Van An`, `Tran Thi Binh`, `Vo Thi Hoa`.
- [x] Kích hoạt gọi `todo15()` trong `partD()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Tìm khóa học đầy chỗ và sinh viên bận rộn với hàm SIZE: `b6f6d83` (`feat(query): find full courses and busy students with size Refs: EX2 TODO 15`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo15():
   - Dòng tiêu đề: "===== TODO 15: SIZE() on collections ====="
   - Dòng log mục (a): "-- (a) Full courses:" gồm môn AIL303 | Machine Learning (-> 1 record(s))
   - Dòng log mục (b): "-- (b) Students with more than 2 courses:" gồm 3 sinh viên:
     + Nguyen Van An (SE001)
     + Tran Thi Binh (SE002)
     + Vo Thi Hoa (AI003)
     + -> 3 record(s)
2. (Tùy chọn) Chụp câu lệnh Hibernate SQL tự sinh trong console thấy rõ câu lệnh subquery select count(*) from student_courses...
-->

---

## Issue: [EX2-TODO 16] Tái hiện LazyInitializationException và xử lý Eager Loading an toàn với JOIN FETCH cùng @EntityGraph

### 📌 Mô tả yêu cầu (Description)
Tái hiện và phân tích nguyên nhân ngoại lệ `LazyInitializationException` khi truy xuất tập hợp Lazy Collection ngoài phạm vi Transaction/Session, đồng thời áp dụng 2 giải pháp tối ưu: câu lệnh JPQL `LEFT JOIN FETCH` và declarative annotation `@EntityGraph` để nạp sẵn dữ liệu chỉ trong một câu lệnh SQL duy nhất.

### 🎯 Mục tiêu (Objectives)

- Phân tích cơ chế và nguyên nhân gây lỗi `LazyInitializationException`:
  - Khi phương thức của Service kết thúc, Transaction commit và Hibernate Session / Persistence Context đóng lại.
  - Đối tượng Entity trở về trạng thái Detached, tập hợp collection (`s.getCourses()`) vẫn là một `PersistentSet` chưa được khởi tạo.
  - Việc truy xuất collection này ở tầng Runner ngoài Session sẽ ném lỗi: `failed to lazily initialize a collection ... could not initialize proxy - no Session`.
- Nắm vững giải pháp 1 - JPQL `LEFT JOIN FETCH`:
  - Viết câu truy vấn `SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.studentCode = :code`.
  - Hibernate chủ động JOIN bảng `students` -> `student_courses` -> `courses` và khởi tạo sẵn collection `courses` ngay trong 1 câu SELECT.
  - Sử dụng `LEFT JOIN FETCH` để bảo toàn kết quả nếu sinh viên chưa đăng ký môn học nào.
- Nắm vững giải pháp 2 - Spring Data JPA `@EntityGraph`:
  - Khai báo `@EntityGraph(attributePaths = "students")` trên phương thức derived query `findWithStudentsByCode(String code)` của `CourseRepository`.
  - Cho phép cấu hình linh hoạt nạp trước thuộc tính quan hệ (Ad-hoc Fetch Plan) mà không cần viết câu truy vấn JPQL thủ công.
- Kiểm thử các trường hợp trong `Exercise2Runner.todo16()`:
  - (a) Bắt và in thông điệp ngoại lệ `LazyInitializationException` khi gọi `s.getCourses().size()` ngoài Session.
  - (b) Nạp thành công danh sách môn học của sinh viên `SE001` bằng `JOIN FETCH`: `HSF302`, `PRJ301`, `SWP391`.
  - (c) Nạp thành công danh sách sinh viên của môn học `SWP391` bằng `@EntityGraph`: `Nguyen Thi Mai`, `Nguyen Van An`, `Vo Thi Hoa`.

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo query method `findByStudentCodeWithCourses` trong `StudentRepository` với `@Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.studentCode = :code")`.
- [x] Khai báo method `findWithStudentsByCode(String code)` trong `CourseRepository` kèm annotation `@EntityGraph(attributePaths = "students")`.
- [x] Khai báo và cài đặt method `getStudentWithCourses(String studentCode)` trong `EnrollmentService` / `EnrollmentServiceImpl`.
- [x] Khai báo và cài đặt method `getWithStudents(String code)` trong `CourseService` / `CourseServiceImpl`.
- [x] Viết hàm kiểm thử `todo16()` trong `Exercise2Runner.java` thực hiện:
  - Khối try-catch bắt `LazyInitializationException` khi truy cập `s.getCourses()` ngoài Session.
  - Kiểm tra kết quả nạp trước với `JOIN FETCH` (SE001).
  - Kiểm tra kết quả nạp trước với `@EntityGraph` (SWP391).
- [x] Kích hoạt gọi `todo16()` trong `partD()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Xử lý Lazy Loading với JOIN FETCH và EntityGraph: `40b23c0` (`fix(query): load collections with join fetch and entity graph Refs: EX2 TODO 16`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo16():
   - Dòng tiêu đề: "===== TODO 16: LazyInitializationException, JOIN FETCH, @EntityGraph ====="
   - Dòng log mục (a) bắt thành công ngoại lệ:
     + (a) Caught: LazyInitializationException
     + failed to lazily initialize a collection ... could not initialize proxy - no Session
   - Dòng log mục (b) dùng JOIN FETCH:
     + (b) SE001 - Nguyen Van An
     + Danh sách môn học: HSF302, PRJ301, SWP391
   - Dòng log mục (c) dùng @EntityGraph:
     + (c) SWP391 - Software Development Project
     + Danh sách sinh viên: Nguyen Thi Mai, Nguyen Van An, Vo Thi Hoa
2. (Tùy chọn) Chụp câu lệnh Hibernate SQL tự sinh trong console cho mục (b) và (c) thể hiện chỉ 1 câu SELECT duy nhất chứa LEFT JOIN sang bảng trung gian và bảng đích.
-->

---

## Issue: [EX2-TODO 17] Truy vấn Native SQL trực tiếp trên bảng trung gian kết hợp Interface-based Projection

### 📌 Mô tả yêu cầu (Description)
Triển khai câu truy vấn Native SQL (`nativeQuery = true`) thao tác trực tiếp trên bảng vật lý `courses` và bảng liên kết trung gian `student_courses`, sử dụng cú pháp đặc thù `TOP (:n)` của SQL Server kết hợp với Spring Data Interface-based Projection (`CourseEnrollmentCount`) để lấy top N khóa học có số lượng sinh viên đăng ký nhiều nhất.

### 🎯 Mục tiêu (Objectives)

- Hiểu rõ sự khác biệt giữa JPQL và Native SQL: Native SQL cho phép thao tác trực tiếp với các bảng vật lý và tên cột trong CSDL (`courses`, `student_courses`, `student_id`, `course_id`), là cách tiếp cận duy nhất khi cần tối ưu trực tiếp trên bảng trung gian không có Entity ánh xạ.
- Sử dụng cú pháp phân trang đặc thù của Microsoft SQL Server: `SELECT TOP (:n) ...` với tham số được đặt trong dấu ngoặc đơn.
- Ứng dụng kỹ thuật Interface-based Projection (`CourseEnrollmentCount`): các alias `AS code`, `AS name`, `AS enrolled` trong câu SELECT phải khớp chính xác với tên các getter (`getCode()`, `getName()`, `getEnrolled()`).
- Tận dụng cơ chế Spring Data JPA tự động ép kiểu: hàm tổng hợp `COUNT` của SQL Server trả về kiểu `int`, Spring tự động chuyển đổi an toàn sang kiểu `Long` của getter.
- Sử dụng mệnh đề sắp xếp đa tiêu chí `ORDER BY enrolled DESC, c.code` để đảm bảo thứ tự kết quả luôn ổn định và tất định khi các môn học có số lượng sinh viên bằng nhau.
- Kiểm thử trong `Exercise2Runner.todo17()` với `n = 3`:
  - `HSF302` | Hibernate & Spring Framework: 5 sinh viên
  - `AIL303` | Machine Learning: 4 sinh viên
  - `PRJ301` | Java Web Application Development: 4 sinh viên

### ✅ Danh sách công việc (Checklist)

- [x] Tạo interface projection `src/main/java/com/hsf302/ch4/dto/CourseEnrollmentCount.java` với các getter tương ứng.
- [x] Khai báo query method `findTopEnrolledNative(@Param("n") int n)` trong `CourseRepository` với `@Query(value = "...", nativeQuery = true)`.
- [x] Khai báo method `List<CourseEnrollmentCount> findTopEnrolled(int n)` trong `CourseService`.
- [x] Triển khai method trong `CourseServiceImpl` kèm validate `n > 0`.
- [x] Viết hàm kiểm thử `todo17()` trong `Exercise2Runner.java` in danh sách Top 3 môn học đông sinh viên nhất.
- [x] Kích hoạt gọi `todo17()` trong `partD()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Truy vấn top khóa học đông sinh viên bằng Native SQL: `3854967` (`feat(query): get top enrolled courses with native sql Refs: EX2 TODO 17`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo17():
   - Dòng tiêu đề: "===== TODO 17: native SQL on join table - top 3 enrolled courses ====="
   - Danh sách top 3 khóa học đông sinh viên nhất:
     + HSF302 | Hibernate & Spring Framework | 5 student(s)
     + AIL303 | Machine Learning | 4 student(s)
     + PRJ301 | Java Web Application Development | 4 student(s)
2. (Tùy chọn) Chụp câu lệnh Native SQL in ra trong console thể hiện cú pháp SELECT TOP (?) c.code AS code... FROM courses c LEFT JOIN student_courses sc...
-->

---

## Issue: [EX2-TODO 18] Chiếu dữ liệu đa bảng với Interface-based Projection tổng hợp bảng đăng ký học tập

### 📌 Mô tả yêu cầu (Description)
Triển khai phương thức truy vấn JPQL kết hợp đồng thời quan hệ `@ManyToOne` (`Department`) và quan hệ `@ManyToMany` (`Course`) xuất ra danh sách đăng ký học tập theo khoa, ánh xạ trực tiếp sang interface projection `EnrollmentView` để tối ưu hóa hiệu năng và loại bỏ nguy cơ `LazyInitializationException`.

### 🎯 Mục tiêu (Objectives)

- Xây dựng Spring Data Interface-based Projection `EnrollmentView` chứa các trường thông tin: `getStudentCode()`, `getFullName()`, `getCourseCode()`, `getCourseName()`, `getCredits()`.
- Viết câu lệnh JPQL JOIN đồng thời 3 thực thể liên kết: `Student s JOIN s.department d JOIN s.courses c WHERE d.code = :deptCode ORDER BY s.studentCode, c.code`.
- Nắm vững quy tắc đặt alias trong JPQL Projection: các tên alias (`AS studentCode`, `AS fullName`, `AS courseCode`, `AS courseName`, `AS credits`) bắt buộc phải khớp chính xác với tên thuộc tính trong getter của interface để Spring Data ánh xạ dữ liệu (nếu sai alias getter sẽ nhận giá trị `null`).
- Hiểu rõ lợi thế vượt trội của Interface Projection: Hibernate chỉ SELECT đúng các cột dữ liệu cần hiển thị, hoàn toàn không nạp toàn bộ thực thể Entity vào Persistence Context, từ đó giúp tiết kiệm bộ nhớ và triệt tiêu hoàn toàn rủi ro ngoại lệ Lazy Loading.
- Kiểm thử trong `Exercise2Runner.todo18()` với khoa `AI` (trả về đúng 6 bản ghi đăng ký môn học của sinh viên khoa AI):
  - `AI001` | Pham Thi Dung: `AIL303` (3), `HSF302` (3)
  - `AI002` | Hoang Van Em: `AIL303` (3)
  - `AI003` | Vo Thi Hoa: `AIL303` (3), `PRJ301` (3), `SWP391` (4)

### ✅ Danh sách công việc (Checklist)

- [x] Tạo interface projection `src/main/java/com/hsf302/ch4/dto/EnrollmentView.java`.
- [x] Khai báo query method `findEnrollmentsOfDepartment(@Param("deptCode") String deptCode)` trong `StudentRepository` với cú pháp JPQL JOIN 3 thực thể và đặt alias tương ứng.
- [x] Khai báo method `List<EnrollmentView> getEnrollmentsOfDepartment(String deptCode)` trong `EnrollmentService`.
- [x] Cài đặt method trong `EnrollmentServiceImpl` gọi `studentRepository.findEnrollmentsOfDepartment(deptCode)`.
- [x] Viết hàm kiểm thử `todo18()` trong `Exercise2Runner.java` in danh sách chi tiết các môn đăng ký của sinh viên khoa `AI`.
- [x] Kích hoạt gọi `todo18()` trong `partD()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Thêm Interface Projection hiển thị danh sách đăng ký theo khoa: `55c77ff` (`feat(query): add enrollment view interface projection Refs: EX2 TODO 18`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo18():
   - Dòng tiêu đề: "===== TODO 18: interface projection - enrollments of department AI ====="
   - Bảng danh sách 6 lượt đăng ký của sinh viên khoa AI:
     + AI001 | Pham Thi Dung | AIL303 | Machine Learning | 3
     + AI001 | Pham Thi Dung | HSF302 | Hibernate & Spring Framework | 3
     + AI002 | Hoang Van Em | AIL303 | Machine Learning | 3
     + AI003 | Vo Thi Hoa | AIL303 | Machine Learning | 3
     + AI003 | Vo Thi Hoa | PRJ301 | Java Web Application Development | 3
     + AI003 | Vo Thi Hoa | SWP391 | Software Development Project | 4
2. (Tùy chọn) Chụp câu lệnh Hibernate SQL tự sinh trong console thể hiện câu lệnh SELECT các trường alias cụ thể từ bảng students JOIN departments JOIN student_courses JOIN courses.
-->

---

## Issue: [EX2-TODO 19] Phân trang danh sách sinh viên theo môn học kết hợp JOIN và countQuery tùy chỉnh

### 📌 Mô tả yêu cầu (Description)
Triển khai phương thức phân trang danh sách sinh viên theo khóa học sử dụng cơ chế `Pageable` và kiểu trả về `Page<Student>`, khai báo tường minh thuộc tính `countQuery` trong `@Query` để tối ưu hóa hiệu năng và kiểm soát chính xác câu lệnh đếm tổng số bản ghi trong quan hệ Many-To-Many.

### 🎯 Mục tiêu (Objectives)

- Hiểu rõ cơ chế phân trang phía Server (Server-side Pagination): Spring Data JPA tự động dịch `Pageable` sang câu lệnh SQL Server chuẩn hiệu năng cao: `OFFSET ? ROWS FETCH NEXT ? ROWS ONLY`.
- Nắm vững tầm quan trọng của `countQuery`: Khi câu truy vấn chính có `JOIN`, việc khai báo tường minh `countQuery = "SELECT COUNT(s) FROM Student s JOIN s.courses c WHERE c.code = :code"` giúp Hibernate không phải sinh câu đếm tự động phức tạp, đảm bảo tính chuẩn xác và tối ưu hiệu suất thực thi.
- Phân biệt với `JOIN FETCH`: Tuyệt đối không dùng `JOIN FETCH` collection kèm với phân trang vì Hibernate sẽ phải nạp toàn bộ dữ liệu vào bộ nhớ RAM rồi mới cắt trang (Memory Pagination) kèm cảnh báo nguy hiểm `HHH90003004`.
- Sử dụng đối tượng `PageRequest.of(pageIndex, size, Sort.by("fullName"))` để phân trang kết hợp sắp xếp theo họ tên sinh viên.
- Duyệt qua toàn bộ các trang bằng vòng lặp `do-while` với điều kiện `page.hasNext()`.
- Kiểm thử trong `Exercise2Runner.todo19()` với môn `HSF302`, kích thước trang `size = 2`:
  - Page 0: `Bui Thi Lan`, `Nguyen Thi Mai`
  - Page 1: `Nguyen Van An`, `Pham Thi Dung`
  - Page 2: `Tran Thi Binh`
  - Tổng số bản ghi (`totalElements`): `5`, tổng số trang (`totalPages`): `3`.

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo query method `findPageByCourseCode` trong `StudentRepository` với `@Query` chỉ định rõ `value` và `countQuery`.
- [x] Khai báo method `Page<Student> findStudentsInCoursePage(String courseCode, int pageIndex, int size)` trong `EnrollmentService`.
- [x] Cài đặt method trong `EnrollmentServiceImpl` có validate điều kiện `pageIndex >= 0 && size > 0`, tạo `PageRequest` kèm sắp xếp `fullName ASC`.
- [x] Viết hàm kiểm thử `todo19()` trong `Exercise2Runner.java` duyệt in toàn bộ các trang của môn `HSF302` cùng thông số `totalElements` và `totalPages`.
- [x] Kích hoạt gọi `todo19()` trong `partD()` của `Exercise2Runner` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Phân trang sinh viên theo khóa học với JOIN và countQuery: `bbb2bd9` (`feat(query): paginate students of course Refs: EX2 TODO 19`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo19():
   - Dòng tiêu đề: "===== TODO 19: paginate students of HSF302 (size 2, order by fullName) ====="
   - Chi tiết 3 trang dữ liệu được duyệt qua vòng lặp:
     + -- Page 0: Bui Thi Lan, Nguyen Thi Mai (-> 2 record(s))
     + -- Page 1: Nguyen Van An, Pham Thi Dung (-> 2 record(s))
     + -- Page 2: Tran Thi Binh (-> 1 record(s))
   - Dòng tổng kết: "totalElements = 5, totalPages = 3"
2. (Tùy chọn) Chụp 2 câu lệnh SQL sinh bởi Hibernate: 1 câu SELECT kèm "offset ? rows fetch next ? rows only" và 1 câu SELECT count_big(s1_0.id)...
-->

---

## Issue: [EX2-TODO 25] Tìm kiếm động với Spring Data JPA Specification (Bonus)

### 📌 Mô tả yêu cầu (Description)

Hiện thực tính năng tìm kiếm động (Dynamic Query / Filter) thông tin sinh viên kết hợp đa điều kiện bằng Spring Data JPA `Specification` và JPA Criteria API.

Hệ thống cho phép người dùng tùy chọn lọc sinh viên theo:
- **`courseCode`**: Mã môn học mà sinh viên đang đăng ký (vd: `"HSF302"`). Cần thực hiện `JOIN` sang bảng `Course`.
- **`semester`**: Học kỳ của môn học (vd: `"SU26"`, `"FA26"`). Cần thực hiện `JOIN` sang bảng `Course`.
- **`deptCode`**: Mã khoa của sinh viên (vd: `"SE"`, `"AI"`). Lọc qua quan hệ `@ManyToOne` với `Department`.
- **`minGpa`**: Điểm GPA tối thiểu (vd: `>= 3.5`).

Nếu một tiêu chí lọc mang giá trị `null` hoặc chuỗi rỗng (`isBlank()`), điều kiện đó sẽ tự động được bỏ qua (`null predicate`) mà không làm gãy câu truy vấn SQL. Đặc biệt, khi thực hiện `JOIN` collection qua Criteria API, phải kích hoạt `query.distinct(true)` để loại bỏ dữ liệu sinh viên bị nhân đôi do phép tích Descartes (Cartesian product).

### 🎯 Mục tiêu (Objectives)

- Hiểu và áp dụng JPA Criteria API kết hợp `Specification<Student>` để xây dựng các câu truy vấn động an toàn type-safe, không nối chuỗi SQL/JPQL thủ công.
- Nắm vững cơ chế `Specification.where().and(...)`: predicate trả về `null` tương đương với điều kiện luôn đúng, Spring Data JPA tự động loại bỏ điều kiện đó trong mệnh đề `WHERE`.
- Nắm vững kỹ thuật xử lý `JOIN` quan hệ Many-to-Many trong Criteria API: `root.join("courses")` và giải quyết triệt để lỗi nhân bản dòng (duplicate rows) bằng `query.distinct(true)`.
- Đảm bảo interface `StudentRepository` kế thừa `JpaSpecificationExecutor<Student>` để hỗ trợ các hàm `findAll(spec, sort)`.
- Đóng gói các predicates tái sử dụng trong utility class `EnrollmentSpecs` (chứa các static factory methods).
- Kiểm thử thành công các kịch bản tìm kiếm động trong `Exercise2Runner.todo25()`:
  - `search(null, "SU26", null, null)`: Lọc sinh viên học kỳ Summer 2026.
  - `search("HSF302", null, "SE", 3.5)`: Lọc sinh viên khoa SE, học HSF302 và có GPA >= 3.5.
  - `search(null, "FA26", "AI", null)`: Lọc sinh viên khoa AI trong học kỳ Fall 2026.

### ✅ Danh sách công việc (Checklist)

- [x] Tạo tiện ích `EnrollmentSpecs` trong package `com.hsf302.ch4.specification` với private constructor.
- [x] Định nghĩa predicate `enrolledIn(String courseCode)` sử dụng `root.join("courses")` và `query.distinct(true)`.
- [x] Định nghĩa predicate `inSemester(String semester)` sử dụng `root.join("courses")` và `query.distinct(true)`.
- [x] Định nghĩa predicate `inDepartment(String deptCode)` duyệt quan hệ `root.get("department").get("code")`.
- [x] Định nghĩa predicate `gpaAtLeast(Double minGpa)` kiểm tra điều kiện `cb.greaterThanOrEqualTo`.
- [x] Khai báo method `List<Student> search(String courseCode, String semester, String deptCode, Double minGpa)` trong `EnrollmentService`.
- [x] Cài đặt method `search` trong `EnrollmentServiceImpl` kết hợp các Specs qua `.and(...)` và gọi `studentRepository.findAll(spec, Sort.by("fullName"))`.
- [x] Viết hàm `todo25()` trong `Exercise2Runner` kiểm thử 3 kịch bản tìm kiếm khác nhau và in kết quả.
- [x] Cập nhật hàm `bonus()` gọi `todo25()` và kích hoạt chạy trong method `run()` của runner.

### 🔗 Liên kết Commit (Related Commits)

- Thêm tìm kiếm động bằng JPA Specification: `b8cc041` (`feat(spec): add dynamic enrollment search with specification Refs: EX2 TODO 25`)
- Kích hoạt phương thức bonus() trong Exercise2Runner: `f4ebad8` (`feat(spec): add dynamic enrollment search with specification Refs: EX2 TODO 25`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo25():
   - Dòng tiêu đề: "===== TODO 25 (Bonus): Specification search ====="
   - Kết quả lọc search(null, SU26, null, null):
     Danh sách các sinh viên có đăng ký môn trong kỳ SU26 (không bị trùng lặp tên)
   - Kết quả lọc search(HSF302, null, SE, 3.5):
     Danh sách sinh viên khoa SE, đăng ký HSF302 có GPA >= 3.5
   - Kết quả lọc search(null, FA26, AI, null):
     Danh sách sinh viên khoa AI có môn trong kỳ FA26
2. (Tùy chọn) Chụp câu lệnh SQL sinh bởi Hibernate với mệnh đề "SELECT DISTINCT ... FROM student ... JOIN ... WHERE ..."
-->

---

## Issue: [EX2-TODO 20] Đăng ký môn học kèm kiểm tra quy tắc nghiệp vụ (Business Rules)

### 📌 Mô tả yêu cầu (Description)

Hiện thực nghiệp vụ đăng ký khóa học (`enroll`) cho sinh viên với cơ chế giao dịch (`@Transactional`) và kiểm tra nghiêm ngặt toàn bộ các quy tắc nghiệp vụ (Business Rules) trước khi cập nhật dữ liệu vào bảng trung gian `student_courses`:

1. **Sinh viên phải tồn tại và đang hoạt động (`active == true`)**: Nếu sinh viên không tồn tại thì báo lỗi tìm kiếm, nếu `active == false` thì từ chối đăng ký (`IllegalStateException`).
2. **Khóa học phải tồn tại**: Nếu mã khóa học không có trong hệ thống thì ném ngoại lệ.
3. **Không được đăng ký trùng lặp**: Nếu sinh viên đã có trong danh sách khóa học (`s.getCourses().contains(c)`), báo lỗi sinh viên đã đăng ký môn học này.
4. **Không vượt quá sĩ số tối đa (`capacity`)**: So sánh số lượng sinh viên hiện tại đã đăng ký với chỉ tiêu mở lớp (`c.getStudents().size() >= c.getCapacity()`). Nếu lớp đã đầy thì từ chối đăng ký.
5. **Cập nhật quan hệ 2 chiều an toàn**: Sử dụng helper method `s.enroll(c)` để đồng bộ state trong bộ nhớ cho cả `Student` và `Course`, từ đó Hibernate Dirty Checking tự động sinh câu lệnh `INSERT INTO student_courses` khi transaction commit.

### 🎯 Mục tiêu (Objectives)

- Hiểu rõ cơ chế quản trị Transaction trong Spring Boot với `@Transactional`: Quá trình đọc, kiểm tra và ghi đều nằm trọn vẹn trong một phiên làm việc của EntityManager.
- Nắm vững cơ chế Dirty Checking của JPA / Hibernate: Không cần gọi hàm `save()` tường minh, chỉ cần thay đổi collection trên Managed Entity, Hibernate sẽ tự động so sánh snapshot và flush câu lệnh `INSERT` xuống bảng trung gian.
- Tách nhỏ logic kiểm tra nghiệp vụ vào private helper method `checkAndEnroll(Student s, Course c)` để tái sử dụng tối đa cho các tác vụ phức tạp tiếp theo (như đổi lớp ở TODO 22).
- Kiểm thử các kịch bản thực tế trong `Exercise2Runner.todo20()`:
  - `IA003 -> MKT101`: Thành công hợp lệ (sinh viên `IA003` ghi danh vào `MKT101`).
  - `SE001 -> PRJ301`: Báo lỗi vì sinh viên `SE001` đang ở trạng thái không hoạt động (`inactive`).
  - `SE004 -> AIL303`: Báo lỗi trùng lặp vì sinh viên `SE004` đã đăng ký môn `AIL303` trước đó.
  - `SE003 -> HSF302`: Báo lỗi lớp học đã đầy sĩ số (`HSF302 is full (5/5)`).
  - `XX999 -> HSF302`: Báo lỗi không tìm thấy sinh viên với mã `XX999`.

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo method `void enroll(String studentCode, String courseCode)` trong `EnrollmentService`.
- [x] Tạo private helper method `checkAndEnroll(Student s, Course c)` trong `EnrollmentServiceImpl` kiểm tra đầy đủ 3 điều kiện: `s.isActive()`, `s.getCourses().contains(c)`, và `c.getStudents().size() >= c.getCapacity()`.
- [x] Cài đặt method `enroll` được đánh dấu `@Transactional`, lấy thực thể qua `getStudent` / `getCourse` và gọi `checkAndEnroll`.
- [x] Sử dụng helper method `s.enroll(c)` để đồng bộ hóa quan hệ 2 chiều giữa 2 entity.
- [x] Viết hàm `todo20()` trong `Exercise2Runner` sử dụng tiện ích `attempt(...)` để kiểm thử lần lượt cả 5 trường hợp (1 hợp lệ, 4 vi phạm nghiệp vụ).
- [x] In danh sách môn học của `IA003` và đếm sĩ số môn `MKT101` sau khi đăng ký để xác nhận dữ liệu đã được cập nhật chính xác.
- [x] Kích hoạt `partE()` và hàm `todo20()` trong runner để kiểm tra log thực thi.

### 🔗 Liên kết Commit (Related Commits)

- Cài đặt nghiệp vụ đăng ký môn học có kiểm tra business rules: `e99ebd7` (`feat(enrollment): enroll student with business rules Refs: EX2 TODO 20`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo20():
   - Dòng tiêu đề: "===== TODO 20: enroll with business rules ====="
   - [OK] enroll IA003 -> MKT101: OK
   - [FAIL] enroll SE001 -> PRJ301: Student SE001 is inactive
   - [FAIL] enroll SE004 -> AIL303: Student SE004 already enrolled in AIL303
   - [FAIL] enroll SE003 -> HSF302: Course HSF302 is full (5/5)
   - [FAIL] enroll XX999 -> HSF302: Student not found with code: XX999
   - In danh sách Courses of IA003: xuất hiện thêm môn MKT101
   - Sĩ số Students of MKT101 tăng từ 1 lên 2
2. (Tùy chọn) Chụp câu lệnh INSERT sinh bởi Hibernate: "insert into student_courses (student_id, course_id) values (?, ?)"
-->

---

## Issue: [EX2-TODO 21] Hủy đăng ký môn học (Unenroll) và bảo toàn tính toàn vẹn Entity

### 📌 Mô tả yêu cầu (Description)

Hiện thực nghiệp vụ hủy đăng ký môn học (`unenroll`) cho sinh viên theo phương thức an toàn và bảo toàn dữ liệu:

1. **Kiểm tra trạng thái đăng ký**: Kiểm tra xem sinh viên có đang thực sự theo học môn học đó không (`!s.getCourses().contains(c)`). Nếu không, ném ra ngoại lệ `IllegalStateException("Student ... is not enrolled in ...")`.
2. **Cập nhật quan hệ 2 chiều qua Helper Method**: Sử dụng helper method `s.unenroll(c)` (đã cài đặt ở TODO 3) để xóa phần tử `Course` khỏi collection `s.courses`, đồng thời xóa `Student` khỏi collection `c.students`.
3. **Hiểu bản chất Cascade trên quan hệ Many-to-Many**: Tuyệt đối không cấu hình `CascadeType.REMOVE` trên quan hệ Many-to-Many giữa `Student` và `Course`. Việc gọi `s.unenroll(c)` chỉ được phép kích hoạt câu lệnh `DELETE` trên đúng 1 dòng tương ứng trong bảng trung gian `student_courses`, cả 2 thực thể gốc `Student` và `Course` vẫn phải tồn tại nguyên vẹn trong cơ sở dữ liệu.

### 🎯 Mục tiêu (Objectives)

- Hiểu rõ cơ chế đồng bộ collection trong Hibernate / JPA khi xóa một liên kết Nhiều-Nhiều: Hibernate Dirty Checking phát hiện việc gỡ bỏ liên kết trong collection và phát sinh câu lệnh SQL `DELETE FROM student_courses WHERE student_id = ? AND course_id = ?`.
- Nhận thức sâu sắc về sự khác biệt giữa xóa mối quan hệ (unlink association) và xóa thực thể (delete entity).
- Kiểm thử các kịch bản thực tế trong `Exercise2Runner.todo21()`:
  - `unenroll AI002 <- AIL303`: Thành công hợp lệ (sinh viên `AI002` rút khỏi môn `AIL303`).
  - `unenroll IA003 <- PRJ301`: Báo lỗi vì sinh viên `IA003` chưa từng đăng ký môn `PRJ301`.
  - `enroll SE004 -> AIL303`: Đăng ký thành công vì lúc này `AIL303` đã có chỗ trống sau khi `AI002` rút môn.
  - Kiểm tra tính toàn vẹn:
    - Danh sách `Students of AIL303`: Đã thay thế `AI002` bằng `SE004`.
    - Danh sách `Courses of AI002`: Không còn môn `AIL303`.
    - `AI002 still exists?`: Trả về `true` (sinh viên không bị xóa).
    - `Total courses`: Vẫn giữ nguyên `5` (khóa học không bị xóa).

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo method `void unenroll(String studentCode, String courseCode)` trong `EnrollmentService`.
- [x] Cài đặt method `unenroll` trong `EnrollmentServiceImpl` có `@Transactional`.
- [x] Lấy thông tin `Student` và `Course` qua helper methods, kiểm tra ràng buộc `s.getCourses().contains(c)`.
- [x] Gọi `s.unenroll(c)` để gỡ bỏ quan hệ ở cả 2 đầu Entity trong bộ nhớ.
- [x] Viết hàm `todo21()` trong `Exercise2Runner` kiểm thử hủy môn hợp lệ, hủy môn không tồn tại, và đăng ký bù vào chỗ vừa trống.
- [x] Kiểm tra và in ra màn hình các assertion về sự tồn tại của thực thể `Student` (`AI002`) và tổng số lượng `Course`.
- [x] Cập nhật runner gọi `todo21()` trong `partE()` và kiểm tra log thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Nghiệp vụ hủy đăng ký môn học (unenroll): `0ef0867` (`feat(enrollment): unenroll student from course Refs: EX2 TODO 21`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo21():
   - Dòng tiêu đề: "===== TODO 21: unenroll ====="
   - [OK] unenroll AI002 <- AIL303: OK
   - [FAIL] unenroll IA003 <- PRJ301: Student IA003 is not enrolled in PRJ301
   - [OK] enroll SE004 -> AIL303: OK
   - Danh sách Students of AIL303: hiển thị SE004 (không còn AI002)
   - Courses of AI002: không còn môn AIL303
   - "AI002 still exists? true"
   - "Total courses: 5"
2. (Tùy chọn) Chụp câu lệnh DELETE sinh bởi Hibernate: "delete from student_courses where student_id=? and course_id=?"
-->

---

## Issue: [EX2-TODO 22] Đổi lớp học trong cùng một Transaction và cơ chế Rollback (Atomicity)

### 📌 Mô tả yêu cầu (Description)

Hiện thực nghiệp vụ đổi khóa học (`switchCourse`) cho sinh viên bằng cách kết hợp hai thao tác: hủy đăng ký lớp cũ (`unenroll`) và đăng ký vào lớp mới (`enroll`) bên trong cùng một Transaction nguyên tử (`@Transactional`).

Quy trình xử lý nghiệp vụ:
1. **Kiểm tra tham số**: `fromCode` và `toCode` phải khác nhau (`!fromCode.equals(toCode)`).
2. **Kiểm tra lớp cũ**: Sinh viên phải đang theo học lớp `fromCode` (`s.getCourses().contains(from)`).
3. **Thực hiện thao tác kép (Compound Operation)**:
   - Bước 1: Gỡ sinh viên khỏi lớp cũ (`s.unenroll(from)`).
   - Bước 2: Kiểm tra các quy tắc nghiệp vụ và ghi danh sinh viên vào lớp mới qua helper method `checkAndEnroll(s, to)`.
4. **Đảm bảo tính nguyên tử (Atomicity - ACID)**: Nếu bước 2 gặp lỗi (ví dụ: lớp mới đã đầy sĩ số, môn mới không hợp lệ, hoặc sinh viên bị inactive), một ngoại lệ `RuntimeException` (như `IllegalStateException`) sẽ được ném ra. Khi đó, Spring Transaction Manager phải tự động rollback toàn bộ transaction, hủy bỏ thao tác gỡ môn ở bước 1 để sinh viên không bị mất môn học cũ vô lý.

### 🎯 Mục tiêu (Objectives)

- Hiểu sâu sắc thuộc tính nguyên tử (Atomicity) của giao dịch CSDL trong Spring Data JPA: Tất cả thành công (`commit`) hoặc không có gì thay đổi (`rollback`).
- Nắm vững cơ chế rollback mặc định của Spring `@Transactional`: Bất kỳ unchecked exception nào (`RuntimeException` hoặc `Error`) phát sinh từ bên trong phương thức transactional sẽ kích hoạt rollback giao dịch.
- Tái sử dụng linh hoạt các helper methods đã xây dựng ở TODO 20 (`checkAndEnroll`) và TODO 21 (`s.unenroll`).
- Kiểm thử các kịch bản thực tế trong `Exercise2Runner.todo22()`:
  - **Đổi lớp thành công**: Chuyển sinh viên `SE001` từ môn `SWP391` sang môn `MKT101`. Danh sách môn của `SE001` được cập nhật chính xác (mất `SWP391`, có thêm `MKT101`).
  - **Đổi lớp thất bại & Rollback**: Chuyển sinh viên `SE001` từ môn `PRJ301` sang `AIL303`. Do lớp `AIL303` đã đầy sĩ số (`full 2/2`), `checkAndEnroll` ném ra ngoại lệ. Sau lỗi, in lại danh sách môn của `SE001` để kiểm chứng sinh viên vẫn còn giữ nguyên môn `PRJ301` ban đầu (không bị mất môn do rollback bảo vệ thành công).

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo method `void switchCourse(String studentCode, String fromCode, String toCode)` trong `EnrollmentService`.
- [x] Cài đặt method `switchCourse` trong `EnrollmentServiceImpl` có đánh dấu `@Transactional`.
- [x] Kiểm tra tính hợp lệ của tham số: kiểm tra `fromCode == null || fromCode.equals(toCode)` và sự tồn tại của quan hệ học môn cũ.
- [x] Thực hiện gỡ lớp cũ qua `s.unenroll(from)` và đăng ký lớp mới qua `checkAndEnroll(s, to)`.
- [x] Viết hàm `todo22()` trong `Exercise2Runner` kiểm thử 2 kịch bản: đổi lớp thành công và đổi lớp thất bại kèm kiểm tra rollback state.
- [x] Cập nhật runner gọi `todo22()` trong `partE()` và kiểm tra log thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Nghiệp vụ đổi lớp trong cùng 1 transaction và rollback: `3b6fbe0` (`feat(enrollment): switch course in one transaction Refs: EX2 TODO 22`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo22():
   - Dòng tiêu đề: "===== TODO 22: switch course in one transaction ====="
   - [OK] switch SE001 SWP391 -> MKT101: OK
   - In Courses of SE001: hiển thị MKT101 (đã thay thế SWP391)
   - [FAIL] switch SE001 PRJ301 -> AIL303: Course AIL303 is full (2/2)
   - In Courses of SE001 (after rollback): môn PRJ301 vẫn còn nguyên vẹn trong danh sách của SE001
2. (Tùy chọn) Chụp log SQL minh chứng Rollback hoặc chuỗi câu lệnh liên hoàn trong Transaction
-->

---

## Issue: [EX2-TODO 23] Xóa khóa học an toàn từ Owning Side (Safe Course Deletion)

### 📌 Mô tả yêu cầu (Description)

Hiện thực nghiệp vụ xóa khóa học (`Course`) và phân tích sâu cơ chế toàn vẹn dữ liệu trong quan hệ Many-to-Many giữa hai bên: **Owning Side** (`Student`) và **Inverse / Non-Owning Side** (`Course` với `mappedBy`).

Thực hiện so sánh giữa 2 phương pháp:
1. **Phương pháp trực tiếp (Sai lầm phổ biến - `deleteCourseDirectly`)**:
   - Gọi `courseRepository.delete(c)` và ép `courseRepository.flush()`.
   - Do `Course` là phía inverse side (`mappedBy = "courses"`), Hibernate không tự động gỡ các dòng liên kết của sinh viên trỏ đến khóa học này trong bảng trung gian `student_courses`.
   - Cơ sở dữ liệu SQL Server phát hiện vi phạm ràng buộc khóa ngoại (Foreign Key Constraint Violation) và ném ra ngoại lệ `DataIntegrityViolationException` (`The DELETE statement conflicted with the REFERENCE constraint...`).
2. **Phương pháp chuẩn xác và an toàn (`deleteCourse`)**:
   - Nhân bản danh sách sinh viên đang học sang một `Set<Student>` mới (`new HashSet<>(c.getStudents())`) để tránh ngoại lệ `ConcurrentModificationException` khi duyệt và sửa đổi collection.
   - Duyệt qua từng sinh viên và gọi `s.unenroll(c)` để gỡ liên kết trực tiếp từ **Owning Side** (`Student`). Khi đó Hibernate Dirty Checking sẽ phát sinh các câu lệnh `DELETE FROM student_courses WHERE student_id = ? AND course_id = ?`.
   - Sau khi bảng trung gian đã hoàn toàn sạch các liên kết trỏ đến khóa học, gọi `courseRepository.delete(c)` để xóa an toàn bản ghi trong bảng `courses`.
   - Trả về số lượng sinh viên vừa được gỡ liên kết (`unlinked count`).

### 🎯 Mục tiêu (Objectives)

- Hiểu rõ bản chất của `mappedBy`: Bất kỳ thay đổi nào trên collection thuộc inverse side (như `c.getStudents()`) đều KHÔNG được Hibernate đồng bộ xuống CSDL; chỉ có thay đổi từ phía owning side (`s.getCourses()`) mới quyết định các thao tác trên bảng trung gian.
- Nhận thức nguy cơ vi phạm ràng buộc toàn vẹn khóa ngoại (FK) trong quan hệ Nhiều-Nhiều khi xóa từ non-owning side.
- Nắm vững kỹ thuật lập trình phòng thủ: Sao chép tập hợp `new HashSet<>(c.getStudents())` trước khi duyệt và biến đổi collection để tránh `ConcurrentModificationException`.
- Kiểm thử trong `Exercise2Runner.todo23()` với khóa học `IAA202`:
  - **(a) Xóa trực tiếp**: Bắt và ghi nhận thành công ngoại lệ `DataIntegrityViolationException`.
  - **(b) Xóa an toàn**: Gỡ thành công các sinh viên liên quan (in `Unlinked students: 2`), xóa sạch môn `IAA202`.
  - Kiểm tra lại danh sách môn còn lại (`Remaining courses` chỉ còn 4 môn, không còn `IAA202`).
  - Kiểm tra danh sách môn của sinh viên `IA002`: Môn `IAA202` đã được gỡ bỏ sạch sẽ, sinh viên `IA002` vẫn tồn tại bình thường.

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo 2 method trong `CourseService`: `void deleteCourseDirectly(String code)` và `int deleteCourse(String code)`.
- [x] Cài đặt `deleteCourseDirectly` trong `CourseServiceImpl` có `@Transactional` và gọi `flush()` để ép chạy SQL kiểm chứng lỗi FK constraint.
- [x] Cài đặt `deleteCourse` an toàn trong `CourseServiceImpl`: clone tập hợp sinh viên, duyệt gọi `s.unenroll(c)`, và cuối cùng thực hiện `courseRepository.delete(c)`.
- [x] Viết hàm `todo23()` trong `Exercise2Runner` minh chứng cả 2 trường hợp: bắt ngoại lệ `DataIntegrityViolationException` ở (a) và thực thi thành công ở (b).
- [x] In danh sách các khóa học còn lại và danh sách khóa học của sinh viên `IA002` để xác nhận toàn vẹn dữ liệu.
- [x] Cập nhật runner kích hoạt `todo23()` trong `partE()` và kiểm tra log chạy thực tế.

### 🔗 Liên kết Commit (Related Commits)

- Xóa khóa học an toàn từ owning side: `07b91a3` (`feat(course): delete course safely from owning side Refs: EX2 TODO 23`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo23():
   - Dòng tiêu đề: "===== TODO 23: delete course ====="
   - Phần (a) Bắt ngoại lệ:
     + (a) Caught: DataIntegrityViolationException
     + The DELETE statement conflicted with the REFERENCE constraint "FK_..._student_courses"
   - Phần (b) Xóa an toàn:
     + (b) Unlinked students: 2
   - Remaining courses: Chỉ còn 4 môn (AIL303, HSF302, MKT101, PRJ301) — môn IAA202 đã bị xóa hoàn toàn
   - Courses of IA002: không còn môn IAA202
2. (Tùy chọn) Chụp chuỗi câu lệnh SQL: Các câu lệnh DELETE FROM student_courses chạy trước, theo sau bởi DELETE FROM courses WHERE id=?
-->

---

## Issue: [EX2-TODO 24] Xóa hàng loạt trên bảng trung gian với Native SQL và @Modifying (Bulk Delete)

### 📌 Mô tả yêu cầu (Description)

Hiện thực nghiệp vụ dọn dẹp dữ liệu: Xóa hàng loạt (Bulk Delete) tất cả các bản ghi đăng ký khóa học của những sinh viên đã ngưng hoạt động (`active = false / 0`) khỏi bảng trung gian `student_courses`.

Do bảng trung gian `student_courses` không có Entity JPA tương ứng (trong thiết kế `@ManyToMany` trực tiếp không có thuộc tính phụ), việc xóa hàng loạt bằng JPQL gặp khó khăn. Giải pháp tối ưu và nhanh nhất là sử dụng câu truy vấn Native SQL kết hợp annotation `@Modifying`:
```sql
DELETE FROM student_courses WHERE student_id IN (SELECT id FROM students WHERE active = 0)
```

Đặc biệt lưu ý các thuộc tính quan trọng của `@Modifying`:
- **`clearAutomatically = true`**: Sau khi câu lệnh `DELETE` native thực thi trực tiếp trên CSDL, EntityManager Persistence Context (L1 Cache) phải được xóa sạch (clear) để tránh tình trạng dữ liệu Entity trong bộ nhớ cache bị lỗi thời (Stale Data / Cache Desynchronization).
- **`flushAutomatically = true`**: Đẩy toàn bộ các thay đổi đang chờ (pending changes) trong EntityManager xuống CSDL trước khi câu lệnh Native SQL được chạy.

### 🎯 Mục tiêu (Objectives)

- Hiểu rõ sự khác biệt giữa xóa từng entity qua JPA (`collection.remove()` + dirty checking) và xóa hàng loạt (Bulk Delete) bằng `@Modifying @Query(nativeQuery = true)`: Tiết kiệm tài nguyên, thực thi chỉ với 1 câu lệnh SQL duy nhất mà không cần tải hàng trăm bản ghi vào RAM.
- Nắm vững tầm quan trọng của `clearAutomatically = true` và `flushAutomatically = true` nhằm đồng bộ giữa CSDL và L1 Cache của JPA.
- Trả về số lượng dòng thực tế bị ảnh hưởng (`affected rows count`).
- Kiểm thử trong `Exercise2Runner.todo24()`:
  - Hiển thị số lượng dòng bị xóa khỏi bảng trung gian (`Deleted rows: 3`).
  - Thống kê lại sĩ số các khóa học qua `printCourseStats()`: Các sinh viên bị inactive (như `SE001`) đã được gỡ khỏi tất cả các lớp đã đăng ký.
  - Kiểm tra danh sách sinh viên chưa đăng ký môn nào qua `findStudentsWithoutCourses()`: Sinh viên `SE001` xuất hiện trong danh sách này do toàn bộ môn học trước đó đã bị xóa sạch.

### ✅ Danh sách công việc (Checklist)

- [x] Khai báo method `int deleteEnrollmentsOfInactiveStudents()` trong `StudentRepository` với `@Modifying(clearAutomatically = true, flushAutomatically = true)` và `@Query(nativeQuery = true)`.
- [x] Khai báo method `int removeEnrollmentsOfInactiveStudents()` trong `EnrollmentService`.
- [x] Cài đặt method trong `EnrollmentServiceImpl` có đánh dấu `@Transactional` và gọi method repository tương ứng.
- [x] Viết hàm `todo24()` trong `Exercise2Runner` in số dòng bị xóa, thống kê sĩ số các khóa học và in danh sách sinh viên không có môn học nào.
- [x] Kích hoạt `partE()` và `todo24()` trong runner để kiểm tra log thực thi.

### 🔗 Liên kết Commit (Related Commits)

- Xóa hàng loạt đăng ký của sinh viên inactive bằng native SQL và @Modifying: `7a4a944` (`feat(enrollment): remove enrollments of inactive students Refs: EX2 TODO 24`)

### 📸 Hình ảnh giao diện / Minh họa (Screenshots)

<!-- GỢI Ý CHỤP ẢNH MINH CHỨNG:
1. Chụp màn hình console log khi thực thi method todo24():
   - Dòng tiêu đề: "===== TODO 24: bulk delete enrollments of inactive students ====="
   - Dòng thông báo số lượng bản ghi: "Deleted rows: 3"
   - Thống kê sĩ số các khóa học sau khi dọn dẹp: Sĩ số các môn có sinh viên inactive giảm tương ứng
   - Danh sách "Students without courses": Hiển thị sinh viên SE001 (inactive) vì không còn môn học nào
2. (Tùy chọn) Chụp câu lệnh Native SQL sinh ra: "DELETE FROM student_courses WHERE student_id IN (SELECT id FROM students WHERE active = 0)"
-->






















