package com.garden.admin.entity.dto;

import com.garden.children.entity.Children;
import com.garden.guardian.entity.Guardian;
import lombok.Builder;

import java.util.Set;
@Builder
public record AdminResponseDto(String email, Set<Guardian> guardians) {
}
