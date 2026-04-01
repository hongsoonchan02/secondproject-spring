package kr.co.secondProject.department.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.secondProject.department.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	List<Department> findByDpNameContaining(String keyword);
	
	List<Department> findTop5ByOrderByIdDesc(); // 최근 등록된(id가 높은 순) 부서를 가져오는 코드
														  // 없을수도 있기에 Optional 처리
	
	
}
