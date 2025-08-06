package com.example.hijra_kyc.dto.FormDto;

import com.example.hijra_kyc.dto.Imagedto.ImageReturnDto;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class MakeFormDisplayDto {
    private Long id;
    private Long makerId;
    private Long makeId;
    private String makerName;
    private Instant madeAt;
    private Instant checkedAt;
    private Instant assignedAt;
    private Long hoId;
    private String hoName;
    private String cif;
    private String customerAccount;
    private String customerPhone;
    private String customerName;
    private List<ImageReturnDto> images;
    private int status;
    private int saved;
    private String backReason;
}
