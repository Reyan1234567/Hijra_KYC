package com.example.hijra_kyc.dto.Imagedto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ImageDto {
    @NotBlank(message = "Description is required")
    @Size(max=30, message = "Description must be less than 30 characters")
    private String description;

    @NotBlank(message = "MakeId is required")
    private Long makeId;
}
