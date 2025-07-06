package com.example.hijra_kyc.service;
import com.example.hijra_kyc.KycUserProfile;
import com.example.hijra_kyc.dto.MakeFormDto;
import com.example.hijra_kyc.mapper.MakeFormMapper;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.MakeFormRepository;
import com.example.hijra_kyc.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
public class MakeFormService {

    private final MakeFormRepository makeFormRepository;
    private final MakeFormMapper makerFormMapper;
    private final BaseService baseService;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;

    public Base<?> saveForm(MakeFormDto makeFormDto) {
        try{
            MakeForm makeForm= makerFormMapper.mapToMakeForm(makeFormDto);
            KycUserProfile maker=userRepository.findById(makeFormDto.getMakerId().longValue())
                    .orElseThrow(()->new RuntimeException("no maker with this id"));
            makeForm.setMaker(maker);
            Branch branchId=branchRepository.findById(maker.getBranch().getBranch_id())
                    .orElseThrow(()->new RuntimeException("no branch with this id"));
            makeForm.setBranchId(branchId);
            var createMakeForm=makeFormRepository.save(makeForm);
            return baseService.success(createMakeForm);
        }
        catch(Exception e){
            System.out.println("In the catch"+e);
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
            return baseService.listSuccess(makersForm);
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
            return baseService.listSuccess(makersForm);
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
            return baseService.success(makeForm1);
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
                return baseService.success("Make is now Accepted"+"\n"+makeForm1);
            }
            else if (statusNumber==2) {
                return baseService.success("Make is now Rejected"+"\n"+makeForm1);
            }
            else {
                return baseService.success("Make is now Pending"+"\n"+makeForm1);
            }
        }
        catch (Exception e){
            return baseService.error(e.getMessage());
        }
    }
}
