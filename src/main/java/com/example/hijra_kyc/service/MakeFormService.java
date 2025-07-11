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


    public Integer saveForm(MakeFormDto makeFormDto) {
            MakeForm makeForm=makerFormMapper.mapToMakeForm(makeFormDto);
            UserProfile maker=userRepository.findById(makeFormDto.getMakerId())
                    .orElseThrow(()->new EntityNotFoundException("User not found"));
            makeForm.setMaker(maker);
            MakeForm foundMakeForm=makeFormRepository.findMakeFormByAccount(makeForm.getCustomerAccount());
            if(foundMakeForm==null){
                foundMakeForm=makeFormRepository.save(makeForm);
            }
            return foundMakeForm.getId();
    }

    public List<MakeFormOutDto> getAll(Long makerId) {
        System.out.println(makerId);
        List<MakeForm> makersForm = makeFormRepository.findByMaker(makerId);
        if(makersForm==null||makersForm.isEmpty()){
            throw new EntityNotFoundException("Maker not found");
        }
        return makersForm.stream()
                .map(makerFormMapper::makeFormOutMapper)
                .toList();
    }

    public List<MakeFormOutDto> get(Long makerId, Long status) {
            if(status<0||status>3){
                throw new IllegalArgumentException("invalid status");
            }
            List<MakeForm> makersForm = makeFormRepository.findByMakerAndStatus(makerId, status);
            if(makersForm==null||makersForm.isEmpty()){
                throw new EntityNotFoundException("No list items found");
            }
            return makersForm
                    .stream()
                    .map(makerFormMapper::makeFormOutMapper).toList();
    }

    public Base<?> delete(Long id) {
        try{
            MakeForm makeForm1 = makeFormRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Make not found"));
            makeFormRepository.delete(makeForm1);
            return "Make is now deleted";
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
            return makerFormMapper.makeFormOutMapper(makeForm1);
    }



    public String changeStatus(Long makeId, int statusNumber) {
            if(statusNumber<0||statusNumber>3){
                throw new IllegalArgumentException("invalid status");
            }
            System.out.println(makeId.intValue());
            MakeForm makeForm1=makeFormRepository.findById(makeId)
                    .orElseThrow(() -> new EntityNotFoundException("Make not found"));
            makeForm1.setStatus(statusNumber);
            makeForm1.setHoActionTime(Instant.now());
            makeFormRepository.save(makeForm1);
            if(statusNumber==1){
                return "Make is now Accepted";
            }
            else if (statusNumber==2) {
                return "Make is now Rejected";
            }
            else {
                return "Make is now Pending";
            }
    }
}


