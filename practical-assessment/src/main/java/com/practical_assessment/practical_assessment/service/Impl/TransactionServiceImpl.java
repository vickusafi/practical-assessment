package com.practical_assessment.practical_assessment.service.Impl;

import com.practical_assessment.practical_assessment.dto.TransactionRequestDTO;
import com.practical_assessment.practical_assessment.dto.TransferRequestDTO;
import com.practical_assessment.practical_assessment.entity.Account;
import com.practical_assessment.practical_assessment.entity.Transaction;
import com.practical_assessment.practical_assessment.enums.TransactionStatus;
import com.practical_assessment.practical_assessment.enums.TransactionType;
import com.practical_assessment.practical_assessment.exception.AccountNotFoundException;
import com.practical_assessment.practical_assessment.exception.InsufficientFundsException;
import com.practical_assessment.practical_assessment.repository.AccountRepository;
import com.practical_assessment.practical_assessment.repository.TransactionRepository;
import com.practical_assessment.practical_assessment.response.TransactionResponse;
import com.practical_assessment.practical_assessment.service.TransactionService;
import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Transactional
    @Override
    public TransactionResponse transferFunds(TransferRequestDTO transferRequestDTO) throws InvalidTransactionException {
        log.info("Initiating transfer: from={}, to={}, amount={}",
                transferRequestDTO.getFromAccountNumber(), transferRequestDTO.getToAccountNumber(), transferRequestDTO.getAmount());

        if (transferRequestDTO.getFromAccountNumber().equals(transferRequestDTO.getToAccountNumber())) {
            throw new InvalidTransactionException("Cannot transfer to the same account");
        }

        //avoid deadlocks
        String first = transferRequestDTO.getFromAccountNumber().compareTo(transferRequestDTO.getToAccountNumber()) < 0
                ? transferRequestDTO.getFromAccountNumber() : transferRequestDTO.getToAccountNumber();
        String second = first.equals(transferRequestDTO.getFromAccountNumber())
                ? transferRequestDTO.getToAccountNumber() : transferRequestDTO.getFromAccountNumber();

        Account firstLocked = accountRepository.findByAccountNumberForUpdate(first)
                .orElseThrow(() -> new AccountNotFoundException(first));
        Account secondLocked = accountRepository.findByAccountNumberForUpdate(second)
                .orElseThrow(() -> new AccountNotFoundException(second));

        Account fromAccount = first.equals(transferRequestDTO.getFromAccountNumber()) ? firstLocked : secondLocked;
        Account toAccount = first.equals(transferRequestDTO.getFromAccountNumber()) ? secondLocked : firstLocked;

        BigDecimal amount = transferRequestDTO.getAmount();
        //CHECK FOR INSUFFICIENT FUNDS
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            log.warn("Transfer failed - insufficient funds in account: {} (requested={}, available={})",
                    fromAccount.getAccountNumber(), amount, fromAccount.getBalance());
            throw new InsufficientFundsException(fromAccount.getAccountNumber());
        }

        // Debit source, credit destination
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = Transaction.builder()
                .referenceNumber(generateReference())
                .fromAccountNumber(fromAccount.getAccountNumber())
                .toAccountNumber(toAccount.getAccountNumber())
                .amount(amount)
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.SUCCESS)
                .remarks(transferRequestDTO.getRemarks())
                .build();

        Transaction saved = transactionRepository.save(transaction);
        log.info("Transfer successful: ref={}, from={}, to={}, amount={}",
                saved.getReferenceNumber(), fromAccount.getAccountNumber(), toAccount.getAccountNumber(), amount);

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
