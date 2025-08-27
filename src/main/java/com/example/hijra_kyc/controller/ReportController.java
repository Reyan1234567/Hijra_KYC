package com.example.hijra_kyc.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.dto.CheckerReportDto;
import com.example.hijra_kyc.dto.DetailReportDto;
import com.example.hijra_kyc.dto.SummaryReportDto;
import com.example.hijra_kyc.service.ReportService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

   @GetMapping("/detail-report")
public List<DetailReportDto> getDetailReport(
    @RequestParam(required = false) Integer status,
    @RequestParam(required = false) Long branchId,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
    
    LocalDateTime startDateTime = fromDate != null ? fromDate.atStartOfDay() : null;
    LocalDateTime endDateTime = toDate != null ? toDate.atTime(LocalTime.MAX) : null;
    
    return reportService.getDetailReport(status, branchId, startDateTime, endDateTime);
}
    @GetMapping("/summary-report")
    public List<SummaryReportDto> getSummaryReport(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        
        LocalDateTime startDateTime = fromDate != null ? fromDate.atStartOfDay() : null;
        LocalDateTime endDateTime = toDate != null ? toDate.atTime(LocalTime.MAX) : null;
        
        Long finalBranchId = (branchId != null && branchId == 0) ? null : branchId;
        
        return reportService.getSummaryReport(finalBranchId, startDateTime, endDateTime);
    }
    @GetMapping("/checker-report")
    public List<CheckerReportDto> getCheckerReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        LocalDateTime startDateTime = fromDate != null ? fromDate.atStartOfDay() : null;
    LocalDateTime endDateTime = toDate != null ? toDate.atTime(LocalTime.MAX) : null;
        return reportService.getCheckerReport(startDateTime, endDateTime);
    }
}