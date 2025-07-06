package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.MakeFormDto;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.service.BaseService;
import com.example.hijra_kyc.service.MakeFormService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("makeForm")
@RequiredArgsConstructor
public class MakeFormController {
    private final MakeFormService makeFormService;
    private final BaseService baseService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MakeFormDto makeFormDto) {
        Base<?> newMakeForm= makeFormService.saveForm(makeFormDto);
        return baseService.rest(newMakeForm);
    }

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

//    @PatchMapping("/updateForm/{id}")
//    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MakeFormDto makeFormDto){
//        Base<?> editForm=makeFormService.patch(id, makeFormDto);
//        return baseService.rest(editForm);
//    }
}
