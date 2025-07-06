package com.example.hijra_kyc.service;


import com.example.hijra_kyc.KycUserProfile;
import com.example.hijra_kyc.dto.ImageDto;
import com.example.hijra_kyc.dto.ImageReturnDto;
import com.example.hijra_kyc.mapper.ImageMapper;
import com.example.hijra_kyc.mapper.ImageOutMapper;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ImageService {

    private final BaseService baseService;
    private ImageRepository imageRepository;
    private MakeFormRepository makeFormRepository;
    private UserRepository userRepository;
    private ImageMapper imageMapper;
    private ImageOutMapper imageOutMapper;

    public BaseList<?> getImages(Long makeId) {
        try{
            System.out.println("getImages");
            MakeForm makeForm = makeFormRepository.findById(makeId.intValue())
                    .orElseThrow(() -> new RuntimeException("Maker not found"));
            List<Image> listOfImages = makeForm.getImages();
            if(listOfImages.isEmpty()){
                return baseService.listError("No images found");
            }
            System.out.println(listOfImages);
            return baseService.listSuccess(
                    listOfImages.stream()
                            .map((image)->imageOutMapper.toImageDto(image))
                            .collect(Collectors.toList())
            );
        }
        catch (Exception e){
            return baseService.listError(e.getMessage());
        }
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

    public Base<?> createImage(MultipartFile imageFile, ImageDto imageDto) {
        try{
            var image = imageMapper.mapImageDtoToImage(imageDto);
            MakeForm makeForm=makeFormRepository.findById(imageDto.getMakeId())
                    .orElseThrow(()->new RuntimeException("can't find form-makeId"));
            image.setImageMake(makeForm);
            LocalDate today = LocalDate.now();
            String cif=makeForm.getCif();

            //uploading the file
            KycUserProfile maker=userRepository.findById(image.getImageMake().getMaker().getId())
                    .orElseThrow(()->new RuntimeException("Can't find maker-Id"));
            String makerName=maker.getFirstName();

            String filePath="C:/Users/hp/Desktop/hijra_kyc/upload/"+makerName+"/"+today+"/"+cif+"/"+makeForm.getCustomerAccount()+"/";
            File uploadDir = new File(filePath);

            if (!uploadDir.exists()) {
                var success = uploadDir.mkdirs();
//                if (!success) {
//                    return baseService.error("Can't create directory");
//                }
            }
            String fileName=filePath+LocalTime.now().toString().replace(":","-").replace(".","-")+"__"+imageFile.getOriginalFilename();
            File fileToBeUploaded=new File(fileName);

            if(!fileToBeUploaded.getParentFile().exists()){
                fileToBeUploaded.getParentFile().mkdirs();
            }
            imageFile.transferTo(fileToBeUploaded);

            image.setImageName(fileName);
            return baseService.success(imageRepository.save(image));
        }
        catch(Exception e){
            return baseService.error(e.getMessage());
        }
    }
}
