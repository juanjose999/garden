package com.garden.auth.service;

import com.garden.admin.entity.LoginRequestUser;
import com.garden.admin.entity.MyUser;
import io.vavr.control.Either;

import java.util.Map;

public interface AuthIService {
    Either<Map<String, String>, Map<String, String>> login(LoginRequestUser loginRequestUser);
    MyUser save(MyUser myUser);
}
