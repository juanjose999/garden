package com.garden.admin.repository;

import com.garden.admin.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminIRepositoryJpa extends JpaRepository<MyUser, Integer> {
    MyUser findByEmail(String email);
}
