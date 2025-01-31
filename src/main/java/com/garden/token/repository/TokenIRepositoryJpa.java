package com.garden.token.repository;

import com.garden.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenIRepositoryJpa extends JpaRepository<Token, Long> {
    List<Token> findAllValidIsFalseOrRevokedIsFalseByAdminId(int admin_id);
    Optional<Token> findByToken(String token);
}
