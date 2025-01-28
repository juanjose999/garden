package com.garden.guardian.repository;

import com.garden.guardian.entity.Guardian;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GuardianRepositoryImpl implements GuardianIRepository {

    private final GuardianIRepositoryJpa repositoryJpa;

    @Override
    public List<Guardian> findAllGuardian() {
        return repositoryJpa.findAll();
    }

    @Override
    public Guardian findGuardianById(int id) {
        return repositoryJpa.findById(id).orElse(null);
    }

    @Override
    public Guardian saveGuardian(Guardian guardian) {
        return repositoryJpa.save(guardian);
    }

    @Override
    public Guardian updateGuardian(Guardian guardianNewData, int id) {
        Guardian guardian = findGuardianById(id);
        if(guardianNewData.getFull_name()!= null) guardian.setFull_name(guardianNewData.getFull_name());
        if(guardianNewData.getN_whasapp()!=null) guardian.setN_whasapp(guardianNewData.getN_whasapp());
        return repositoryJpa.save(guardian);
    }

    @Override
    public Boolean deleteGuardian(Integer id) {
        Guardian guardianToDelete = findGuardianById(id);
        if(guardianToDelete!=null) {
            repositoryJpa.delete(guardianToDelete);
            return true;
        };
        return false;
    }
}
