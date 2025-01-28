package com.garden.children.repository;

import com.garden.children.entity.Children;
import com.garden.guardian.entity.Guardian;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChildrenRepositoryImpl implements ChildrenIRepository{

    private final ChildrenIRepositoryJpa childrenIRepositoryJpa;

    @Override
    public List<Children> findAllChildren() {
        return childrenIRepositoryJpa.findAll();
    }

    @Override
    public Children findChildrenById(int id) {
        return childrenIRepositoryJpa.findById(id).orElseThrow(() -> new RuntimeException("Child not found"));
    }

    @Override
    public Children saveChildren(Children children, Guardian guardian) {
        Children children1 = childrenIRepositoryJpa.save(children);
        children1.setGuardian(guardian);
        return childrenIRepositoryJpa.save(children1);
    }

    @Override
    public Children updateChildren(Children childrenUpdate, int childrenId) {
        Children children = findChildrenById(childrenId);
        if(childrenUpdate.getFull_name() != null) children.setFull_name(childrenUpdate.getFull_name());
        if(childrenUpdate.getGuardian() != null) children.setGuardian(childrenUpdate.getGuardian());
        return childrenIRepositoryJpa.save(children);
    }

    @Override
    public Boolean deleteChildren(Integer childrenId) {
        if(childrenIRepositoryJpa.findById(childrenId).isEmpty()) return false;
        childrenIRepositoryJpa.deleteById(childrenId);
        return true;
    }
}
