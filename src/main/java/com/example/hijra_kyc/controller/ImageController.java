package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.ImageDto;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.model.Image;
import com.example.hijra_kyc.service.BaseService;
import com.example.hijra_kyc.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.hibernate.validator.internal.util.ReflectionHelper.typeOf;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final BaseService baseService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getImages(@RequestParam Long makeId){
        BaseList<?> getImages=imageService.getImages(makeId);
        return baseService.rest(getImages);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable Long id){
        Base<?> deleteImage=imageService.deleteImage(id);
        return baseService.rest(deleteImage);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createImage(@RequestParam("file") MultipartFile file, @RequestParam("makeId") String makeId, @RequestParam("description") String description){
        ImageDto imageDto=new ImageDto();
        imageDto.setDescription(description);
        imageDto.setMakeId(Integer.parseInt(makeId));
        Base<?> createImage=imageService.createImage(file, imageDto);
        return baseService.rest(createImage);
    }
}
