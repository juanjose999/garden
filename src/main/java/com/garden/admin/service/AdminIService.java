package com.garden.admin.service;

import com.garden.admin.entity.LoginRequestUser;
import com.garden.admin.entity.MyUser;
import io.vavr.control.Either;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Map;

public interface AdminIService {
    List<MyUser> findAll();
    MyUser findById(int id);
    MyUser save(MyUser myUser);
    MyUser update(MyUser myUser, int id);
    Boolean delete(Integer id);
}
