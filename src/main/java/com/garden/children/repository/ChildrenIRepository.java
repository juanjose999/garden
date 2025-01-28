package com.garden.children.repository;

import com.garden.children.entity.Children;
import com.garden.guardian.entity.Guardian;

import java.util.List;

public interface ChildrenIRepository {
    List<Children> findAllChildren();
    Children findChildrenById(int id);
    Children saveChildren(Children children, Guardian guardian);
    Children updateChildren(Children childrenUpdate, int childrenId);
    Boolean deleteChildren(Integer childrenId);
}
