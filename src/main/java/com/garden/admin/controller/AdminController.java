package com.garden.admin.controller;

import com.garden.admin.service.AdminIService;
import com.garden.config.JwtService;
import com.garden.exception.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> findById(@PathVariable Integer id) throws AdminException {
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
