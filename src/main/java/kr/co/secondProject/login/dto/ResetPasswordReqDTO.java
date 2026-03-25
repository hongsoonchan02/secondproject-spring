package kr.co.secondProject.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResetPasswordReqDTO {
    private String email;
    private String newPassword;
}
