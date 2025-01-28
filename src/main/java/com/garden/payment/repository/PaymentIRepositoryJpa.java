package com.garden.payment.repository;

import com.garden.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentIRepositoryJpa extends JpaRepository<Payment,Integer> {
}
