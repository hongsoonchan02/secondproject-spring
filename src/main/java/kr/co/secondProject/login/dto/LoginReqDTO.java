package kr.co.secondProject.login.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description="로그인 요청 DTO")
public class LoginReqDTO {

    @Schema(description = "이메일", example = "sojeong@test.com")
    private String email;

    @Schema(description = "비밀번호", example = "1234")
    private String password;
}
