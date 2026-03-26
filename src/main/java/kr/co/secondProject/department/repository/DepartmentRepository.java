package kr.co.secondProject.department.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.secondProject.department.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	public List<Department> findByDpNameContaining(String keyword);
	
}
