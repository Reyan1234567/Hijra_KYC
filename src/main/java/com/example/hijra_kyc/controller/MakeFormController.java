package com.example.hijra_kyc.controller;


import com.example.hijra_kyc.dto.BackReasonDto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonDto.BackReasonOutDto;
import com.example.hijra_kyc.dto.FormDto.MakeFormDisplayDto;
import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.service.DistributorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hijra_kyc.dto.FormDto.MakeFormDto;
import com.example.hijra_kyc.dto.FormDto.MakeFormOutDto;
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
        List<MakeFormDisplayDto> makes= makeFormService.getAll(makerId);
        return ResponseEntity.ok(makes);
    }

    @GetMapping("/manager")
    public ResponseEntity<?> getAll(){
        List<MakeFormDisplayDto> makes=makeFormService.getManager();
        return ResponseEntity.ok(makes);
    }
    @GetMapping("/getHo")
    public ResponseEntity<?> getWithHoUserId(@RequestParam("hoUserId") Long hoUserId) {
        List<MakeFormDisplayDto> makes= makeFormService.getWithHoUserId(hoUserId);
        return ResponseEntity.ok(makes);
    }

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<?> statusChange(@PathVariable Long id, @RequestParam("status") int statusNumber) {
        MakeFormDisplayDto approve=makeFormService.changeStatus(id, statusNumber);
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

    @PatchMapping("/toDrafts/{id}")
    public ResponseEntity<?> toDraft(@PathVariable("id") Long id){
        MakeFormDisplayDto makeForm=makeFormService.toDraft(id);
        return ResponseEntity.ok(makeForm);
    }
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MakeFormDto makeFormDto) {
        MakeFormOutDto Id=makeFormService.saveForm(makeFormDto);
        return ResponseEntity.ok(Id);
    }

    @PatchMapping("/assignChecker")
    public ResponseEntity<?> AssignToChecker(@RequestParam("make")Long makeId, @RequestParam("checker") Long checkerId){
        MakeFormDisplayDto make=makeFormService.assignToChecker(makeId, checkerId);
        return ResponseEntity.ok(make);
    }

    @GetMapping("/distribute")
    public ResponseEntity<?> distribute(){
        String distribute=distributorService.Assign();
        return ResponseEntity.ok(distribute);
    }

    
    @GetMapping("/assignToNight")
    public ResponseEntity<?> assignToNight(){
        String makes=distributorService.AssignNight();
        return ResponseEntity.ok(makes);
    }

    @PatchMapping("/send-ToHo/{id}")
    public ResponseEntity<?> sendToHo(@PathVariable Long id){
        MakeFormDisplayDto response=makeFormService.sendToHo(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reject-request")
    public ResponseEntity<?> rejectRequest(@RequestBody BackReasonInDto comment){
        BackReasonOutDto response=makeFormService.rejectResponse(comment);
        return ResponseEntity.ok(response);
    }
}
