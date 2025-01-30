package com.garden.admin.service;

import com.garden.admin.entity.Admin;
import com.garden.admin.entity.dto.AdminRequestDto;
import com.garden.admin.entity.dto.AdminResponseDto;
import com.garden.exception.AdminException;

import java.util.List;
import java.util.Optional;

public interface AdminIService {
    List<AdminResponseDto> findAll();
    AdminResponseDto findById(int id) throws AdminException;
    AdminResponseDto findByEmail(String email) throws AdminException;
    AdminResponseDto findByUsername(String username) throws AdminException;
    AdminResponseDto save(AdminRequestDto adminRequestDto) throws AdminException;
    AdminResponseDto update(AdminRequestDto adminRequestDto, int id) throws AdminException;
    Boolean delete(Integer id);
}
