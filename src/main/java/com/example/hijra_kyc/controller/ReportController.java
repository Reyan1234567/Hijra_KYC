package com.example.hijra_kyc.controller;


import com.example.hijra_kyc.dto.CheckerReportDto;
import com.example.hijra_kyc.dto.DetailReportDto;
import com.example.hijra_kyc.dto.SummaryReportDto;
import com.example.hijra_kyc.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/detail-report")
    public List<DetailReportDto> getDetailReport(@RequestParam(required = false) Integer status,
                                                 @RequestParam(required = false) Long branchId,
                                                 @RequestParam(required = false) LocalDateTime fromDate,
                                                 @RequestParam(required = false) LocalDateTime toDate) {
        return reportService.getDetailReport(status, branchId, fromDate, toDate);
    }

    @GetMapping("/summary-report")
    public List<SummaryReportDto> getSummaryReport() {
        return reportService.getSummaryReport();
    }

    @GetMapping("/checker-report")
    public List<CheckerReportDto> getCheckerReport() {
        return reportService.getCheckerReport();
    }
}