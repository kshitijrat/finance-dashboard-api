package com.finance.dashboard.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dashboard.dto.request.TransactionRequest;
import com.finance.dashboard.dto.response.TransactionResponse;
import com.finance.dashboard.enums.TransactionType;
import com.finance.dashboard.model.Transaction;
import com.finance.dashboard.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionResponse create(TransactionRequest request, String userId) {

        Transaction txn = Transaction.builder()
                .amount(request.getAmount())
                .type(TransactionType.valueOf(request.getType()))
                .category(request.getCategory())
                .notes(request.getNotes())
                .date(LocalDate.now())
                .createdBy(Long.valueOf(userId))
                .build();

        Transaction saved = transactionRepository.save(txn);

        return mapToResponse(saved);
    }

    public List<TransactionResponse> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    private TransactionResponse mapToResponse(Transaction txn) {
        return TransactionResponse.builder()
                .id(txn.getId())
                .amount(txn.getAmount())
                .type(txn.getType().name())
                .category(txn.getCategory())
                .notes(txn.getNotes())
                .date(txn.getDate())
                .build();
    }
}
