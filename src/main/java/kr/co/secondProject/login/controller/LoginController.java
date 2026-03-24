package kr.co.secondProject.login.controller;

import kr.co.secondProject.login.dto.LoginReqDTO;
import kr.co.secondProject.login.dto.LoginResDTO;
import kr.co.secondProject.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins ="*")
public class LoginController {

    private final LoginService loginService;

    //POST /api /auth /login
    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> login(@RequestBody LoginReqDTO req){
        LoginResDTO res = loginService.login(req);
        return ResponseEntity.ok(res);
    }
}
