package kr.co.secondProject.repository;

import kr.co.secondProject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //이메일로 직원 찾기( 로그인할 때 사용)
    Optional<Employee> findByEmail(String email);
}
