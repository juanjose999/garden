package com.garden.auth.service;

import com.garden.admin.entity.dto.AdminMapper;
import com.garden.admin.entity.dto.AdminRequestDto;
import com.garden.admin.entity.dto.AdminResponseDto;
import com.garden.admin.entity.dto.LoginRequestAdmin;
import com.garden.admin.entity.Admin;
import com.garden.admin.repository.AdminIRepository;
import com.garden.config.JwtService;
import com.garden.token.entity.Token;
import com.garden.token.entity.dto.TokenResponse;
import com.garden.token.repository.TokenIRepositoryJpa;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthIService{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AdminIRepository adminIRepository;
    private final TokenIRepositoryJpa tokenIRepositoryJpa;

    public TokenResponse save(AdminRequestDto adminRequestDto) {
        Admin admin = Admin.builder()
                .fullName(adminRequestDto.name())
                .email(adminRequestDto.email())
                .password(passwordEncoder.encode(adminRequestDto.password()))
                .build();
        adminIRepository.save(admin);
        var jwtToken = jwtService.generateToken(admin.getEmail());
        var refresh = jwtService.generateTokenRefresh(admin.getEmail());
        var token = saveUserWithToken(admin, refresh);
        admin.setToken(token);
        adminIRepository.save(admin);
        return new TokenResponse(jwtToken,refresh);
    }

    public Either<Map<String,String>,TokenResponse> login(LoginRequestAdmin loginRequestAdmin) {
        System.out.println(loginRequestAdmin);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestAdmin.email(), loginRequestAdmin.password()
        ));
        if(authentication.isAuthenticated()) {
            var admin = adminIRepository.findByEmail(loginRequestAdmin.email()).orElseThrow(() -> new UsernameNotFoundException(loginRequestAdmin.email()));
            var jwtToken = jwtService.generateToken(admin.getEmail());
            var refresh = jwtService.generateTokenRefresh(admin.getEmail());
            revokedAllUserToken(admin);
            saveUserWithToken(admin, refresh);
            return Either.right(new TokenResponse(jwtToken, refresh));
        }
        return Either.left(Map.of("Error","Credenciales de usuario invalidas."));
    }

    public TokenResponse refreshToken(final String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token invalido");
        }
        final String refreshToken = authHeader.substring(7);
        final String adminEmail = jwtService.extractUserName(refreshToken);

        if(adminEmail == null || adminEmail.isEmpty()) {
            throw new IllegalArgumentException("Token invalido");
        }
        final Admin admin = adminIRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new UsernameNotFoundException(adminEmail));

        if(!jwtService.isTokenValid(refreshToken, admin)) {
            throw new IllegalArgumentException("Token invalido");
        }

        final String accessToken = jwtService.generateToken(admin.getEmail());
        return new TokenResponse(accessToken, refreshToken);
    }

    private void revokedAllUserToken(Admin admin){
        final List<Token> validAdminTokens = tokenIRepositoryJpa
                .findAllValidIsFalseOrRevokedIsFalseByAdminId(admin.getId());
        if(!validAdminTokens.isEmpty()){
            for(Token token : validAdminTokens){
                token.setRevoked(true);
                token.setExpired(true);
            }
            tokenIRepositoryJpa.saveAll(validAdminTokens);
        }
    }

    public Token saveUserWithToken(Admin admin, String tokenGenerate){
        var token =
                Token.builder()
                        .admin(admin)
                        .token(tokenGenerate)
                        .type(Token.TokenType.BEARER)
                        .expired(false)
                        .revoked(false)
                        .build();
        tokenIRepositoryJpa.save(token);
        return token;
    }

}
