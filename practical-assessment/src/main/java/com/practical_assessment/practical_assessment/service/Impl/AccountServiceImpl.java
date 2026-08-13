package com.practical_assessment.practical_assessment.service.Impl;

import com.practical_assessment.practical_assessment.dto.AccountRequestDTO;
import com.practical_assessment.practical_assessment.entity.Account;
import com.practical_assessment.practical_assessment.exception.AccountNotFoundException;
import com.practical_assessment.practical_assessment.exception.DuplicateAccountException;
import com.practical_assessment.practical_assessment.repository.AccountRepository;
import com.practical_assessment.practical_assessment.response.AccountResponse;
import com.practical_assessment.practical_assessment.response.BalanceResponse;
import com.practical_assessment.practical_assessment.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    //CREATE CUSTOMER ACCOUNT
    @Override
    public AccountResponse createAccount(AccountRequestDTO accountRequestDTO) {

        log.info("Creating account: {}", accountRequestDTO.getAccountNumber());

        if (accountRepository.existsByAccountNumber(accountRequestDTO.getAccountNumber())) {
            log.warn("Attempt to create duplicate account: {}", accountRequestDTO.getAccountNumber());
            throw new DuplicateAccountException(accountRequestDTO.getAccountNumber());
        }
        Account account = Account.builder()
                .accountNumber(accountRequestDTO.getAccountNumber())
                .ownerName(accountRequestDTO.getOwnerName())
                .balance(accountRequestDTO.getOpeningBalance() != null ? accountRequestDTO.getOpeningBalance() : BigDecimal.ZERO)
                .build();

        Account saved = accountRepository.save(account);
        log.info("Account created successfully: {}", saved.getAccountNumber());
        return AccountResponse.builder()
                .accountNumber(saved.getAccountNumber())
                .ownerName(saved.getOwnerName())
                .balance(saved.getBalance())
                .createdAt(saved.getCreatedAt())
                .build();

    }


    //GET ACCOUNT BALANCE
    @Override
    public BalanceResponse getBalance(String accountNumber) {
        log.info("Fetching balance for account: {}", accountNumber);

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        currencyFormat.setCurrency(Currency.getInstance("KES"));
        String formatted = currencyFormat.format(account.getBalance());
        return BalanceResponse.builder()
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwnerName())
                .balance(account.getBalance())
                .formattedBalance(formatted)
                .currency("KES")
                .asOf(LocalDateTime.now())
                .build();
    }
}
