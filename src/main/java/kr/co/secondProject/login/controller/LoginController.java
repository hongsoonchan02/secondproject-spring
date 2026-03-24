package kr.co.secondProject.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.secondProject.login.dto.LoginReqDTO;
import kr.co.secondProject.login.dto.LoginResDTO;
import kr.co.secondProject.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="로그인 API", description="로그인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    @Operation(summary="로그인", description="이메일과 비밀번호로 로그인합니다.")
    //POST /api /auth /login
    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> login(@RequestBody LoginReqDTO req){
        LoginResDTO res = loginService.login(req);
        return ResponseEntity.ok(res);
    }
}
