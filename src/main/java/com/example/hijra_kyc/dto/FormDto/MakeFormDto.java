package com.example.hijra_kyc.dto.FormDto;

import com.example.hijra_kyc.model.Image;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@AllArgsConstructor
@Data
public class MakeFormDto {
    @NotNull(message = "makeId is required")
    private Long makerId;

    @NotBlank(message = "cif is required")
    private String cif;

    @NotBlank(message = "customerAccount is required")
    private String customerAccount;

    @NotBlank(message = "customerPhone is required")
    private String customerPhone;

    @NotBlank(message = "customerName is required")
    private String customerName;

    @NotNull
    private String[] descriptions;
}


//
//@Data
//public class MakeFormDto {
//    @NotNull(message = "makeId is required")
//    private Integer makerId;
//
//    @NotBlank(message = "cif is required")
//    private String cif;
//
//    @NotBlank(message = "customerAccount is required")
//    private String customerAccount;
//
//    @NotBlank(message = "customerPhone is required")
//    private String customerPhone;
//
//    @NotBlank(message = "customerName is required")
//    private String customerName;
//
//}

