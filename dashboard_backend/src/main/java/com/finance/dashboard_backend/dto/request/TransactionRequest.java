package com.finance.dashboard_backend.dto.request;

import com.finance.dashboard_backend.enums.TransactionType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    @NotNull @Positive private BigDecimal amount;
    @NotNull private TransactionType type;
    @NotBlank private String category;
    @NotNull private LocalDate date;
    private String notes;
}