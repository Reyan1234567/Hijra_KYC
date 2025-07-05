package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.MessageEdit;
import com.example.hijra_kyc.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")
public interface EditMessageMapper {
    void update(MessageEdit messageEdit, @MappingTarget Message message);

}
