package com.example.hijra_kyc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
public class ImageDto {
    @NotBlank(message = "Description is required")
    @Size(max=255, message = "Description must be less than 255 charcters")
    private String description;

    @NotBlank(message = "MakeId is required")
    private int makeId;
}
