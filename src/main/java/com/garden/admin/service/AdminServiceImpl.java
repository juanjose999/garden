package com.garden.admin.service;

import com.garden.admin.entity.Admin;
import com.garden.admin.entity.dto.AdminMapper;
import com.garden.admin.entity.dto.AdminRequestDto;
import com.garden.admin.entity.dto.AdminResponseDto;
import com.garden.admin.repository.AdminIRepository;
import com.garden.config.JwtService;
import com.garden.exception.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminIService {

    private final AdminIRepository adminIRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public List<AdminResponseDto> findAll() {
        return adminIRepository.findAll().stream().map(AdminMapper::adminToAdminResponseDto).toList();
    }

    @Override
    public AdminResponseDto findById(int id) throws AdminException {
        return adminIRepository.findById(id).map(AdminMapper::adminToAdminResponseDto).orElseThrow(() -> new AdminException("No se encontro el usuario"));
    }

    @Override
    public AdminResponseDto findByEmail(String email) throws AdminException {
        return adminIRepository.findByEmail(email).map(AdminMapper::adminToAdminResponseDto).orElseThrow(() -> new AdminException("No se encontro el usuario"));
    }

    @Override
    public AdminResponseDto findByUsername(String username) throws AdminException {
        return adminIRepository.findByUsername(username)
                .map(AdminMapper::adminToAdminResponseDto).orElseThrow(() -> new AdminException("No se encontro el usuario"));
    }

    @Override
    public AdminResponseDto save(AdminRequestDto adminRequestDto) {
        return AdminMapper.adminToAdminResponseDto(
                adminIRepository.save(
                    new Admin(
                            adminRequestDto.name(),
                            adminRequestDto.email(),
                            passwordEncoder.encode(adminRequestDto.password())))
        );
    }

    @Override
    public AdminResponseDto update(AdminRequestDto adminRequestDto, int id) throws AdminException {
        Admin update = adminIRepository.update(AdminMapper.adminResponseDtoToAdmin(adminRequestDto), id)
                .orElseThrow(() -> new AdminException("No se encontró al usuario"));

        return AdminMapper.adminToAdminResponseDto(update);
    }


    @Override
    public Boolean delete(Integer id) {
        return adminIRepository.delete(id);
    }

}
