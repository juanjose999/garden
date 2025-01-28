package com.garden.guardian.service;

import com.garden.guardian.entity.Guardian;
import com.garden.guardian.entity.dto.GuardianRequestDto;
import com.garden.guardian.entity.dto.GuardianResponseDto;

import java.util.List;

public interface GuardianIService {
    List<GuardianResponseDto> findAllGuardian();
    GuardianResponseDto findGuardianById(int id);
    GuardianResponseDto saveGuardian(GuardianRequestDto guardian);
    GuardianResponseDto updateGuardian(GuardianRequestDto guardianRequestDto, int id);
    Boolean deleteGuardian(Integer id);
}
