package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.MakeFormDto;
import com.example.hijra_kyc.dto.MakeFormOutDto;
import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.service.MessageService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Data
public class MakeFormMapper {
    private final ImageMapper imageMapper;
    public MakeForm mapToMakeForm(MakeFormDto makeFormDto) {
        return MakeForm.builder()
                .cif(makeFormDto.getCif())
                .customerAccount(makeFormDto.getCustomerAccount())
                .customerPhone(makeFormDto.getCustomerPhone())
                .customerName(makeFormDto.getCustomerName())
                .makeTime(Instant.now())
                .build();
    }

    public MakeFormOutDto makeFormOutMapper(MakeForm makeForm){
        return MakeFormOutDto.builder()
                .id(makeForm.getId())
                .makerId(makeForm.getMaker().getId())
                .hoId(makeForm.getHo()!=null?makeForm.getHo().getId():0)
                .cif(makeForm.getCif())
                .customerAccount(makeForm.getCustomerAccount())
                .customerPhone(makeForm.getCustomerPhone())
                .customerName(makeForm.getCustomerName())
                .status(makeForm.getStatus())
                .branchId(makeForm.getBranchId().getBranchId())
                .image(makeForm.getImages().stream().map(imageMapper::toImageDto).toList())
                .build();
    }
}
