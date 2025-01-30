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
    @Column(name = "full_name")
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "guardian_id")
    @JsonBackReference
    private Guardian guardian;

    @ManyToMany(mappedBy = "children_list")
    @JsonManagedReference
    private Set<Payment> paymentList = new HashSet<>();

    public Children(String fullName, Guardian guardian) {
        this.fullName = fullName;
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
