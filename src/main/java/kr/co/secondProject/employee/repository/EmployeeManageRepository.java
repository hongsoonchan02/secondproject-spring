package kr.co.secondProject.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.secondProject.employee.entity.Employee;

public interface EmployeeManageRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByStatus(String status);

    List<Employee> findByNameContaining(String name);

    List<Employee> findByNameContainingAndStatus(String name, String status);
}
