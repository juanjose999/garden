package com.garden.children.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.garden.payment.entity.Payment;
import com.garden.guardian.entity.Guardian;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Children {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String full_name;

    @ManyToOne
    @JoinColumn(name = "guardian_id")
    @JsonBackReference
    private Guardian guardian;

    @ManyToMany(mappedBy = "children_list")
    @JsonManagedReference
    private Set<Payment> paymentList = new HashSet<>();

    public Children(String full_name, Guardian guardian) {
        this.full_name = full_name;
        this.guardian = guardian;
        this.paymentList = new HashSet<>();
    }

    public void setPaymentList(Payment pago) {
        if(paymentList != null) {
            this.paymentList = new HashSet<>();
        }
        this.paymentList.add(pago);
    }


}
