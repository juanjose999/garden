package com.garden.payment.entity.dto;

public record PaymentRequestDto(double valor, String mes, String descripcion, Integer idNino) {
}
