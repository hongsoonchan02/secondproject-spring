package kr.co.secondProject.login.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.secondProject.login.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "비밀번호 찾기 API", description = "비밀번호 찾기 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/password")
@CrossOrigin(origins = "*")
public class PasswordController {

    private final PasswordService passwordService;

    //1. 인증코드 이메일 발송
    @Operation(summary = "인증코드 발송", description = "이메일로 6자리 인증코드를 발송합니다.")
    @PostMapping("/send")
    public ResponseEntity<String> sendCode(@RequestBody EmailReqDTO req) {
        passwordService.sendVerificationCode(req.getEmail());
        return ResponseEntity.ok("인증코드가 발송됐습니다.");
    }

}
