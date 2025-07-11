package com.example.hijra_kyc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
public class ImageDto {
    @NotBlank(message = "Description is required")
    @Size(max=30, message = "Description must be less than 30 characters")
    private String description;

    @NotNull(message = "file is required")
    @Pattern(regexp = "^data:image/[^;]+;base64,.*$", message = "This is not a correct image")
    private String file;
}
