package com.garden.admin.entity.dto;

import com.garden.admin.entity.Admin;

public class AdminMapper {

    public static AdminResponseDto adminToAdminResponseDto(Admin admin) {
        return AdminResponseDto.builder()
                .email(admin.getEmail())
                .guardians(admin.getGuardians())
                .build();
    }

    public static Admin adminResponseDtoToAdmin(AdminRequestDto adminRequestDto) {
        return new Admin(adminRequestDto.name(), adminRequestDto.email(), adminRequestDto.password());
    }

}
