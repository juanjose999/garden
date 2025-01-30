package com.garden.admin.repository;

import com.garden.admin.entity.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminIRepository{

    private final AdminIRepositoryJpa adminIRepositoryJpa;

    @Override
    public List<Admin> findAll() {
        return adminIRepositoryJpa.findAll();
    }

    @Override
    public Optional<Admin> findById(int id) {
        return adminIRepositoryJpa.findById(id);
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        return Optional.ofNullable(adminIRepositoryJpa.findByEmail(email));
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        return Optional.ofNullable(adminIRepositoryJpa.findByFullName(username));
    }

    @Override
    public Admin save(Admin admin) {
        return adminIRepositoryJpa.save(admin);
    }

    @Override
    public Optional<Admin> update(Admin admin, int id) {
        Optional<Admin> findAdmin = adminIRepositoryJpa.findById(id);
        if (findAdmin.isPresent()) {
            Admin updateAdmin = findAdmin.get();
            if(admin.getEmail() != null) updateAdmin.setEmail(admin.getEmail());
            if(admin.getPassword() != null) updateAdmin.setPassword(admin.getPassword());
            if(admin.getFullName() != null) updateAdmin.setFullName(admin.getFullName());
            return Optional.of(adminIRepositoryJpa.save(updateAdmin));
        }
        return Optional.empty();
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<Admin> admin = adminIRepositoryJpa.findById(id);
        if (admin.isEmpty()) return false;
        adminIRepositoryJpa.delete(admin.get());
        return true;
    }
}
