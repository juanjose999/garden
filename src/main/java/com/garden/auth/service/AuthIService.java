package com.garden.auth.service;

import com.garden.admin.entity.dto.AdminRequestDto;
import com.garden.admin.entity.dto.AdminResponseDto;
import com.garden.admin.entity.dto.LoginRequestAdmin;
import com.garden.admin.entity.Admin;
import io.vavr.control.Either;

import java.util.Map;

public interface AuthIService {
    Either<Map<String, String>, Map<String, String>> login(LoginRequestAdmin loginRequestAdmin);
    AdminResponseDto save(AdminRequestDto adminRequestDto);
}
