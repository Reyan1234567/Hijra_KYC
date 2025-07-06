package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.ImageReturnDto;
import com.example.hijra_kyc.model.Image;
import org.springframework.stereotype.Service;

@Service
public class ImageOutMapper {
    public ImageReturnDto toImageDto(Image image) {
        return ImageReturnDto.builder()
                .id(image.getId())
                .description(image.getImageDescription())
                .name(image.getImageName())
                .make_id(image.getImageMake().getId())
                .build();
    }
}
