package com.finance.dashboard.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.finance.dashboard.enums.TransactionType;

import lombok.*;

@Document(collection = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private String id;

    private Double amount;

    private TransactionType type;

    private String category;

    private LocalDate date;

    private String notes;

    private String createdBy; // userId
}