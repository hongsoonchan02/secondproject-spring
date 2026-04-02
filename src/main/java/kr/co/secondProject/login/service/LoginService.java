package kr.co.secondProject.login.service;

import kr.co.secondProject.login.dto.LoginReqDTO;
import kr.co.secondProject.login.dto.LoginResDTO;
import kr.co.secondProject.login.entity.Employee;
import kr.co.secondProject.login.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final EmployeeRepository employeeRepository;

    //로그인 처리
    public LoginResDTO login(LoginReqDTO req){

        //1. 이메일로 직원 찾기
        Employee employee = employeeRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));


         //2. 비밀번호 확인
        if (!employee.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        //3. 로그인 성공 -> 직원 정보 반환
        return  new LoginResDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getRole(),
                employee.getPosition()
        );
    }
}
