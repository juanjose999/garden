package com.garden.children.service;

import com.garden.children.entity.Children;
import com.garden.exception.ChildrenException;
import com.garden.children.entity.dto.ChildrenRequestDto;
import com.garden.children.entity.dto.ChildrenResponseDto;
import com.garden.exception.GuardianException;
import com.garden.guardian.entity.dto.GuardianResponseDto;

import java.util.List;
import java.util.Optional;

public interface ChildrenIService {
    List<ChildrenResponseDto> findAllChildren();
    ChildrenResponseDto findChildrenById(int id) throws ChildrenException;
    ChildrenResponseDto findChildrenByName(String name);
    GuardianResponseDto saveChildren(ChildrenRequestDto children, Integer idGuardian) throws ChildrenException, GuardianException;
    ChildrenResponseDto updateChildren(ChildrenRequestDto childrenUpdate, int childrenId) throws ChildrenException;
    Boolean deleteChildren(Integer childrenId);
}
