package com.example.hijra_kyc.dto.Imagedto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImageReturnDto {
    private Long id;
    private String name;
    private String description;
    private Long make_id;
}
