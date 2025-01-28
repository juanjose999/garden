package com.garden.payment.repository;

import com.garden.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentIRepository{

    private final PaymentIRepositoryJpa paymentIRepositoryJpa;

    @Override
    public List<Payment> findAllPayments() {
        return paymentIRepositoryJpa.findAll();
    }

    @Override
    public Payment findPaymentById(int id) {
        return paymentIRepositoryJpa.findById(id).orElse(null);
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentIRepositoryJpa.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment, int id) {
        Payment oldPayment = paymentIRepositoryJpa.findById(id).orElse(null);
        if (oldPayment == null) throw new RuntimeException("Payment not found");
        if(payment.getDescripcion() != null) oldPayment.setDescripcion(payment.getDescripcion());
        return paymentIRepositoryJpa.save(oldPayment);
    }

    @Override
    public Boolean deletePayment(int id) {
        Payment oldPayment = paymentIRepositoryJpa.findById(id).orElse(null);
        if(oldPayment == null) return false;
        paymentIRepositoryJpa.delete(oldPayment);
        return true;
    }
}
