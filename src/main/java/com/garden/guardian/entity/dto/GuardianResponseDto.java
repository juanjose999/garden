package com.garden.guardian.entity.dto;

import com.garden.children.entity.Children;
import lombok.Builder;

import java.util.Set;

@Builder
public record GuardianResponseDto(String full_name, String num_whatsapp, Set<Children> childrens) {
}
