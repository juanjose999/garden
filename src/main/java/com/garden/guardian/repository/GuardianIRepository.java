package com.garden.guardian.repository;

import com.garden.guardian.entity.Guardian;

import java.util.List;

public interface GuardianIRepository {
    List<Guardian> findAllGuardian();
    Guardian findGuardianById(int id);
    Guardian saveGuardian(Guardian guardian);
    Guardian updateGuardian(Guardian guardian, int id);
    Boolean deleteGuardian(Integer id);
}
