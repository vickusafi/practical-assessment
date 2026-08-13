package com.practical_assessment.practical_assessment.service;

import com.practical_assessment.practical_assessment.dto.AccountRequestDTO;
import com.practical_assessment.practical_assessment.response.AccountResponse;
import com.practical_assessment.practical_assessment.response.BalanceResponse;

public interface AccountService {

    AccountResponse createAccount(AccountRequestDTO accountRequestDTO);

    BalanceResponse getBalance(String accountNumber);
}

