package com.practical_assessment.practical_assessment.controller;

import com.practical_assessment.practical_assessment.dto.AccountRequestDTO;
import com.practical_assessment.practical_assessment.response.AccountResponse;
import com.practical_assessment.practical_assessment.response.ApiResponse;
import com.practical_assessment.practical_assessment.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
