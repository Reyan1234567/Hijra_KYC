package com.example.hijra_kyc.service;
import com.example.hijra_kyc.KycUserProfile;
import com.example.hijra_kyc.dto.ImageDto;
import com.example.hijra_kyc.dto.MakeFormDto;
import com.example.hijra_kyc.mapper.MakeFormMapper;
import com.example.hijra_kyc.mapper.MakeFormOutMapper;
import com.example.hijra_kyc.model.*;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.ImageRepository;
import com.example.hijra_kyc.repository.MakeFormRepository;
import com.example.hijra_kyc.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MakeFormService {

    private final MakeFormRepository makeFormRepository;
    private final ImageRepository imageRepository;
    private final MakeFormMapper makerFormMapper;
    private final BaseService baseService;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final MakeFormOutMapper makeFormOutMapper;
    private final ImageService imageService;

    public Base<?> saveForm(MakeFormDto makeFormDto, MultipartFile[] images) {
        try{
            MakeForm makeForm= makerFormMapper.mapToMakeForm(makeFormDto);
            KycUserProfile maker=userRepository.findById(makeFormDto.getMakerId().longValue())
                    .orElseThrow(()->new RuntimeException("no maker with this id"));
            makeForm.setMaker(maker);
            Branch branchId=branchRepository.findById(maker.getBranch().getBranch_id())
                    .orElseThrow(()->new RuntimeException("no branch with this id"));
            makeForm.setBranchId(branchId);
            var createMakeForm=makeFormRepository.save(makeForm);
            ArrayList<Image> imageList= new ArrayList<Image>();
            for(int i=0;i<images.length;i++){
                ImageDto imageDto=new ImageDto();
                imageDto.setDescription(makeFormDto.getDescriptions()[i]);
                imageDto.setMakeId(makeForm.getId());
                Image image=imageService.createImage(images[i],imageDto);
                imageList.add(image);
            }
            createMakeForm.setImages(imageList);
            return baseService.success(makeFormOutMapper.makeFormOutMapper(createMakeForm));
        }
        catch(Exception e){
            System.out.println("In the catch "+e);
            return baseService.error(e.getMessage());
        }
    }

    public BaseList<?> getAll(Long makerId) {
        try{
            System.out.println(makerId);
            List<MakeForm> makersForm = makeFormRepository.findByMaker(makerId);
            if(makersForm==null||makersForm.isEmpty()){
                return baseService.listError("No list items found");
            }
            return baseService.listSuccess(makersForm.stream()
                    .map(makeFormOutMapper::makeFormOutMapper)
                    .toList());
        }
        catch(Exception e){
            return baseService.listError(e.getMessage());
        }
    }

    public BaseList<?> get(Long makerId, Long status) {
        try{
            if(status<0||status>3){
                return baseService.listError("invalid status");
            }
            List<MakeForm> makersForm = makeFormRepository.findByMakerAndStatus(makerId, status);
            if(makersForm==null||makersForm.isEmpty()){
                return baseService.listError("No list items found");
            }
            return baseService.listSuccess(makersForm
                    .stream()
                    .map(makeFormOutMapper::makeFormOutMapper).toList());
        }
        catch(Exception e){
            return baseService.listError(e.getMessage());
        }
    }

    public Base<?> delete(Long id) {
        try{
            MakeForm makeForm1 = makeFormRepository.findById(id.intValue())
                    .orElseThrow(() -> new RuntimeException("Make not found"));
            makeFormRepository.delete(makeForm1);
            List<Image> images=makeForm1.getImages();
            if(!images.isEmpty()){
                images.forEach(image -> {imageService.deleteImage(image.getId().longValue());});
            }
            return baseService.success("Make is now deleted");
        }
        catch (Exception e){
            return baseService.error(e.getMessage());
        }
    }

    public Base<?> updateAssignTime(Long id, Long hoId){
        try{
            MakeForm makeForm1 = makeFormRepository.findById(id.intValue())
                    .orElseThrow(() -> new RuntimeException("Make not found"));
            makeForm1.setHoAssignTime(Instant.now());
            KycUserProfile ho=userRepository.findById(hoId)
                    .orElseThrow(()->new RuntimeException("HO not found"));
            makeForm1.setHo(ho);
            makeFormRepository.save(makeForm1);
            return baseService.success(makeFormOutMapper.makeFormOutMapper(makeForm1));
        } catch (Exception e) {
            return baseService.error(e.getMessage());
        }
    }



    public Base<?> changeStatus(Long makeId, int statusNumber) {
        try{
            if(statusNumber<0||statusNumber>3){
                return baseService.error("invalid status");
            }
            System.out.println(makeId.intValue());
            MakeForm makeForm1=makeFormRepository.findById(makeId.intValue())
                    .orElseThrow(() -> new EntityNotFoundException("Make not found"));
            makeForm1.setStatus(statusNumber);
            makeForm1.setHoActionTime(Instant.now());
            makeFormRepository.save(makeForm1);
            if(statusNumber==1){
                return baseService.success("Make is now Accepted"+"\n"+makeFormOutMapper.makeFormOutMapper(makeForm1));
            }
            else if (statusNumber==2) {
                return baseService.success("Make is now Rejected"+"\n"+makeFormOutMapper.makeFormOutMapper(makeForm1));
            }
            else {
                return baseService.success("Make is now Pending"+"\n"+makeFormOutMapper.makeFormOutMapper(makeForm1));
            }
        }
        catch (Exception e){
            return baseService.error(e.getMessage());
        }
    }
}


