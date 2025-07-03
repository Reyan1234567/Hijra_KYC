package com.example.hijra_kyc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.service.BranchService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.hijra_kyc.dto.BranchInDto;
import com.example.hijra_kyc.dto.BranchOutDto;


@RestController
@RequestMapping("/api/branches")
@Validated
public class BranchController {
    private final BranchService branchService;
    public BranchController(BranchService branchService){
        this.branchService = branchService;
    }
    @PostMapping("/post-branch")
    public ResponseEntity<BranchOutDto> postBranch(@RequestBody BranchInDto dto) {
        BranchOutDto result = branchService.createBranch(dto);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/get-all-branches")
    public ResponseEntity<List<BranchOutDto>> getAllBranches(){
        List<BranchOutDto> result = branchService.getAllBranches();
        return ResponseEntity.ok(result);
    
    }
    
}
