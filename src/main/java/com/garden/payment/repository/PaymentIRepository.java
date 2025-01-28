package com.garden.payment.repository;

import com.garden.payment.entity.Payment;

import java.util.List;

public interface PaymentIRepository {
    List<Payment> findAllPayments();
    Payment findPaymentById(int id);
    Payment savePayment(Payment payment);
    Payment updatePayment(Payment payment, int id);
    Boolean deletePayment(int id);
}
