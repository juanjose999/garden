package com.garden.admin.repository;

import com.garden.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminIRepositoryJpa extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);
    Admin findByFullName(String fullName);
}
