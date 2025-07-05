package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.MakeFormDto;
import com.example.hijra_kyc.model.MakeForm;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel="spring")
public interface MakeUpdateMapper {
     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     void toMakeForm(MakeFormDto makeFormDto, @MappingTarget MakeForm makeForm);
}
