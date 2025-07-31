package com.example.hijra_kyc.service;


import com.example.hijra_kyc.dto.BackReasonDto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonDto.BackReasonOutDto;
import com.example.hijra_kyc.dto.FormDto.MakeFormDisplayDto;
import com.example.hijra_kyc.dto.FormDto.MakeFormDto;
import com.example.hijra_kyc.dto.FormDto.MakeFormOutDto;
import com.example.hijra_kyc.mapper.BackReasonMapper;
import com.example.hijra_kyc.mapper.MakeFormMapper;
import com.example.hijra_kyc.model.*;
import com.example.hijra_kyc.repository.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
public class MakeFormService {

    private final MakeFormRepository makeFormRepository;
    private final ImageRepository imageRepository;
    private final MakeFormMapper makerFormMapper;
    private final BackReasonRepository backReasonRepository;
    private final BackReasonMapper backReasonMapper;
    private final BaseService baseService;
    private final UserProfileRepository userRepository;
    private final BranchRepository branchRepository;
    private final ImageService imageService;

    public MakeFormOutDto saveForm(MakeFormDto makeFormDto) {
            MakeForm makeForm=makerFormMapper.mapToMakeForm(makeFormDto);
            UserProfile maker=userRepository.findById(makeFormDto.getMakerId())
                    .orElseThrow(()->new EntityNotFoundException("User not found"));
            makeForm.setMaker(maker);
            MakeForm foundMakeForm=makeFormRepository.findMakeFormByAccount(makeForm.getCustomerAccount());
            if(foundMakeForm==null){
                foundMakeForm=makeFormRepository.save(makeForm);
            }
            return makerFormMapper.makeFormOutMapper(foundMakeForm);
    }

    public List<MakeFormDisplayDto> getAll(Long makerId) {
        System.out.println(makerId);
        UserProfile maker = userRepository.findById(makerId).orElseThrow(()->new EntityNotFoundException("Maker not found"));

        List<MakeForm> makes=maker.getMakeFormList();
        System.out.println(makes.get(0).getMaker().getId());
        return makes.stream()
                .map(makerFormMapper::makeFormDisplayDto)
                .toList();
    }

    public List<MakeFormOutDto> get(Long makerId, Long status) {
            if(status<0||status>4){
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

    public String delete(Long id) {
            MakeForm makeForm1 = makeFormRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Make not found"));
            makeFormRepository.delete(makeForm1);
            return "Make is now deleted";
    }

    public MakeFormOutDto updateAssignTime(Long id, Long hoId){
            MakeForm makeForm1 = makeFormRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Make not found"));
            makeForm1.setHoAssignTime(Instant.now());
            UserProfile ho=userRepository.findById(hoId)
                    .orElseThrow(()->new RuntimeException("HO not found"));
            makeForm1.setHo(ho);
            makeFormRepository.save(makeForm1);
            return makerFormMapper.makeFormOutMapper(makeForm1);
    }



    public MakeFormDisplayDto changeStatus(Long makeId, int statusNumber) {
            if(statusNumber<0||statusNumber>4){
                throw new IllegalArgumentException("invalid status");
            }
            MakeForm makeForm1=makeFormRepository.findById(makeId)
                    .orElseThrow(() -> new EntityNotFoundException("Make not found"));
            makeForm1.setStatus(statusNumber);
            if(statusNumber==2||statusNumber==3){
                makeForm1.setHoActionTime(Instant.now());
            }
            makeFormRepository.save(makeForm1);
            return makerFormMapper.makeFormDisplayDto(makeForm1);
    }

    @Transactional
    public MakeFormDisplayDto sendToHo(Long id) {
        MakeForm makeForm=makeFormRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Make not found"));
        BackReason backReason=backReasonRepository.findByMakeId(id)!=null?backReasonRepository.findByMakeId(id):null;
        if(backReason!=null){
            backReason.setMakeId(null);
            backReasonRepository.save(backReason);
        }
        if(makeForm.getStatus()!=0){
            throw new RuntimeException("can't send a make with this status");
        }
        return changeStatus(id,1);
    }

    public List<MakeFormDisplayDto> getWithHoUserId(Long hoUserId) {
        System.out.println(hoUserId);
        userRepository.findById(hoUserId).orElseThrow(()->new RuntimeException("Make not found"));
        List<MakeForm> makeForm=makeFormRepository.getMakeFormByHoId(hoUserId);
        return makeForm.stream().map(makerFormMapper::makeFormDisplayDto).toList();
    }

    public BackReasonOutDto rejectResponse(BackReasonInDto inDto) {
        MakeForm makeForm1=makeFormRepository.findById(inDto.getMakeFormId()).orElseThrow(()->new RuntimeException("can't find makeForm1"));
        changeStatus(inDto.getMakeFormId(), 3);
        BackReason backReason=backReasonMapper.toEntity(inDto);
        backReason.setMakeId(makeForm1);
        return backReasonMapper.toDto(backReasonRepository.save(backReason));
    }

    public MakeFormDisplayDto toDraft(Long id) {
        MakeForm makeForm=makeFormRepository.findById(id).orElseThrow(()->new RuntimeException("Make not found"));
        makeForm.setHoActionTime(null);
        makeFormRepository.save(makeForm);
        changeStatus(id,0);
        return makerFormMapper.makeFormDisplayDto(makeForm);
    }

    public List<MakeFormDisplayDto> getManager() {
        List<MakeForm> makes=makeFormRepository.findAll();
        return makes.stream().map(makerFormMapper::makeFormDisplayDto).toList();
    }

    public MakeFormDisplayDto assignToChecker(Long makeId, Long checkerId) {
        MakeForm makeForm=makeFormRepository.findById(makeId).orElseThrow(()->new RuntimeException("Make not found"));
        UserProfile checker=userRepository.findById(checkerId).orElseThrow(()->new RuntimeException("User not found"));

        makeForm.setHo(checker);
        makeFormRepository.save(makeForm);
        return makerFormMapper.makeFormDisplayDto(makeForm);
    }
}


