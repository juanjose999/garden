package com.garden.admin.repository;

import com.garden.admin.entity.MyUser;

import java.util.List;

public interface AdminIRepository {
    List<MyUser> findAll();
    MyUser findById(int id);
    MyUser save(MyUser myUser);
    MyUser update(MyUser myUser, int id);
    Boolean delete(Integer id);
}
