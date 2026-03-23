package kr.co.secondProject.login.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="email_verification")
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationId;    //이메일로인증하는 테이블 고유PK

    @ManyToOne
    @JoinColumn(name = "id2")
    private Employee employee;      //직원 FK

    private String verificationCode;//인증코드 6자리

    private String expiredAt;       //인증코드발송 만료시간( 발급 +3분)

    private String isUsed;          //사용여부(true/false)

    private String email;           //인증요청한 이메일
}
