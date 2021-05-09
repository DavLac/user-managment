package io.davlac.user.managment.controller;

import io.davlac.user.managment.service.AuthService;
import io.davlac.user.managment.service.dto.UserLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(authService.login(userLoginDTO));
    }

}
