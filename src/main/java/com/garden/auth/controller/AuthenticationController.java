package com.garden.auth.controller;

import com.garden.admin.entity.dto.AdminRequestDto;
import com.garden.admin.entity.dto.LoginRequestAdmin;
import com.garden.admin.entity.Admin;
import com.garden.auth.service.AuthService;
import com.garden.config.JwtService;
import io.vavr.control.Either;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @GetMapping("/health")
    public String health(HttpServletRequest request) {
        return "OK " + request.getRemoteAddr();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> saveAdmin(@RequestBody AdminRequestDto adminRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.save(adminRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestAdmin loginRequestAdmin){
        Either<Map<String, String>, Map<String, String>> result = authService.login(loginRequestAdmin);
        return result.isRight() ?
                ResponseEntity.status(HttpStatus.OK).body(result.get()) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result.getLeft());
    }

}
