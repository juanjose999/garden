package com.garden.guardian.service;

import com.garden.guardian.entity.Guardian;
import com.garden.guardian.entity.dto.GuardianMapper;
import com.garden.guardian.entity.dto.GuardianRequestDto;
import com.garden.guardian.entity.dto.GuardianResponseDto;
import com.garden.guardian.repository.GuardianIRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Guard;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianIService{

    private final GuardianIRepository guardianRepository;

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
    public GuardianResponseDto saveGuardian(GuardianRequestDto guardianRequestDto) {
        Guardian guardian = guardianRepository.saveGuardian(
                GuardianMapper.guardianRequestDtoToGuardian(guardianRequestDto)
        );
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
