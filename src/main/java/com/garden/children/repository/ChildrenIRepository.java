package com.garden.children.repository;

import com.garden.children.entity.Children;
import com.garden.guardian.entity.Guardian;

import java.util.List;
import java.util.Optional;

public interface ChildrenIRepository {
    List<Children> findAllChildren();
    Optional<Children> findChildrenById(int id);
    Optional<Children> findChildrenByName(String name);
    Children saveChildren(Children children, Guardian guardian);
    Optional<Children> updateChildren(Children childrenUpdate, int childrenId);
    Boolean deleteChildren(Integer childrenId);
}
