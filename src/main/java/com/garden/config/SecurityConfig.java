package com.garden.config;

import com.garden.config.JwtFilter;
import com.garden.admin.service.AdminDetailsAuthService;
import com.garden.token.entity.Token;
import com.garden.token.repository.TokenIRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AdminDetailsAuthService adminDetailsAuthService;
    private final JwtFilter jwtFilter;
    private final TokenIRepositoryJpa tokenIRepositoryJpa;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("health","v1/auth/signup","v1/auth/login").permitAll();
                    auth.anyRequest().hasRole("ADMIN");
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> {
                    logout.logoutUrl("/auth/logout")
                            .addLogoutHandler((request, response, authentication) -> {
                                final var authHeader = request.getHeader("Authorization");
                                logout(authHeader);
                            })
                            .logoutSuccessHandler((request, response, authentication) -> {
                                SecurityContextHolder.clearContext();
                            });
                })
                .build();
    }

    private void logout(String authHeader) {
        if(authHeader == null && !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid token");
        }
        final String token = authHeader.substring(7);
        Token findToken = tokenIRepositoryJpa.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        findToken.expired = true;
        findToken.revoked = true;
        tokenIRepositoryJpa.save(findToken);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(adminDetailsAuthService);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
