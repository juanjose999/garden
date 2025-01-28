package com.garden.payment.controller;

import com.garden.children.entity.Children;
import com.garden.children.entity.dto.ChildrenResponseDto;
import com.garden.children.service.ChildrenIService;
import com.garden.guardian.entity.Guardian;
import com.garden.guardian.repository.GuardianIRepositoryJpa;
import com.garden.children.repository.ChildrenIRepositoryJpa;
import com.garden.payment.repository.PaymentIRepositoryJpa;
import com.garden.payment.entity.dto.PaymentRequestDto;
import com.garden.payment.entity.Mes;
import com.garden.payment.entity.Payment;
import com.garden.payment.service.PaymentIService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentIService paymentIService;

    @GetMapping
    public ResponseEntity<?> findAllPayments() {
        return ResponseEntity.ok(paymentIService.findAllPayments());
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@Validated  @RequestBody PaymentRequestDto paymentRequestDto){
        Either<Map<String, String>, Guardian> result = paymentIService.savePayment(paymentRequestDto);
        if(result.isRight()){
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.badRequest().body(result.getLeft());
    }

}
