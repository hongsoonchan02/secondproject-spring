package kr.co.secondProject.login.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VerifyCodeReqDTO {
    private String email;
    private String verificationCode;
}
