package kr.co.secondProject.login.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 응답 DTO")
public class LoginResDTO {

    @Schema(description = "유저넘버(유저id)")
    private Long id;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "권한")
    private String role;

    @Schema(description = "직책")
    private String position;

}
