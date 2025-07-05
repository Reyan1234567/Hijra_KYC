package com.example.hijra_kyc.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
public class ImageDto {
    private MultipartFile imageFile;
    private String fileName;
    private int makerId;
}
