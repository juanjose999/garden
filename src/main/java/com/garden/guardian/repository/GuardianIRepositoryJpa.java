package com.garden.guardian.repository;

import com.garden.guardian.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianIRepositoryJpa extends JpaRepository<Guardian, Integer> {
}
