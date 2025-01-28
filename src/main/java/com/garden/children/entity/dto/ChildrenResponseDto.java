package com.garden.children.entity.dto;

import com.garden.guardian.entity.Guardian;
import com.garden.payment.entity.Payment;
import lombok.Builder;

import java.util.Set;

@Builder
public record ChildrenResponseDto(Integer id, String nombre, Set<Payment> paymentList ) {
}
