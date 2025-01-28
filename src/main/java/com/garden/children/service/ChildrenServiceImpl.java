package com.garden.children.service;

import com.garden.children.entity.Children;
import com.garden.children.entity.dto.ChildrenMapper;
import com.garden.children.entity.dto.ChildrenRequestDto;
import com.garden.children.entity.dto.ChildrenResponseDto;
import com.garden.children.repository.ChildrenIRepository;
import com.garden.guardian.entity.Guardian;
import com.garden.guardian.entity.dto.GuardianMapper;
import com.garden.guardian.entity.dto.GuardianResponseDto;
import com.garden.guardian.repository.GuardianIRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildrenServiceImpl implements ChildrenIService {

    private final ChildrenIRepository childrenRepository;
    private final GuardianIRepositoryJpa guardianRepository;

    @Override
    public List<ChildrenResponseDto> findAllChildren() {
        return childrenRepository.findAllChildren().stream()
                .map(ChildrenMapper::childrenToChildrenResponseDto)
                .toList();
    }

    @Override
    public ChildrenResponseDto findChildrenById(int id) {
        return ChildrenMapper.childrenToChildrenResponseDto(
                childrenRepository.findChildrenById(id)
        );
    }

    @Override
    public GuardianResponseDto saveChildren(ChildrenRequestDto children, Integer id_guardian) {
        Guardian findGuardian = guardianRepository.findById(id_guardian).orElseThrow(() -> new IllegalArgumentException("Guardian not found"));
        Children childrenSaved = childrenRepository.saveChildren(ChildrenMapper.childrenRequestDtoToChildren(children),findGuardian);
        findGuardian.setChildrens(childrenSaved);
        guardianRepository.save(findGuardian);
        return GuardianMapper.guardianToGuardianResponseDto(childrenSaved.getGuardian());
    }

    @Override
    public ChildrenResponseDto updateChildren(ChildrenRequestDto childrenUpdate, int childrenId) {
        return ChildrenMapper.childrenToChildrenResponseDto(
                childrenRepository.updateChildren(ChildrenMapper.childrenRequestDtoToChildren(childrenUpdate), childrenId)
        );
    }

    @Override
    public Boolean deleteChildren(Integer childrenId) {
        return childrenRepository.deleteChildren(childrenId);
    }
}
