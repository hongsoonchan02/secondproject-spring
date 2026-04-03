package kr.co.secondProject.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.secondProject.employee.entity.Employee;

public interface EmployeeManageRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContaining(String name);

    Optional<Employee> findByEmpNum(String empNum);

    boolean existsByEmpNum(String empNum);

    boolean existsByEmail(String email);
}