package com.example.hijra_kyc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SummaryReportDto {
    private String getBranchName;
    private Long getTotal;
    private Long getApproved;
    private Long getPending;
    private Long getRejected;
    private Long getSaved;
}