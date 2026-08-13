package com.practical_assessment.practical_assessment.service.Impl;

import com.practical_assessment.practical_assessment.dto.TransactionRequestDTO;
import com.practical_assessment.practical_assessment.entity.Account;
import com.practical_assessment.practical_assessment.entity.Transaction;
import com.practical_assessment.practical_assessment.enums.TransactionStatus;
import com.practical_assessment.practical_assessment.enums.TransactionType;
import com.practical_assessment.practical_assessment.exception.AccountNotFoundException;
import com.practical_assessment.practical_assessment.repository.AccountRepository;
import com.practical_assessment.practical_assessment.repository.TransactionRepository;
import com.practical_assessment.practical_assessment.response.TransactionResponse;
import com.practical_assessment.practical_assessment.service.TransactionService;
import jakarta.transaction.InvalidTransactionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    @Override
    public TransactionResponse recordTransaction(TransactionRequestDTO transactionRequestDTO) throws InvalidTransactionException {
        log.info("Recording transaction: type={}, to={}, amount={}",
                transactionRequestDTO.getType(), transactionRequestDTO.getToAccountNumber(), transactionRequestDTO.getAmount());
        // Basic validation for transfer type
        if (transactionRequestDTO.getType() == TransactionType.TRANSFER) {
            throw new InvalidTransactionException("Invalid Transaction type");
        }
        Account toAccount = accountRepository.findByAccountNumber(transactionRequestDTO.getToAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(transactionRequestDTO.getToAccountNumber()));

        Transaction transaction = Transaction.builder()
                .referenceNumber(generateReference())
                .fromAccountNumber(transactionRequestDTO.getFromAccountNumber())
                .toAccountNumber(transactionRequestDTO.getToAccountNumber())
                .amount(transactionRequestDTO.getAmount())
                .type(transactionRequestDTO.getType())
                .remarks(transactionRequestDTO.getRemarks())
                .status(TransactionStatus.PENDING)
                .build();

        //Credit destination account Immediately
        if (transactionRequestDTO.getType() == TransactionType.DEPOSIT) {
            toAccount.setBalance(toAccount.getBalance().add(transactionRequestDTO.getAmount()));
            accountRepository.save(toAccount);
            transaction.setStatus(TransactionStatus.SUCCESS);
        }

        Transaction saved = transactionRepository.save(transaction);
        log.info("Transaction recorded: ref={}, status={}", saved.getReferenceNumber(), saved.getStatus());

        return toResponse(saved);
    }


    //Generate reference Number
    private String generateReference() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .referenceNumber(transaction.getReferenceNumber())
                .fromAccountNumber(transaction.getFromAccountNumber())
                .toAccountNumber(transaction.getToAccountNumber())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .status(transaction.getStatus())
                .remarks(transaction.getRemarks())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
