package kr.co.secondProject.login.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //유저넘버(PK)

    private String profilePhoto;    //프로필사진 URL

    private Long empId;     //사번

    private String name;    //이름

    @Column(unique = true)
    private String email;   //이메일(로그인용)

    private String password;    //비밀번호

    private LocalDateTime hireDate;     //입사일

    private LocalDateTime resignDate;   //퇴사일 (기본값 9999-12-31)

    private String status;      //계정활성화 (기본값 true)

    private String role;        //권한 (ADMIN / MANAGER / EMPLOYEE)

    private String position;    //직책

    private Long dpNum;        // 부서코드(FK)

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }
}
