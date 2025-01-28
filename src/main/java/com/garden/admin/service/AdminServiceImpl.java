package com.garden.admin.service;

import com.garden.admin.entity.LoginRequestUser;
import com.garden.admin.entity.MyUser;
import com.garden.admin.repository.AdminIRepository;
import com.garden.admin.repository.AdminIRepositoryJpa;
import com.garden.jwt.JWTService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final JWTService jwtService;

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
    public Either<Map<String, String>, Map<String, String>> login(LoginRequestUser loginRequestUser) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestUser.email(), loginRequestUser.password()
        ));
        if(authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return Either.right(Map.of("token : ", jwtService.generateToken(userDetails)));
        }
        return Either.left(Map.of("Error : ","Invalid credentials"));
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
