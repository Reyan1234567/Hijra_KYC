package com.example.hijra_kyc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.CheckerReportDto;
import com.example.hijra_kyc.dto.DetailReportDto;
import com.example.hijra_kyc.dto.SummaryReportDto;
import com.example.hijra_kyc.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

   public List<DetailReportDto> getDetailReport(Integer status, Long branchId,
                                             LocalDateTime fromDate, LocalDateTime toDate) {
    return reportRepository.getDetailReport(status, branchId, fromDate, toDate)
            .stream()
            .map(r -> new DetailReportDto(
                    ((Number) r[0]).longValue(),
                    (String) r[1],
                    (String) r[2],
                    (String) r[3],
                    (String) r[4],
                    (String) r[5],
                    (String) r[6],
                    (String) r[7],
                    (r[8] != null) ? ((java.sql.Timestamp) r[8]).toLocalDateTime() : null,
                    (String) r[9]
            ))
            .collect(Collectors.toList());
}
   public List<SummaryReportDto> getSummaryReport(Long branchId, 
                                                 LocalDateTime fromDate, 
                                                 LocalDateTime toDate) {
        return reportRepository.getSummaryReport(branchId, fromDate, toDate)
                .stream()
                .map(r -> new SummaryReportDto(
                        (String) r[0],         // branchName
                        ((Number) r[1]).longValue(), // totalRecords
                        ((Number) r[2]).longValue(), // pendingCount
                        ((Number) r[3]).longValue(), // approvedCount
                        ((Number) r[4]).longValue(), // rejectedCount
                        ((Number) r[5]).longValue()  // savedCount
                ))
                .collect(Collectors.toList());
    }
    public List<CheckerReportDto> getCheckerReport(LocalDateTime fromDate, LocalDateTime toDate) {
        return reportRepository.getCheckerReport(fromDate, toDate)
                .stream()
                .map(r -> new CheckerReportDto(
                        (String) r[0],
                        ((Number) r[1]).longValue(),
                        ((Number) r[2]).longValue(),
                        ((Number) r[3]).longValue(),
                        ((Number) r[4]).longValue()
                ))
                .collect(Collectors.toList());
    }
}