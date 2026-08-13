package com.practical_assessment.practical_assessment.controller;

import com.practical_assessment.practical_assessment.dto.AccountRequestDTO;
import com.practical_assessment.practical_assessment.response.AccountResponse;
import com.practical_assessment.practical_assessment.response.ApiResponse;
import com.practical_assessment.practical_assessment.response.BalanceResponse;
import com.practical_assessment.practical_assessment.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    //CREATE ACCOUNT
    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountRequestDTO request) {
        log.info("Incoming request to create account: {}", request.getAccountNumber());
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Account created successfully", response));
    }

    //GET ACCOUNT BALANCE END POINT
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(@PathVariable String accountNumber) {
        log.info("Incoming request for balance: {}", accountNumber);
        BalanceResponse response = accountService.getBalance(accountNumber);
        return ResponseEntity.ok(ApiResponse.success("Balance retrieved successfully", response));
    }
}
