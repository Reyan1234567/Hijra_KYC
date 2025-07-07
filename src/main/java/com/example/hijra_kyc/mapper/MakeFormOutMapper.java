package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.MakeFormOutDto;
import com.example.hijra_kyc.model.MakeForm;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class MakeFormOutMapper {
    public MakeFormOutDto makeFormOutMapper(MakeForm makeForm){
        System.out.println(makeForm.getHo());
        return MakeFormOutDto.builder()
                .id(makeForm.getId())
                .makerId(makeForm.getMaker().getId().intValue())
                .hoId(makeForm.getHo()!=null?makeForm.getHo().getId().intValue():0)
                .cif(makeForm.getCif())
                .customerAccount(makeForm.getCustomerAccount())
                .customerPhone(makeForm.getCustomerPhone())
                .customerName(makeForm.getCustomerName())
                .status(makeForm.getStatus())
                .branchId(makeForm.getBranchId().getBranch_id().intValue())
                .build();
    }
}
