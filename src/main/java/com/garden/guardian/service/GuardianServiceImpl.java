package com.garden.guardian.service;

import com.garden.admin.entity.Admin;
import com.garden.admin.repository.AdminIRepository;
import com.garden.admin.service.AdminIService;
import com.garden.config.JwtService;
import com.garden.guardian.entity.Guardian;
import com.garden.guardian.entity.dto.GuardianMapper;
import com.garden.guardian.entity.dto.GuardianRequestDto;
import com.garden.guardian.entity.dto.GuardianResponseDto;
import com.garden.guardian.repository.GuardianIRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Guard;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianIService{

    private final GuardianIRepository guardianRepository;
    private final AdminIRepository adminIRepository;
    private final AdminIService adminIService;
    private final JwtService jwtService;

    @Override
    public List<GuardianResponseDto> findAllGuardian() {
        return guardianRepository.findAllGuardian().stream()
                .map(GuardianMapper::guardianToGuardianResponseDto)
                .toList();
    }

    @Override
    public GuardianResponseDto findGuardianById(int id) {
        Guardian guardian = guardianRepository.findGuardianById(id);
        if(guardian == null) throw new RuntimeException("Guardian not found");
        return GuardianMapper.guardianToGuardianResponseDto(guardian);
    }

    @Override
    public GuardianResponseDto saveGuardian(GuardianRequestDto guardianRequestDto, HttpServletRequest request) {
        Guardian guardian = guardianRepository.saveGuardian(
                GuardianMapper.guardianRequestDtoToGuardian(guardianRequestDto)
        );
        String token = request.getHeader("Authorization").substring(7);
        System.out.println(jwtService.extractUserName(token));
        return GuardianMapper.guardianToGuardianResponseDto(guardian);
    }

    @Override
    public GuardianResponseDto updateGuardian(GuardianRequestDto guardianRequestDto, int id) {
        Guardian guardian = guardianRepository.findGuardianById(id);
        if(guardian == null) throw new RuntimeException("Guardian not found");
        return GuardianMapper.guardianToGuardianResponseDto(
                guardianRepository.updateGuardian(GuardianMapper.guardianRequestDtoToGuardian(guardianRequestDto), id)
        );
    }

    @Override
    public Boolean deleteGuardian(Integer id) {
        Guardian guardian = guardianRepository.findGuardianById(id);
        if(guardian == null) throw new RuntimeException("Guardian not found");
        return guardianRepository.deleteGuardian(id);
    }
}
