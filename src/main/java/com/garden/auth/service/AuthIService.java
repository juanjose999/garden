package com.garden.auth.service;

import com.garden.admin.entity.dto.AdminRequestDto;
import com.garden.admin.entity.dto.AdminResponseDto;
import com.garden.admin.entity.dto.LoginRequestAdmin;
import com.garden.admin.entity.Admin;
import com.garden.token.entity.dto.TokenResponse;
import io.vavr.control.Either;

import java.util.Map;

public interface AuthIService {
    Either<Map<String,String>,TokenResponse> login(LoginRequestAdmin loginRequestAdmin);
    TokenResponse save(AdminRequestDto adminRequestDto);
    TokenResponse refreshToken(final String authHeader);
}
