package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.ImageDto;
import com.example.hijra_kyc.model.Image;
import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.repository.MakeFormRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageMapper {
    private MakeFormRepository makeFormRepository;
    public Image mapImageDtoToImage(ImageDto imageDto){
        MakeForm makeId=new MakeForm();
        makeId.setId(imageDto.getMakerId());
        return Image.builder()
                .imageMake(makeId)
                .build();
    }
}

