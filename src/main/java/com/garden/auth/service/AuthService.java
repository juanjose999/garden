package com.garden.auth.service;

import com.garden.admin.entity.dto.AdminMapper;
import com.garden.admin.entity.dto.AdminRequestDto;
import com.garden.admin.entity.dto.AdminResponseDto;
import com.garden.admin.entity.dto.LoginRequestAdmin;
import com.garden.admin.entity.Admin;
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

    public Either<Map<String, String>, Map<String, String>> login(LoginRequestAdmin loginRequestAdmin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestAdmin.email(), loginRequestAdmin.password()
        ));
        if(authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return Either.right(Map.of("token : ", jwtService.generateToken(userDetails)));
        }
        return Either.left(Map.of("Error : ","Invalid credentials"));
    }

    public AdminResponseDto save(AdminRequestDto adminRequestDto) {
        Admin admin = Admin.builder()
                .fullName(adminRequestDto.name())
                .email(adminRequestDto.email())
                .password(passwordEncoder.encode(adminRequestDto.password()))
                .build();
        return AdminMapper.adminToAdminResponseDto(adminIRepository.save(admin));
    }

}
