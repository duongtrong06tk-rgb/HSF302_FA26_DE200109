package fu.de200109.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(precision = 18, scale = 2)
    private BigDecimal salary; // Dùng BigDecimal cho tiền tệ

    @Column(name = "hire_date")
    private LocalDate hireDate; // Sử dụng LocalDate (JPA 2.2+)

    @Column(unique = true, nullable = false)
    private String email;

}
