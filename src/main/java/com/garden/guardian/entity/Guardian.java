package com.garden.guardian.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.garden.admin.entity.Admin;
import com.garden.children.entity.Children;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Guardian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String full_name;
    private String n_whasapp;
    @OneToMany(mappedBy = "guardian", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Children> childrens = new HashSet<>();
    @ManyToOne
    private Admin admin;

    public Guardian(String full_name, String n_whasapp) {
        this.full_name = full_name;
        this.n_whasapp = n_whasapp;
        this.childrens = new HashSet<>();
    }

    public void setChildrens(Children childrens) {
        if (childrens != null) {
            this.childrens = new HashSet<>();
        }
        this.childrens.add(childrens);
    }
}
