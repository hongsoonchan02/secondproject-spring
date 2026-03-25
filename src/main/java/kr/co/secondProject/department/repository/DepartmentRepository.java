package kr.co.secondProject.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.secondProject.department.service.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {

}
