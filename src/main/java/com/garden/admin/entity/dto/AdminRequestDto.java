package com.garden.admin.entity.dto;

import lombok.Builder;

@Builder
public record AdminRequestDto(String name, String email, String password) {
}
