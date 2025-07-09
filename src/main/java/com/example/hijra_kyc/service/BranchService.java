package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.BranchInDto;
import com.example.hijra_kyc.dto.BranchOutDto;
import com.example.hijra_kyc.mapper.BranchMapper;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.repository.BranchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    private final BranchMapper mapper;

    public BranchOutDto createBranch(BranchInDto dto){
        Branch branch = mapper.toEntity(dto);
        Branch savedBranch = branchRepository.save(branch);
        return mapper.toDto(savedBranch);
    }
    public List<BranchOutDto> getAllBranches(){
        return branchRepository.findAll().stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }
    public Branch searchBranchById(Long id){
        return branchRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }
}
