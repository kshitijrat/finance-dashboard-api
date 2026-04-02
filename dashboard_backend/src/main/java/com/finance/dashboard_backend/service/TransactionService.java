package com.finance.dashboard_backend.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.finance.dashboard_backend.dto.request.TransactionRequest;
import com.finance.dashboard_backend.model.Transaction;
import com.finance.dashboard_backend.model.User;
import com.finance.dashboard_backend.repository.TransactionRepository;
import com.finance.dashboard_backend.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction createTransaction(TransactionRequest request) {
        // Current logged-in user nikalo
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDetails.getUser();

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .transType(request.getType())
                .category(request.getCategory())
                .transDate(request.getDate())
                .notes(request.getNotes())
                .createdBy(currentUser)
                .build();
        
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setDeleted(true); // Soft Delete
        transactionRepository.save(transaction);
    }
}