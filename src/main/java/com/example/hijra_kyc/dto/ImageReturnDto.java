package com.example.hijra_kyc.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImageReturnDto {
    private int id;
    private String name;
    private String description;
    private int make_id;
}
