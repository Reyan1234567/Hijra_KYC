package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.ImageDto;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.service.BaseService;
import com.example.hijra_kyc.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final BaseService baseService;

//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<?> deleteImage(@PathVariable Long id){
//        String deleteImage=imageService.deleteImage(id);
//        return ResponseEntity.ok(deleteImage);
//    }


    @PostMapping("/create-Images/{makeId}")
    public ResponseEntity<?> saveImages(@Valid @RequestBody List<ImageDto> imageListDto, @PathVariable Long makeId){
        String newImages=imageService.createImages(imageListDto, makeId);
        return ResponseEntity.ok(newImages);
    }


    @PatchMapping("/description")
    public ResponseEntity<?> editDescription(@RequestParam("description") String description, @RequestParam("id") int id){
        String newImage=imageService.editDescription(description, id);
        return ResponseEntity.ok(newImage);
    }



    //    @PostMapping("/create")
//    public ResponseEntity<?> createImage(@Valid @RequestBody ImageDto imageDto){
//       Base<?> createdImage=imageService.createImage(imageDto);
//       return baseService.rest(createdImage);
//    }

    //    @GetMapping("/getAll")
//    public ResponseEntity<?> getImages(@RequestParam Long makeId){
//        BaseList<?> getImages=imageService.getImages(makeId);
//        return baseService.rest(getImages);
//    }
}
