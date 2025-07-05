package com.example.hijra_kyc.mapper;


import com.example.hijra_kyc.KycUserProfile;
import com.example.hijra_kyc.dto.MakeFormDto;
import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.service.MessageService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class MakeFormMapper {
    //a public static method to get the user's credentials
    KycUserProfile MakerId=new KycUserProfile();
//    MakerId.setId(4L);
    public MakeForm mapToMakeForm(MakeFormDto makeFormDto) {
        return MakeForm.builder()
                .maker(MakerId)
                .cif(makeFormDto.getCif())
                .customerAccount(makeFormDto.getCustomerAccount())
                .customerPhone(makeFormDto.getCustomerPhone())
                .customerName(makeFormDto.getCustomerName())
                .build();
    }
}
