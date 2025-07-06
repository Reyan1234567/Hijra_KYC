package com.example.hijra_kyc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class MakeFormDto {
    @NotBlank(message = "makeId is required")
    private Integer makerId;

    @NotBlank(message = "cif is required")
    private String cif;

    @NotBlank(message = "customerAccount is required")
    private String customerAccount;

    @NotBlank(message = "customerPhone is required")
    private String customerPhone;

    @NotBlank(message = "customerName is required")
    private String customerName;
}


