package com.example.hijra_kyc.dto.FormDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import com.example.hijra_kyc.dto.Imagedto.ImageReturnDto;

@Builder
@Data
public class MakeFormOutDto {
    private Long id;
    private Long makerId;
    private String makerName;
    private Long hoId;
    private String hoName;
    private String cif;
    private String customerAccount;
    private String customerPhone;
    private String customerName;
    private int status;
    private Long branchId;
    private List<ImageReturnDto> image;
}
