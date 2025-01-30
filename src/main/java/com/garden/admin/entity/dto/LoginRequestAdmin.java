package com.garden.admin.entity.dto;

import lombok.Builder;

@Builder
public record LoginRequestAdmin(String email, String password) {
}
