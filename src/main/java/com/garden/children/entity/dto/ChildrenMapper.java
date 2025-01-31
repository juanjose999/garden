package com.garden.children.entity.dto;

import com.garden.children.entity.Children;

public class ChildrenMapper {

    public static ChildrenResponseDto childrenToChildrenResponseDto(Children children) {
        return ChildrenResponseDto.builder()
                .id(children.getId())
                .nombre(children.getFullName())
                .paymentList(children.getPaymentList())
                .build();
    }

    public static Children childrenRequestDtoToChildren(ChildrenRequestDto childrenRequestDto) {
        return Children.builder()
                .fullName(childrenRequestDto.name())
                .isFemale(childrenRequestDto.isFemale())
                .age(childrenRequestDto.age())
                .build();
    }

}
