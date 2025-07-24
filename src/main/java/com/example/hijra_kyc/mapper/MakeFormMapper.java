package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.FormDto.MakeFormDisplayDto;
import com.example.hijra_kyc.dto.FormDto.MakeFormDto;
import com.example.hijra_kyc.dto.FormDto.MakeFormOutDto;
import com.example.hijra_kyc.dto.Imagedto.ImageDto;
import com.example.hijra_kyc.dto.Imagedto.ImageReturnDto;
import com.example.hijra_kyc.model.MakeForm;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class MakeFormMapper {
    private final ImageMapper imageMapper;

    public MakeForm mapToMakeForm(MakeFormDto makeFormDto) {
        return MakeForm.builder()
                .cif(makeFormDto.getCif())
                .customerAccount(makeFormDto.getCustomerAccount())
                .customerPhone(makeFormDto.getCustomerPhone())
                .customerName(makeFormDto.getCustomerName())
                .makeTime(Instant.now())
                .build();
    }

    public MakeFormOutDto makeFormOutMapper(MakeForm makeForm){
        return MakeFormOutDto.builder()
                .id(makeForm.getId())
                .makerId(makeForm.getMaker().getId())
                .makerName(makeForm.getMaker().getFirstName()+" "+makeForm.getMaker().getLastName())
                .hoId(makeForm.getHo()!=null?makeForm.getHo().getId():0)
                .hoName(makeForm.getHo().getFirstName()+" "+makeForm.getHo().getLastName())
                .cif(makeForm.getCif())
                .customerAccount(makeForm.getCustomerAccount())
                .customerPhone(makeForm.getCustomerPhone())
                .customerName(makeForm.getCustomerName())
                .status(makeForm.getStatus())
                .image(makeForm.getImages().stream().map(imageMapper::toImageDto).toList())
                .build();
    }

    public MakeFormDisplayDto makeFormDisplayDto(MakeForm makeForm){
        String checkerFirstName="";
        String checkerLastName="";
        List<ImageReturnDto> imageUrl=Collections.emptyList();
        Long checkerId=0L;
        if(makeForm.getHo()!=null){
            checkerFirstName=makeForm.getHo().getFirstName();
            checkerLastName=makeForm.getHo().getLastName();
            checkerId=makeForm.getHo().getId();
        }
        if(makeForm.getImages()!=null){
            imageUrl=makeForm.getImages().stream().map(imageMapper::toImageDto).toList();
        }
        Instant checkedAt=makeForm.getHoActionTime()==null?Instant.now().plus(1, ChronoUnit.DAYS ):makeForm.getHoActionTime();
        Instant assignedAt=makeForm.getHoAssignTime()==null?Instant.now().plus(1, ChronoUnit.DAYS):makeForm.getHoAssignTime();
        return MakeFormDisplayDto.builder()
                .id(makeForm.getId())
                .cif(makeForm.getCif())
                .customerAccount(makeForm.getCustomerAccount())
                .makerId(makeForm.getMaker().getId())
                .makeId(makeForm.getId())
                .makerName(makeForm.getMaker().getFirstName())
                .customerPhone(makeForm.getCustomerPhone())
                .customerName(makeForm.getCustomerName())
                .images(imageUrl)
                .madeAt(makeForm.getMakeTime())
                .checkedAt(makeForm.getHoActionTime())
                .hoName(checkerFirstName+" "+checkerLastName)
                .hoId(checkerId)
                .status(makeForm.getStatus())
                .build();
    }
}
