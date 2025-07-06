package com.example.hijra_kyc.mapper;


import com.example.hijra_kyc.KycUserProfile;
import com.example.hijra_kyc.dto.MakeFormDto;
import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.service.MessageService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Data
public class MakeFormMapper {
    public MakeForm mapToMakeForm(MakeFormDto makeFormDto) {
        return MakeForm.builder()
                .cif(makeFormDto.getCif())
                .customerAccount(makeFormDto.getCustomerAccount())
                .customerPhone(makeFormDto.getCustomerPhone())
                .customerName(makeFormDto.getCustomerName())
                .makeTime(Instant.now())
                .build();
    }
}
