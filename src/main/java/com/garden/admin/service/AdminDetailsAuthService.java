package com.garden.admin.service;

import com.garden.admin.entity.Admin;
import com.garden.admin.repository.AdminIRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailsAuthService implements UserDetailsService {

    private final AdminIRepositoryJpa adminIRepositoryJpa;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin findUser = adminIRepositoryJpa.findByEmail(username);
        if (findUser == null) throw new RuntimeException("No se encontro el usuario");
        return findUser;
    }

}
