package com.garden.guardian.controller;

import com.garden.children.entity.dto.ChildrenRequestDto;
import com.garden.children.service.ChildrenIService;
import com.garden.guardian.entity.dto.GuardianRequestDto;
import com.garden.guardian.repository.GuardianIRepositoryJpa;
import com.garden.guardian.entity.Guardian;
import com.garden.guardian.service.GuardianIService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/guardians")
@RequiredArgsConstructor
public class GuardianController {

    private final GuardianIService guardianService;
    private final ChildrenIService childrenService;

    @GetMapping("/health")
    public String health(HttpServletRequest request) {
        return "OK " + request.getRemoteAddr();
    }

    @PostMapping("/id/{id}/childrens")
    public ResponseEntity<?> create(@RequestBody ChildrenRequestDto childrenRequestDto, @PathVariable Integer id) {
        return ResponseEntity.ok(childrenService.saveChildren(childrenRequestDto, id));
    }

    @GetMapping
    public ResponseEntity<?> allGuardians() {
        return ResponseEntity.ok(guardianService.findAllGuardian());
    }

    @PostMapping
    public ResponseEntity<?> createGuardian(@RequestBody GuardianRequestDto guardian){
        return ResponseEntity.ok(guardianService.saveGuardian(guardian));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGuardian(@RequestBody GuardianRequestDto guardian, Integer id){
        return ResponseEntity.ok(guardianService.updateGuardian(guardian, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuardian(@PathVariable Integer id){
        boolean deleted = guardianService.deleteGuardian(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
