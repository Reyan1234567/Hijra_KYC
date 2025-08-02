package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.Imagedto.DescriptionDto;
import com.example.hijra_kyc.dto.Imagedto.ImageDto;
import com.example.hijra_kyc.dto.Imagedto.ImageReturnDto;
import com.example.hijra_kyc.mapper.ImageMapper;
import com.example.hijra_kyc.model.*;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.ImageRepository;
import com.example.hijra_kyc.repository.MakeFormRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;
import com.example.hijra_kyc.util.FileUpload;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final BaseService baseService;
    private final BranchRepository branchRepository;
    private final ImageRepository imageRepository;
    private final MakeFormRepository makeFormRepository;
    private final FileUpload fileUpload;
    private final UserProfileRepository userRepository;
    private final ImageMapper imageMapper;

    @Value("${server.port}")
    private String port;

    public List<ImageReturnDto> getImages(Long makeId) {
        System.out.println("getImages");
        MakeForm makeForm = makeFormRepository.findById(makeId)
                .orElseThrow(() -> new RuntimeException("Maker not found"));
        List<Image> listOfImages = makeForm.getImages();
        if(listOfImages.isEmpty()){
            throw new RuntimeException("Maker not found");
        }
        System.out.println(listOfImages);
        return listOfImages.stream()
                        .map(imageMapper::toImageDto)
                        .toList();

    }


    public void createImage(@Valid ImageDto imageDto, Long makeId){
        try{
            var image = imageMapper.mapImageDtoToImage(imageDto);
            MakeForm makeForm = makeFormRepository.findById(makeId)
                    .orElseThrow(() -> new RuntimeException("can't find form-makeId"));
            image.setImageMake(makeForm);
            Branch branch = branchRepository.findById(image.getImageMake().getMaker().getBranch().getBranchId())
                    .orElseThrow(() -> new RuntimeException("branch not found"));

            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();
            String branchName = branch.getName();
            String cif = makeForm.getCif();

            String fileType= imageDto.getFile().split(",")[0].split("/")[1].split(";")[0];


            //check if the file sent is an image
            String variable=Paths.get(branchName, today.toString(), cif, makeForm.getCustomerAccount()).toString();
            String unique=now.toString().replace(":", "-").replace(".", "-") + "__" + image.getImageDescription().trim().replaceAll("\\s+","_") + today + cif + "."+fileType;
            String filePath = Paths.get("C:", "Users", "hp", "Videos", "Hijra_KYC", "upload", variable).toString();
            String fileName = Paths.get(filePath,unique).toString();

            fileUpload.createFile(imageDto.getFile(), filePath, fileName, fileType);

            image.setImageUrl("http://localhost:"+port+"/upload/"+variable.replace("\\","/")+"/"+unique);
            imageRepository.save(image);
//            baseService.success("Image created successfully");
        }
        catch (Exception e){
//            baseService.error(e.getMessage());
//            imageRepository.deleteAllById();
            System.out.println(e.getMessage());
        }
    }


    public String editDescription(DescriptionDto description, Long id) {
            Image image = imageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            MakeForm makeForm=image.getImageMake();
            if(makeForm.getStatus()!=3 && makeForm.getStatus() != 0){
                System.out.println(makeForm.getStatus());
                throw new EntityNotFoundException("Can't edit a pending or an accepted Make-Request");
            }
            if(makeForm.getStatus() ==3){
                makeForm.setStatus(0);
                makeForm.setHoActionTime(null);
                makeFormRepository.save(makeForm);
            }
            image.setImageDescription(description.getDescription());
            imageRepository.save(image);
            return "Image Edited successfully";
    }

    public String createImages(@Valid List<ImageDto> imageListDto, Long makeId) {
        MakeForm makeForm=makeFormRepository.findById(makeId).orElseThrow(() -> new RuntimeException("Maker not found"));

        if(makeForm.getStatus()!=3 && makeForm.getStatus() != 0){
            throw new EntityNotFoundException("Can't edit a pending or an accepted Make-Request");
        }

        if(makeForm.getStatus() ==3){
            makeForm.setStatus(0);
            makeForm.setHoActionTime(null);
            makeFormRepository.save(makeForm);
        }

            for (ImageDto image : imageListDto) {
                createImage(image, makeId);
            }
            return "Images created successfully";

    }

    public String disassociate(Long id) {
        System.out.println(id);
        Image image=imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        MakeForm makeForm=image.getImageMake();
        if(makeForm.getStatus()!=3 && makeForm.getStatus() != 0){
            throw new EntityNotFoundException("Can't edit a pending or an accepted Make-Request");
        }
        if(makeForm.getStatus() ==3){
            makeForm.setStatus(0);
            makeForm.setHoActionTime(null);
            makeFormRepository.save(makeForm);
        }
        System.out.println(makeForm.getId());
        makeForm.setStatus(0);
        makeForm.setHoActionTime(null);
        makeFormRepository.save(makeForm);
        image.setImageMake(null);
        imageRepository.save(image);
        return "Updated successfully";
    }
}
