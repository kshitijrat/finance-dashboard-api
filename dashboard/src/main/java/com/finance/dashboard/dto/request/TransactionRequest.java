package com.finance.dashboard.dto.request;

import lombok.Data;

@Data
public class TransactionRequest {

    private Double amount;
    private String type; // INCOME / EXPENSE
    private String category;
    private String notes;
}