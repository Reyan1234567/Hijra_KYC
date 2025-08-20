package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.CheckerReportDto;
import com.example.hijra_kyc.dto.DetailReportDto;
import com.example.hijra_kyc.dto.SummaryReportDto;
import com.example.hijra_kyc.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<SummaryReportDto> getSummaryReport() {
        return reportRepository.getSummaryReport()
                .stream()
                .map(r -> new SummaryReportDto(
                        (String) r[0],
                        ((Number) r[1]).longValue(),
                        ((Number) r[2]).longValue(),
                        ((Number) r[3]).longValue(),
                        ((Number) r[4]).longValue(),
                        ((Number) r[5]).longValue()
                ))
                .collect(Collectors.toList());
    }

    public List<CheckerReportDto> getCheckerReport() {
        return reportRepository.getCheckerReport()
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
