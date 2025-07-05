package com.example.hijra_kyc.service;


import com.example.hijra_kyc.KycUserProfile;
import com.example.hijra_kyc.dto.ImageDto;
import com.example.hijra_kyc.mapper.ImageMapper;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.model.Image;
import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.repository.ImageRepository;
import com.example.hijra_kyc.repository.MakeFormRepository;
import com.example.hijra_kyc.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ImageService {

    private final BaseService baseService;
    private ImageRepository imageRepository;
    private MakeFormRepository makeFormRepository;
    private UserRepository userRepository;
    private ImageMapper imageMapper;

    public BaseList<?> getImages(Long makeId) {
        MakeForm makeForm = makeFormRepository.findById(makeId.intValue())
                .orElseThrow(()->new RuntimeException("Maker not found"));
        List<Image> listOfImages=makeForm.getImages();
        return baseService.listSuccess(listOfImages);
    }

    public Base<?> deleteImage(Long id) {
        try{
            Image image = imageRepository.findById(id.intValue())
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            imageRepository.delete(image);
            return baseService.success("Image Deleted successfully");
        }
        catch (Exception e){
            return baseService.error(e.getMessage());
        }
    }

    public Base<?> createImage(ImageDto imageDto) {
        try{
            var image = imageMapper.mapImageDtoToImage(imageDto);
            MakeForm makeForm=makeFormRepository.findById(image.getImageMake().getId())
                    .orElseThrow(()->new RuntimeException("can't find makeId"));
            LocalDate today = LocalDate.now();
            String cif=makeForm.getCif();

            //uploading the file
            var maker=userRepository.findById(image.getImageMake().getMaker().getId().longValue())
                    .orElseThrow(()->new RuntimeException("Can't find makeId"));
            String makerName=maker.getFirstName();

            String filePath="upload/"+makerName+"/"+today+"/"+cif+"/"+LocalTime.now()+"/"+image.getImageName()+"/";
            File uploadDir = new File(filePath);
            if (!uploadDir.exists()) {
                var success = uploadDir.mkdirs();
                if (!success) {
                    return baseService.error("Can't create directory");
                }
            }
            imageDto.getImageFile().transferTo(uploadDir);
            //saving image details to the db
                //set image name before saving
            image.setImageName(filePath);
            return baseService.success(imageRepository.save(image));
        }
        catch(Exception e){
            return baseService.error(e.getMessage());
        }
    }
}
