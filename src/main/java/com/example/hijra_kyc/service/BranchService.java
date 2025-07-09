package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.BranchDto.BranchInDto;
import com.example.hijra_kyc.dto.BranchDto.BranchOutDto;
import com.example.hijra_kyc.mapper.BranchMapper;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.District;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.DistrictRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    private final BranchMapper mapper;
    private final DistrictRepository districtRepository;
    public BranchOutDto createBranch(BranchInDto dto){
        District district  = districtRepository.findById(dto.getDistrictCode())
        .orElseThrow(() -> new RuntimeException("Diistrict is not found"));
        Branch branch = mapper.toEntity(dto,district);
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
