//package com.example.hijra_kyc.service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//
//import com.example.hijra_kyc.dto.BranchDto.BranchInDto;
//import com.example.hijra_kyc.dto.BranchDto.BranchOutDto;
//import com.example.hijra_kyc.mapper.BranchMapper;
//import com.example.hijra_kyc.model.Branch;
//import com.example.hijra_kyc.model.District;
//import com.example.hijra_kyc.repository.BranchRepository;
//import com.example.hijra_kyc.repository.DistrictRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class BranchService {
//    private final BranchRepository branchRepository;
//    private final BranchMapper mapper;
//    private final DistrictRepository districtRepository;
//    public BranchOutDto createBranch(BranchInDto dto){
//        District district  = districtRepository.findById(dto.getDistrictId())
//        .orElseThrow(() -> new RuntimeException("Diistrict is not found"));
//        Branch branch = mapper.toEntity(dto,district);
//        Branch savedBranch = branchRepository.save(branch);
//        return mapper.toDto(savedBranch);
//    }
//    public List<BranchOutDto> getAllBranches(){
//        return branchRepository.findAll().stream()
//            .map(mapper::toDto)
//            .collect(Collectors.toList());
//    }
//    public BranchOutDto searchBranchById(Long id){
//        Branch branch=branchRepository.findById(id)
//            .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
//        return mapper.toDto(branch);
//    }
//
//
//    public BranchOutDto updateBranch(Long branchId, BranchInDto dto) {
//        Branch existingBranch = branchRepository.findById(branchId)
//                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + branchId));
//
//        // Update only the fields you want (name, branchCode)
//        existingBranch.setName(dto.getName());
//        existingBranch.setBranchCode(dto.getBranchCode());
//
//        Branch updatedBranch = branchRepository.save(existingBranch);
//        return mapper.toDto(updatedBranch);
//    }
//}
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

    public BranchOutDto createBranch(BranchInDto dto) {
        District district = districtRepository.findById(dto.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District is not found"));
        Branch branch = mapper.toEntity(dto, district);
        Branch savedBranch = branchRepository.save(branch);
        return mapper.toDto(savedBranch);
    }

    public List<BranchOutDto> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public BranchOutDto searchBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
        return mapper.toDto(branch);
    }

    public BranchOutDto updateBranch(Long branchId, BranchInDto dto) {
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + branchId));

        existingBranch.setName(dto.getName());
        existingBranch.setBranchCode(dto.getBranchCode());
        existingBranch.setStatus(dto.getStatus());   // NEW
        existingBranch.setPhone(dto.getPhone());     // NEW

        // if you want to allow switching district
        if (dto.getDistrictId() != null) {
            District district = districtRepository.findById(dto.getDistrictId())
                    .orElseThrow(() -> new RuntimeException("District not found"));
            existingBranch.setId(district);
        }

        Branch updatedBranch = branchRepository.save(existingBranch);
        return mapper.toDto(updatedBranch);
    }
}
