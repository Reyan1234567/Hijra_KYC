package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.MakeFormOutDto;
import com.example.hijra_kyc.service.DistributorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.hijra_kyc.dto.FormDto.MakeFormDto;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.service.BaseService;
import com.example.hijra_kyc.service.MakeFormService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("makeForm")
@RequiredArgsConstructor
public class MakeFormController {
    private final MakeFormService makeFormService;
    private final BaseService baseService;
    private final DistributorService distributorService;


    @GetMapping
    public ResponseEntity<?> get(@RequestParam("makerId") Long makerId) {
        List<MakeFormOutDto> makes= makeFormService.getAll(makerId);
        return ResponseEntity.ok(makes);
    }

    @DeleteMapping("deleteForm/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        String makeDelete=makeFormService.delete(id);
        return ResponseEntity.ok(makeDelete);
    }

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<?> statusChange(@PathVariable Long id, @RequestParam("status") int statusNumber) {
        String approve=makeFormService.changeStatus(id, statusNumber);
        return ResponseEntity.ok(approve);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAll(@RequestParam("makerId") Long makerId, @RequestParam("status") Long status){
        List<MakeFormOutDto> allMakes=makeFormService.get(makerId, status);
        return ResponseEntity.ok(allMakes);
    }

    @PostMapping("/status")
    public ResponseEntity<?> updateAssign(@RequestParam("id") Long id, @RequestParam("hoId") Long hoId){
        MakeFormOutDto status=makeFormService.updateAssignTime(id, hoId);
        return ResponseEntity.ok(status);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestParam("makerId") Long makerId,@RequestParam("cif") String cif,@RequestParam("CA") String customerAccount,@RequestParam("CP") String customerPhone,@RequestParam("CN") String customerName,@RequestParam("descriptions") String[] descriptions,@RequestParam("image") MultipartFile[] images) {
        if(descriptions.length!=images.length){
            return new ResponseEntity<>("Images and descriptions don't match", HttpStatus.BAD_REQUEST);
        }
        try{
            MakeFormDto makeFormDto = new MakeFormDto(makerId, cif, customerAccount, customerPhone, customerName, descriptions);
            Base<?> newMakeForm= makeFormService.saveForm(makeFormDto, images);
            return baseService.rest(newMakeForm);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @PatchMapping("/updateForm/{id}")
//    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MakeFormDto makeFormDto){
//        Base<?> editForm=makeFormService.patch(id, makeFormDto);
//        return baseService.rest(editForm);
//    }
}
