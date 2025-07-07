package com.example.hijra_kyc.dto;

import com.example.hijra_kyc.model.Image;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class MakeFormOutDto {
    private int id;
    private int makerId;
    private int hoId;
    private String cif;
    private String customerAccount;
    private String customerPhone;
    private String customerName;
    private int status;
    private int branchId;
    private List<ImageReturnDto> image;
}
