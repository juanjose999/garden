package com.garden.payment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.garden.children.entity.Children;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double valor;
    private String descripcion;
    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "payments",
            joinColumns = @JoinColumn(name = "payment_id"),
            inverseJoinColumns = @JoinColumn(name = "children_id")
    )
    private List<Children> children_list = new ArrayList<>();
    private String fecha_pago;
    @Enumerated(EnumType.STRING)
    private Mes mes_pago;

    public void setFecha_pago() {
        this.fecha_pago = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
    }

    public void setChildrenList(Children nino) {
        if (this.children_list == null) {
            this.children_list = new ArrayList<>();
        }
        this.children_list.add(nino);
    }

}
