package com.example.hijra_kyc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class MakeFormOutDto {
    @NotBlank(message = "id is required")
    private int id;
    @NotBlank(message = "makerId is required")
    private int makerId;
    @NotBlank(message = "hoId is required")
    private int hoId;
    @NotBlank(message = "cif is required")
    private String cif;
    @NotBlank(message = "customerAccount is required")
    private String customerAccount;
    @NotBlank(message = "customerPhone is required")
    private String customerPhone;
    @NotBlank(message = "customerName is required")
    private String customerName;
    @NotBlank(message = "status is required")
    private int status;
    @NotBlank(message = "branchId is required")
    private int branchId;
}
