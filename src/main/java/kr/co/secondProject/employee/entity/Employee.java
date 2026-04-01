package kr.co.secondProject.employee.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_id")
    private String empId;

    private String name;

    private String email;

    private String password;

    @Column(name = "hire_date")
    private LocalDateTime hireDate;

    @Column(name = "resign_date")
    private LocalDateTime resignDate;

    private String status;

    private String role;

    private String position;

    @Column(name = "dp_num")
    private String dpNum;

    public static Employee create(
            String empId,
            String name,
            String email,
            String password,
            LocalDateTime hireDate,
            String status,
            String role,
            String position,
            String dpNum) {

        Employee employee = new Employee();
        employee.empId = empId;
        employee.name = name;
        employee.email = email;
        employee.password = password;
        employee.hireDate = hireDate;
        employee.status = status;
        employee.role = role;
        employee.position = position;
        employee.dpNum = dpNum;
        return employee;
    }

    public void updateInfo(
            String name,
            String email,
            String status,
            String role,
            String position,
            String dpNum) {

        if (name != null) this.name = name;
        if (email != null) this.email = email;
        if (status != null) this.status = status;
        if (role != null) this.role = role;
        if (position != null) this.position = position;
        if (dpNum != null) this.dpNum = dpNum;
    }

    public void resign() {
        this.status = "RESIGNED";
        this.resignDate = LocalDateTime.now();
    }
}