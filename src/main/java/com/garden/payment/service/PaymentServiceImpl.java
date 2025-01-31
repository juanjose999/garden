package com.garden.payment.service;

import com.garden.children.entity.Children;
import com.garden.children.entity.dto.ChildrenResponseDto;
import com.garden.children.repository.ChildrenIRepository;
import com.garden.children.service.ChildrenIService;
import com.garden.exception.ChildrenException;
import com.garden.guardian.entity.Guardian;
import com.garden.guardian.entity.dto.GuardianMapper;
import com.garden.guardian.entity.dto.GuardianResponseDto;
import com.garden.payment.entity.Mes;
import com.garden.payment.entity.Payment;
import com.garden.payment.entity.dto.PaymentRequestDto;
import com.garden.payment.repository.PaymentIRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentIService{

    private final PaymentIRepository paymentRepository;
    private final ChildrenIRepository childrenRepository;

    @Override
    public List<Payment> findAllPayments() {
        return List.of();
    }

    @Override
    public Payment findPaymentById(int id) {
        return null;
    }

    @Override
    public Either<Map<String, String>, GuardianResponseDto> savePayment(PaymentRequestDto paymentRequestDto) throws ChildrenException {
        Optional<Children> findChild = childrenRepository.findChildrenById(paymentRequestDto.idNino());
        if(findChild.isEmpty()) throw new ChildrenException("El niño no se encontro.");

        Children nino = findChild.get();
        if (nino.getPaymentList().stream()
                .anyMatch(pago -> pago.getMes_pago().equals(Mes.valueOf(paymentRequestDto.mes())))) {
            Map<String, String> error = Map.of("error", "Está pagando un mes que ya está pago, por favor corrija.");
            return Either.left(error);
        }

        Payment paymentNew = new Payment();
        paymentRepository.savePayment(paymentNew);
        paymentNew.setValor(paymentRequestDto.valor());
        paymentNew.setDescripcion(paymentRequestDto.descripcion());
        paymentNew.setMes_pago(Mes.valueOf(paymentRequestDto.mes()));
        paymentNew.setChildrenList(nino);
        paymentNew.setFecha_pago();
        paymentRepository.savePayment(paymentNew);

        nino.setPaymentList(paymentNew);

        return Either.right(GuardianMapper.guardianToGuardianResponseDto(nino.getGuardian()));
    }

    @Override
    public Payment updatePayment(Payment payment, int id) {
        return null;
    }

    @Override
    public Boolean deletePayment(int id) {
        return null;
    }
}
