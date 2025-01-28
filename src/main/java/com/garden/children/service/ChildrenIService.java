package com.garden.children.service;

import com.garden.children.entity.dto.ChildrenRequestDto;
import com.garden.children.entity.dto.ChildrenResponseDto;
import com.garden.guardian.entity.dto.GuardianResponseDto;

import java.util.List;

public interface ChildrenIService {
    List<ChildrenResponseDto> findAllChildren();
    ChildrenResponseDto findChildrenById(int id);
    GuardianResponseDto saveChildren(ChildrenRequestDto children, Integer idGuardian);
    ChildrenResponseDto updateChildren(ChildrenRequestDto childrenUpdate, int childrenId);
    Boolean deleteChildren(Integer childrenId);
}
