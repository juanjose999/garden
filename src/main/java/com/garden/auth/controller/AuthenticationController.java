package com.garden.auth.controller;

import com.garden.admin.entity.LoginRequestUser;
import com.garden.admin.entity.MyUser;
import com.garden.auth.service.AuthService;
import com.garden.config.JwtService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> saveAdmin(@RequestBody MyUser myUser){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.save(myUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestUser loginRequestUser){
        Either<Map<String, String>, Map<String, String>> result = authService.login(loginRequestUser);
        return result.isRight() ?
                ResponseEntity.status(HttpStatus.OK).body(result.get()) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result.getLeft());
    }

}
