package kr.co.secondProject.employee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_num", nullable = false, unique = true, length = 50)
    private String empNum;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(nullable = false, length = 30)
    private String role;

    @Column(nullable = false, length = 30)
    private String position;

    @Column(name = "dp_num", nullable = false)
    private Integer dpNum;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Employee(String empNum, String name, String email, LocalDate hireDate,
                    String role, String position, Integer dpNum) {
        this.empNum = empNum;
        this.name = name;
        this.email = email;
        this.hireDate = hireDate;
        this.role = role;
        this.position = position;
        this.dpNum = dpNum;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String name, String email, LocalDate hireDate,
                       String role, String position, Integer dpNum) {
        this.name = name;
        this.email = email;
        this.hireDate = hireDate;
        this.role = role;
        this.position = position;
        this.dpNum = dpNum;
    }
}