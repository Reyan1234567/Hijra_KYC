package com.example.hijra_kyc.controller;

import org.springframework.http.HttpStatus;
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

import com.example.hijra_kyc.dto.MakeFormDto;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.service.BaseService;
import com.example.hijra_kyc.service.MakeFormService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("makeForm")
@RequiredArgsConstructor
public class MakeFormController {
    private final MakeFormService makeFormService;
    private final BaseService baseService;

//    @PostMapping
//    public ResponseEntity<?> save(@Valid @RequestBody MakeFormDto makeFormDto) {
//        Base<?> newMakeForm= makeFormService.saveForm(makeFormDto);
//        return baseService.rest(newMakeForm);
//    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam("makerId") Long makerId) {
        BaseList<?> makes= makeFormService.getAll(makerId);
        return baseService.rest(makes);
    }

    @DeleteMapping("deleteForm/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Base<?> makeDelete=makeFormService.delete(id);
        return baseService.rest(makeDelete);
    }

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<?> statusChange(@PathVariable Long id, @RequestParam("status") int statusNumber) {
        Base<?> approve=makeFormService.changeStatus(id, statusNumber);
        return baseService.rest(approve);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAll(@RequestParam("makerId") Long makerId, @RequestParam("status") Long status){
        BaseList<?> allMakes=makeFormService.get(makerId, status);
        return baseService.rest(allMakes);
    }

    @PostMapping("/status")
    public ResponseEntity<?> updateAssign(@RequestParam("id") Long id, @RequestParam("hoId") Long hoId){
        Base<?> status=makeFormService.updateAssignTime(id, hoId);
        return baseService.rest(status);
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
