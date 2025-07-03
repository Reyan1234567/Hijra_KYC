package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.BranchInDto;
import com.example.hijra_kyc.dto.BranchOutDto;
import com.example.hijra_kyc.mapper.BranchMapper;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.repository.BranchRepository;

@Service
public class BranchService {
    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository){
        this.branchRepository = branchRepository;
    }

    public BranchOutDto createBranch(BranchInDto dto){
        Branch branch = BranchMapper.toEntity(dto);
        Branch savedBranch = branchRepository.save(branch);
        return BranchMapper.toDto(savedBranch);
    }
    public List<BranchOutDto> getAllBranches(){
        return branchRepository.findAll().stream()
            .map(BranchMapper::toDto)
            .collect(Collectors.toList());
    }
}
