package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.MakeFormOutDto;
import com.example.hijra_kyc.service.DistributorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.hijra_kyc.dto.MakeFormDto;
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
    public ResponseEntity<?> save(@Valid @RequestBody MakeFormDto makeFormDto) {
        Integer Id=makeFormService.saveForm(makeFormDto);
        return ResponseEntity.ok(Id);
    }

    @GetMapping("/distribute")
    public ResponseEntity<?> distribute(){
        String distribute=distributorService.Assign();
        return ResponseEntity.ok(distribute);
    }
}
