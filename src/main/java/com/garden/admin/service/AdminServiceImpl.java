package com.garden.admin.service;

import com.garden.admin.entity.LoginRequestUser;
import com.garden.admin.entity.MyUser;
import com.garden.admin.repository.AdminIRepository;
import com.garden.config.JwtService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminIService {

    private final AdminIRepository adminIRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public List<MyUser> findAll() {
        return adminIRepository.findAll();
    }

    @Override
    public MyUser findById(int id) {
        MyUser myUser = adminIRepository.findById(id);
        if (myUser == null) throw new RuntimeException("No se encontro el usuario");
        return myUser;
    }


    @Override
    public MyUser save(MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        return adminIRepository.save(myUser);
    }

    @Override
    public MyUser update(MyUser myUser, int id) {
        return adminIRepository.update(myUser, id);
    }

    @Override
    public Boolean delete(Integer id) {
        return adminIRepository.delete(id);
    }

}
