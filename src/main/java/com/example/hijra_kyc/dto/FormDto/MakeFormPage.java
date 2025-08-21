package com.example.hijra_kyc.dto.FormDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MakeFormPage {
    Long total;
    List<MakeFormDisplayDto> makes;
}
