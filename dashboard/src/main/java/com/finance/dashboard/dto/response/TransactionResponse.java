package com.finance.dashboard.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {

    private Long id;
    private Double amount;
    private String type;
    private String category;
    private String notes;
    private LocalDate date;
}