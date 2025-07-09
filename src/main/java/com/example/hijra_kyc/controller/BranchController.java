package com.example.hijra_kyc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.dto.BranchInDto;
import com.example.hijra_kyc.dto.BranchOutDto;
import com.example.hijra_kyc.mapper.BranchMapper;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.service.BranchService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/branches")
@Validated
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;
    private BranchMapper mapper;

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
    @GetMapping("/search-branch/{id}")
    public ResponseEntity<BranchOutDto> getBranchById(@PathVariable Long id) {
        Branch branch = branchService.searchBranchById(id);
        BranchOutDto dto = mapper.toDto(branch);
        return ResponseEntity.ok(dto);
    }
    
}
