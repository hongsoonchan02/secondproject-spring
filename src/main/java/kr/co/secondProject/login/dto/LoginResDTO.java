package kr.co.secondProject.login.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResDTO {

    private Long id;      //유저넘버
    private String name;  //이름
    private String email; //이메일
    private String role;  //권한 (ADMIN/ MANAGER/ EMPLOYEE)
    private String position;//직책

}
