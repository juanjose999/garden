package com.garden.guardian.entity.dto;

import com.garden.guardian.entity.Guardian;

public class GuardianMapper {

    public static Guardian guardianRequestDtoToGuardian(GuardianRequestDto guardianRequestDto) {
        return Guardian.builder()
                .full_name(guardianRequestDto.full_name())
                .n_whasapp(guardianRequestDto.num_whatsapp())
                .build();
    }

    public static GuardianResponseDto guardianToGuardianResponseDto(Guardian guardian) {
        return GuardianResponseDto.builder()
                .full_name(guardian.getFull_name())
                .num_whatsapp(guardian.getN_whasapp())
                .childrens(guardian.getChildrens())
                .build();
    }

}
