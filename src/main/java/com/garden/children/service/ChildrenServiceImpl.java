package com.garden.children.service;

import com.garden.exception.ChildrenException;
import com.garden.children.entity.Children;
import com.garden.children.entity.dto.ChildrenMapper;
import com.garden.children.entity.dto.ChildrenRequestDto;
import com.garden.children.entity.dto.ChildrenResponseDto;
import com.garden.children.repository.ChildrenIRepository;
import com.garden.exception.GuardianException;
import com.garden.guardian.entity.Guardian;
import com.garden.guardian.entity.dto.GuardianMapper;
import com.garden.guardian.entity.dto.GuardianResponseDto;
import com.garden.guardian.repository.GuardianIRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public ChildrenResponseDto findChildrenById(int id) throws ChildrenException {
        Optional<Children> findChildren = childrenRepository.findChildrenById(id);
        if(findChildren.isEmpty()) throw new ChildrenException("El niño con el id : " + id + " no fue encontrado.");
        return ChildrenMapper.childrenToChildrenResponseDto(findChildren.get());
    }

    @Override
    public ChildrenResponseDto findChildrenByName(String name) {
        return null;
    }

    @Override
    public GuardianResponseDto saveChildren(ChildrenRequestDto children, Integer id_guardian) throws ChildrenException, GuardianException {

        Guardian findGuardian = guardianRepository.findById(id_guardian).orElseThrow(
                () -> new GuardianException("Guardian not found.")
        );

        if(findAllChildren().stream().anyMatch(childrenResponseDto -> childrenResponseDto.nombre().equalsIgnoreCase(children.name()))){
            throw new ChildrenException("El nino con el nombre :" + children.name() + " ya existe, Ingrese un nombre que no exista.");
        }
        Children childrenSaved = childrenRepository.saveChildren(ChildrenMapper.childrenRequestDtoToChildren(children),findGuardian);
        findGuardian.setChildrens(childrenSaved);
        guardianRepository.save(findGuardian);
        return GuardianMapper.guardianToGuardianResponseDto(childrenSaved.getGuardian());
    }

    @Override
    public ChildrenResponseDto updateChildren(ChildrenRequestDto childrenUpdate, int childrenId) throws ChildrenException {
        Optional<Children> updateChild = childrenRepository.updateChildren(ChildrenMapper.childrenRequestDtoToChildren(childrenUpdate), childrenId);
        if(updateChild.isEmpty()) throw new ChildrenException("Error en la actualización de los datos, comuniquese con soporte.");
        return ChildrenMapper.childrenToChildrenResponseDto(updateChild.get());
    }

    @Override
    public Boolean deleteChildren(Integer childrenId) {
        return childrenRepository.deleteChildren(childrenId);
    }
}
