package kr.co.secondProject.department.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.secondProject.department.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	public ArrayList<Department> findByDpNameContaining(String keyword);
	
	public boolean existsByDpCode(String dpCode);
}
