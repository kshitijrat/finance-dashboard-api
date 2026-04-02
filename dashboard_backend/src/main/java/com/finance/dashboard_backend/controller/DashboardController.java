package com.finance.dashboard_backend.controller;
import com.finance.dashboard_backend.dto.response.DashboardSummaryResponse;
import com.finance.dashboard_backend.model.Transaction;
import com.finance.dashboard_backend.repository.TransactionRepository;
import com.finance.dashboard_backend.service.DashboardService;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    private final TransactionRepository transactionRepository;

    // Admin and Analyst can see insights, Viewer can also see general summary
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'VIEWER')")
    public ResponseEntity<DashboardSummaryResponse> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }
    @GetMapping("/recent-activity")
    public ResponseEntity<List<Transaction>> getRecent() {
        return ResponseEntity.ok(transactionRepository.findTop10ByOrderByTransDateDesc());
    }
    @GetMapping("/weekly-trends")
    public ResponseEntity<List<Map<String, Object>>> getTrends() {
        return ResponseEntity.ok(transactionRepository.getWeeklyTrends());
    }
}