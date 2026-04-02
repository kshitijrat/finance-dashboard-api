package com.finance.dashboard_backend.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.finance.dashboard_backend.dto.response.DashboardSummaryResponse;
import com.finance.dashboard_backend.enums.TransactionType;
import com.finance.dashboard_backend.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TransactionRepository transactionRepository;

    public DashboardSummaryResponse getSummary() {
        BigDecimal income = transactionRepository.sumByType(TransactionType.INCOME);
        BigDecimal expense = transactionRepository.sumByType(TransactionType.EXPENSE);
        
        // Null checks for safety
        income = (income == null) ? BigDecimal.ZERO : income;
        expense = (expense == null) ? BigDecimal.ZERO : expense;

        return DashboardSummaryResponse.builder()
                .totalIncome(income)
                .totalExpenses(expense)
                .netBalance(income.subtract(expense))
                .categoryWiseData(transactionRepository.getCategoryWiseSum())
                .build();
    }
}
