package com.example.hijra_kyc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class DetailReportDto {
    private Long getId;
    private String getCustomerName;
    private String getCustomerAccount;
    private String getCustomerPhone;
    private String getStatus;
    private String getBranchName;
    private String getUserName;
    private String getHoUserName;
    private LocalDateTime getUploadedOn;
    private String getComment;
}