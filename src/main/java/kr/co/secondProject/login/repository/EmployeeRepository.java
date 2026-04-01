package kr.co.secondProject.login.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.secondProject.login.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //이메일로 직원 찾기( 로그인할 때 사용)
    Optional<Employee> findByEmail(String email);
    
    int countByDpNum(Long id); // DepartmentServiceImpl의 부서검색에 쓰기 위한 메서드
    
    List<Employee> findByDpNum(Long DpNum);
    
    Optional<Employee> findByEmpId(String id);
}
