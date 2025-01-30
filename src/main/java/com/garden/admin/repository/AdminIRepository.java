package com.garden.admin.repository;

import com.garden.admin.entity.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminIRepository {
    List<Admin> findAll();
    Optional<Admin> findById(int id);
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByUsername(String username);
    Admin save(Admin admin);
    Optional<Admin> update(Admin admin, int id);
    Boolean delete(Integer id);
}
