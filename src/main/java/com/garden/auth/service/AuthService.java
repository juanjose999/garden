package com.garden.auth.service;

import com.garden.admin.entity.LoginRequestUser;
import com.garden.admin.entity.MyUser;
import com.garden.admin.repository.AdminIRepository;
import com.garden.config.JwtService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthIService{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AdminIRepository adminIRepository;


    public Either<Map<String, String>, Map<String, String>> login(LoginRequestUser loginRequestUser) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestUser.email(), loginRequestUser.password()
        ));
        if(authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return Either.right(Map.of("token : ", jwtService.generateToken(userDetails)));
        }
        return Either.left(Map.of("Error : ","Invalid credentials"));
    }

    public MyUser save(MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        return adminIRepository.save(myUser);
    }

}
