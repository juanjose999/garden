package com.garden.auth.controller;

import com.garden.admin.entity.dto.AdminMapper;
import com.garden.admin.entity.dto.AdminRequestDto;
import com.garden.admin.entity.dto.LoginRequestAdmin;
import com.garden.admin.entity.Admin;
import com.garden.admin.repository.AdminIRepository;
import com.garden.auth.service.AuthService;
import com.garden.config.JwtService;
import com.garden.token.entity.Token;
import com.garden.token.repository.TokenIRepositoryJpa;
import com.garden.token.entity.dto.TokenResponse;
import io.vavr.control.Either;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @GetMapping("/health")
    public String health(HttpServletRequest request) {
        return "OK " + request.getRemoteAddr();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> saveAdmin(@RequestBody AdminRequestDto adminRequestDto){
        var result = authService.save(adminRequestDto);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestAdmin loginRequestAdmin){
        Either<Map<String, String>, TokenResponse> result = authService.login(loginRequestAdmin);
        if(result.isRight()){
            return ResponseEntity.status(HttpStatus.OK).body(result.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result.getLeft());
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        return ResponseEntity.ok().body(authService.refreshToken(authorization));
    }

}
