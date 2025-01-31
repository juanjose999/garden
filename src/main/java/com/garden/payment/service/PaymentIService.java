package com.garden.payment.service;

import com.garden.exception.ChildrenException;
import com.garden.guardian.entity.Guardian;
import com.garden.guardian.entity.dto.GuardianResponseDto;
import com.garden.payment.entity.Payment;
import com.garden.payment.entity.dto.PaymentRequestDto;
import io.vavr.control.Either;

import java.util.List;
import java.util.Map;

public interface PaymentIService {
    List<Payment> findAllPayments();
    Payment findPaymentById(int id);
    Either<Map<String, String>, GuardianResponseDto> savePayment(PaymentRequestDto paymentRequestDto) throws ChildrenException;
    Payment updatePayment(Payment payment, int id);
    Boolean deletePayment(int id);
}
