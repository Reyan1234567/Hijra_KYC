package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.ImageDto;
import com.example.hijra_kyc.dto.ImageReturnDto;
import com.example.hijra_kyc.model.Image;
import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.repository.MakeFormRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageMapper {
    public Image mapImageDtoToImage(ImageDto imageDto){
        return Image.builder()
                .imageDescription(imageDto.getDescription())
                .build();
    }

    public ImageReturnDto toImageDto(Image image) {
        return ImageReturnDto.builder()
                .id(image.getId())
                .description(image.getImageDescription())
                .name(image.getImageName())
                .make_id(image.getImageMake().getId())
                .build();
    }
}

