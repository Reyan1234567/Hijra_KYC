package com.example.hijra_kyc.dto.Imagedto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImageReturnDto {
    private Long id;
    private String url;
    private String description;
    private String descriptionCopy;
    private Long make_id;
}
