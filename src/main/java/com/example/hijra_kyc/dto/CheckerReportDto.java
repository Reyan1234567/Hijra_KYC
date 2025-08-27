package com.example.hijra_kyc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckerReportDto {
    private String hoUserName;
    private Long pending;
    private Long approved;
    private Long rejected;
    private Long total;
}
