package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.ImageDto;
import com.example.hijra_kyc.dto.ImageReturnDto;
import com.example.hijra_kyc.mapper.ImageMapper;
import com.example.hijra_kyc.model.*;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.ImageRepository;
import com.example.hijra_kyc.repository.MakeFormRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Service
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

    public Base<?> deleteImage(Long id) {
        try{
            Image image = imageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            imageRepository.delete(image);
            File file=new File(image.getImageName());
            file.delete();

            return baseService.success("Image Deleted successfully");
        }
        catch (Exception e){
            return baseService.error(e.getMessage());
        }
    }

    public Image createImage(MultipartFile imageFile, ImageDto imageDto) throws Exception{
        System.out.println("In the createImage");
        var image = imageMapper.mapImageDtoToImage(imageDto);
        System.out.println("image created "+image);
        MakeForm makeForm=makeFormRepository.findById(imageDto.getMakeId())
                .orElseThrow(()->new RuntimeException("can't find form-makeId"));
        image.setImageMake(makeForm);
        Branch branch=branchRepository.findById(image.getImageMake().getMaker().getBranch().getBranchId())
                .orElseThrow(()->new RuntimeException("branch not found"));

        image.setImageMake(makeForm);
        LocalDate today = LocalDate.now();
        LocalTime now=LocalTime.now();
        String branchName=branch.getName();
        String cif=makeForm.getCif();

        //uploading the file
        UserProfile maker=userRepository.findById(image.getImageMake().getMaker().getId())
                .orElseThrow(()->new RuntimeException("Can't find maker-Id"));
        String makerName=maker.getFirstName();

        //creating the path's parent path
        String filePath= Paths.get("C:","Users","hp","Desktop","hijra_kyc","upload",branchName,today.toString(),cif,makeForm.getCustomerAccount()).toString();
        File uploadDir = new File(filePath);
        System.out.println(filePath);
        //check existence and create if path doesn't exist
        if (!uploadDir.exists()) {
            var success = uploadDir.mkdirs();
        }

        //the full path which includes the file to be created
        String fileName=Paths.get(filePath,now.toString().replace(":","-").replace(".","-")+"__"+image.getImageDescription()+today+cif+getExtension(imageFile.getOriginalFilename())).toString();
        File fileToBeUploaded=new File(fileName);


//            creates the file in a lower storage and detail
        Thumbnails.of(imageFile.getInputStream())
                        .size(600, 400)
                        .outputQuality(0.85)
                        .toFile(fileToBeUploaded);

        image.setImageName(fileName);
        return imageRepository.save(image);
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }


    public Base<?> editDescription(String description, Long id) {
        try{
            Image image = imageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            image.setImageDescription(description);
            imageRepository.save(image);
            return baseService.success("Image Edited successfully");
        }
        catch (Exception e){
            return baseService.error(e.getMessage());
        }
    }
}
