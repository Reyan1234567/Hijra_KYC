package com.example.hijra_kyc.controller;
import java.util.List;

import com.example.hijra_kyc.dto.UserProfileDto.UserProfileOutDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.hijra_kyc.dto.BranchDto.BranchInDto;
import com.example.hijra_kyc.dto.BranchDto.BranchOutDto;
import com.example.hijra_kyc.mapper.BranchMapper;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.service.BranchService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/branches")
@Validated
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;
    private BranchMapper mapper;
    private BranchOutDto dto;

    @PostMapping("/post-branch")
    public ResponseEntity<BranchOutDto> postBranch(@RequestBody BranchInDto dto) {
        BranchOutDto result = branchService.createBranch(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping()
    public ResponseEntity<List<BranchOutDto>> getAllBranches(){
        List<BranchOutDto> result = branchService.getAllBranches();
        return ResponseEntity.ok(result);

    }


    @GetMapping("/get-all-branches")
    public ResponseEntity<List<BranchOutDto>> getAllBranchess(){
        List<BranchOutDto> result = branchService.getAllBranches();
        return ResponseEntity.ok(result);

    }

    @GetMapping("/search-branch/{id}")
    public ResponseEntity<BranchOutDto> getBranchById(@PathVariable Long id) {
        BranchOutDto branch = branchService.searchbranchById(id);
        return ResponseEntity.ok(branch);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BranchOutDto> updateBranch(
            @PathVariable("id") Long id,
            @RequestBody BranchInDto updatedDto) {
        BranchOutDto updatedBranch = branchService.updateBranch(id, updatedDto);
        return ResponseEntity.ok(updatedBranch);
    }




}

