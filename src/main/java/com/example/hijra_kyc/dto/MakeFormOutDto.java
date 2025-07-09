package com.example.hijra_kyc.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MakeFormOutDto {
    private Long id;
    private Long makerId;
    private Long hoId;
    private String cif;
    private String customerAccount;
    private String customerPhone;
    private String customerName;
    private int status;
    private Long branchId;
    private List<ImageReturnDto> image;
}
