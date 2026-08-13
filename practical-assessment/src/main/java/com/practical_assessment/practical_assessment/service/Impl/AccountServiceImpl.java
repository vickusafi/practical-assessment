package com.practical_assessment.practical_assessment.service.Impl;

import com.practical_assessment.practical_assessment.dto.AccountRequestDTO;
import com.practical_assessment.practical_assessment.repository.AccountRepository;
import com.practical_assessment.practical_assessment.response.AccountResponse;
import com.practical_assessment.practical_assessment.response.BalanceResponse;
import com.practical_assessment.practical_assessment.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    @Override
    public AccountResponse createAccount(AccountRequestDTO accountRequestDTO) {
        return null;
    }

    @Override
    public BalanceResponse getBalance(String accountNumber) {
        return null;
    }
}
