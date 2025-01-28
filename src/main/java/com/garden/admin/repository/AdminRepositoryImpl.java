package com.garden.admin.repository;

import com.garden.admin.entity.MyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminIRepository{

    private final AdminIRepositoryJpa adminIRepositoryJpa;

    @Override
    public List<MyUser> findAll() {
        return adminIRepositoryJpa.findAll();
    }

    @Override
    public MyUser findById(int id) {
        return adminIRepositoryJpa.findById(id).orElse(null);
    }

    @Override
    public MyUser save(MyUser myUser) {
        return adminIRepositoryJpa.save(myUser);
    }

    @Override
    public MyUser update(MyUser myUser, int id) {
        MyUser updatedMyUser = adminIRepositoryJpa.findById(id).orElse(null);
        if (updatedMyUser != null) {
            if(myUser.getEmail() != null) updatedMyUser.setEmail(myUser.getEmail());
            if(myUser.getPassword() != null) updatedMyUser.setPassword(myUser.getPassword());
            if(myUser.getFull_name() != null) updatedMyUser.setFull_name(myUser.getFull_name());
            return adminIRepositoryJpa.save(updatedMyUser);
        }
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        MyUser myUser = adminIRepositoryJpa.findById(id).orElse(null);
        if (myUser == null) return false;
        adminIRepositoryJpa.delete(myUser);
        return true;
    }
}
