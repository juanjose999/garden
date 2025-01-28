package com.garden.admin.controller;

import com.garden.admin.entity.LoginRequestUser;
import com.garden.admin.entity.MyUser;
import com.garden.admin.service.AdminIService;
import com.garden.config.JwtService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminIService adminIService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(adminIService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(adminIService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        Boolean deleted = adminIService.delete(id);
        return deleted ?
                ResponseEntity.status(HttpStatus.OK).body("Se elimino el registro correctamente.") :
                ResponseEntity.notFound().build();
    }

}
