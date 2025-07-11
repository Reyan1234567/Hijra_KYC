package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.Imagedto.ImageDto;
import com.example.hijra_kyc.dto.Imagedto.ImageReturnDto;
import com.example.hijra_kyc.mapper.ImageMapper;
import com.example.hijra_kyc.model.*;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.ImageRepository;
import com.example.hijra_kyc.repository.MakeFormRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;

@AllArgsConstructor
@Service
@Validated
public class ImageService {

    private final BaseService baseService;
    private final BranchRepository branchRepository;
    private ImageRepository imageRepository;
    private MakeFormRepository makeFormRepository;
    private UserProfileRepository userRepository;
    private ImageMapper imageMapper;

    public List<ImageReturnDto> getImages(Long makeId) {
        try{
            System.out.println("getImages");
            MakeForm makeForm = makeFormRepository.findById(makeId)
                    .orElseThrow(() -> new RuntimeException("Maker not found"));
            List<Image> listOfImages = makeForm.getImages();
            if(listOfImages.isEmpty()){
                throw new RuntimeException("Maker not found");
            }
            System.out.println(listOfImages);
            return listOfImages.stream()
                            .map((image)->imageMapper.toImageDto(image))
                            .toList();

        }
        catch (Exception e){
            throw e;
        }
    }

    public String deleteImage(Long id) {
            Image image = imageRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Image not found"));
            imageRepository.delete(image);
            File file=new File(image.getImageName());
            file.delete();

            return "Image Deleted successfully";
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

            //check if the file sent is an image

            //getting the file
            String fileString = imageDto.getFile();
            String fileType=fileString.split(",")[0].split("/")[1].split(";")[0];
            String encodedBit=fileString.split(",")[1];
            byte[] fileByte=Base64.getDecoder().decode(encodedBit);

            System.out.println(fileType);
            //check, if really an image
            if(fileType.equals("png")||fileType.equals("jpg")||fileType.equals("jpeg")||fileType.equals("webm")){
                throw new RuntimeException("file type not supported");
            }
            //creating the path's parent path
            String filePath = Paths.get("C:", "Users", "hp", "Desktop", "hijra_kyc", "upload", branchName, today.toString(), cif, makeForm.getCustomerAccount()).toString();
            File uploadDir = new File(filePath);
            //check existence and create if path doesn't exist
            if (!uploadDir.exists()) {
                var success = uploadDir.mkdirs();
            }

            //the full path which includes the file to be created
            String fileName = Paths.get(filePath, now.toString().replace(":", "-").replace(".", "-") + "__" + image.getImageDescription() + today + cif + "."+fileType).toString();
            File fileToBeUploaded = new File(fileName);


//          creates the file in a lower storage and detail
            Thumbnails.of(new ByteArrayInputStream(fileByte))
                    .size(600, 400)
                    .outputQuality(0.85)
                    .toFile(fileToBeUploaded);
//          could use Files.write(Path, bytes), were bytes is the decoded image,
//          and the the Path is of type path representing the path(Path.get("..."))

            image.setImageName(fileName);
            imageRepository.save(image);
//            baseService.success("Image created successfully");
        }
        catch (Exception e){
//            baseService.error(e.getMessage());
//            imageRepository.deleteAllById();
            System.out.println(e.getMessage());
        }
    }


    public String editDescription(String description, Long id) {
            Image image = imageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            image.setImageDescription(description);
            imageRepository.save(image);
            return "Image Edited successfully";
    }

    public String createImages(@Valid List<ImageDto> imageListDto, Long makeId) {
            for (ImageDto image : imageListDto) {
                createImage(image, makeId);
            }
            return "Images created successfully";

    }
}
