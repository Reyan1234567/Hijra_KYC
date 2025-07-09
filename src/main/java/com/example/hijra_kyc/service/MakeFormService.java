package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.FormDto.MakeFormDto;
import com.example.hijra_kyc.dto.Imagedto.ImageDto;
import com.example.hijra_kyc.mapper.MakeFormMapper;
import com.example.hijra_kyc.model.*;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.ImageRepository;
import com.example.hijra_kyc.repository.MakeFormRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MakeFormService {

    private final MakeFormRepository makeFormRepository;
    private final ImageRepository imageRepository;
    private final MakeFormMapper makerFormMapper;
    private final BaseService baseService;
    private final UserProfileRepository userRepository;
    private final BranchRepository branchRepository;
    private final ImageService imageService;

    public Base<?> saveForm(MakeFormDto makeFormDto, MultipartFile[] images) {
        try{
            MakeForm makeForm= makerFormMapper.mapToMakeForm(makeFormDto);
            UserProfile maker=userRepository.findById(makeFormDto.getMakerId())
                    .orElseThrow(()->new RuntimeException("no maker with this id"));
            makeForm.setMaker(maker);
            Branch branchId=branchRepository.findById(maker.getBranch().getBranchId())
                    .orElseThrow(()->new RuntimeException("no branch with this id"));
            makeForm.setBranchId(branchId);
            var createMakeForm=makeFormRepository.save(makeForm);
            ArrayList<Image> imageList= new ArrayList<Image>();
            for(int i=0;i<images.length;i++){
                ImageDto imageDto=new ImageDto();
                imageDto.setDescription(makeFormDto.getDescriptions()[i]);
                imageDto.setMakeId(createMakeForm.getId());
                Image image=imageService.createImage(images[i],imageDto);
                imageList.add(image);
            }
            createMakeForm.setImages(imageList);
            return baseService.success(makerFormMapper.makeFormOutMapper(createMakeForm));
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
                    .map(makerFormMapper::makeFormOutMapper)
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
                    .map(makerFormMapper::makeFormOutMapper).toList());
        }
        catch(Exception e){
            return baseService.listError(e.getMessage());
        }
    }

    public Base<?> delete(Long id) {
        try{
            MakeForm makeForm1 = makeFormRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Make not found"));
            makeFormRepository.delete(makeForm1);
            return baseService.success("Make is now deleted");
        }
        catch (Exception e){
            return baseService.error(e.getMessage());
        }
    }

    public Base<?> updateAssignTime(Long id, Long hoId){
        try{
            MakeForm makeForm1 = makeFormRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Make not found"));
            makeForm1.setHoAssignTime(Instant.now());
            UserProfile ho=userRepository.findById(hoId)
                    .orElseThrow(()->new RuntimeException("HO not found"));
            makeForm1.setHo(ho);
            makeFormRepository.save(makeForm1);
            return baseService.success(makerFormMapper.makeFormOutMapper(makeForm1));
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
            MakeForm makeForm1=makeFormRepository.findById(makeId)
                    .orElseThrow(() -> new EntityNotFoundException("Make not found"));
            makeForm1.setStatus(statusNumber);
            makeForm1.setHoActionTime(Instant.now());
            makeFormRepository.save(makeForm1);
            if(statusNumber==1){
                return baseService.success("Make is now Accepted"+"\n"+makerFormMapper.makeFormOutMapper(makeForm1));
            }
            else if (statusNumber==2) {
                return baseService.success("Make is now Rejected"+"\n"+makerFormMapper.makeFormOutMapper(makeForm1));
            }
            else {
                return baseService.success("Make is now Pending"+"\n"+makerFormMapper.makeFormOutMapper(makeForm1));
            }
        }
        catch (Exception e){
            return baseService.error(e.getMessage());
        }
    }
}


