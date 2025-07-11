package com.example.hijra_kyc.dto.FormDto;

import com.example.hijra_kyc.model.Image;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@AllArgsConstructor
@Data
public class MakeFormDto {
    @NotNull(message = "makeId is required")
    private Long makerId;

    @NotBlank(message = "cif is required")
    @Pattern(regexp="^\\d{5}$", message = "cif is not in the correct format")
    private String cif;

    @NotBlank(message = "customerAccount is required")
    @Pattern(regexp = "^100\\d{7}$", message = "customerAccount is not in the correct format")
    private String customerAccount;

    @NotBlank(message = "customerPhone is required")
    @Pattern(regexp = "^(09|07)\\d{8}$", message = "customerPhone is not in the correct format")
    private String customerPhone;

    @NotBlank(message = "customerName is required")
    private String customerName;

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

