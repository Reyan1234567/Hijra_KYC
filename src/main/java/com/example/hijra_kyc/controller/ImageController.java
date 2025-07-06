package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.ImageDto;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.model.Image;
import com.example.hijra_kyc.service.BaseService;
import com.example.hijra_kyc.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createImage(@RequestBody ImageDto image){
        Base<?> createImage=imageService.createImage(image);
        return baseService.rest(createImage);
    }
}
