package com.example.hijra_kyc.dto.BackReasonDto;

import com.example.hijra_kyc.model.MakeForm;
import lombok.Data;

@Data
public class BackReasonInDto {
    private String comment;
    private Long makeFormId;
}
