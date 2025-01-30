package com.garden.children.repository;

import com.garden.children.entity.Children;
import com.garden.guardian.entity.Guardian;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChildrenRepositoryImpl implements ChildrenIRepository{

    private final ChildrenIRepositoryJpa childrenIRepositoryJpa;

    @Override
    public List<Children> findAllChildren() {
        return childrenIRepositoryJpa.findAll();
    }

    @Override
    public Optional<Children> findChildrenById(int id) {
        return childrenIRepositoryJpa.findById(id);
    }

    @Override
    public Optional<Children> findChildrenByName(String name) {
        return Optional.empty();
    }

    @Override
    public Children saveChildren(Children children, Guardian guardian) {
        Children children1 = childrenIRepositoryJpa.save(children);
        children1.setGuardian(guardian);
        return childrenIRepositoryJpa.save(children1);
    }

    @Override
    public Optional<Children> updateChildren(Children childrenUpdate, int childrenId) {
        Optional<Children> children = findChildrenById(childrenId);
        if (children.isPresent()) {
            if(childrenUpdate.getFullName() != null) children.get().setFullName(childrenUpdate.getFullName());
            if(childrenUpdate.getGuardian() != null) children.get().setGuardian(childrenUpdate.getGuardian());
            return Optional.of(childrenIRepositoryJpa.save(children.get()));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteChildren(Integer childrenId) {
        if(childrenIRepositoryJpa.findById(childrenId).isEmpty()) return false;
        childrenIRepositoryJpa.deleteById(childrenId);
        return true;
    }
}
