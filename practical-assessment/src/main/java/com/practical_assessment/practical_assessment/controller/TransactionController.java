package com.practical_assessment.practical_assessment.controller;

import com.practical_assessment.practical_assessment.dto.TransactionRequestDTO;
import com.practical_assessment.practical_assessment.response.ApiResponse;
import com.practical_assessment.practical_assessment.response.TransactionResponse;
import com.practical_assessment.practical_assessment.service.TransactionService;
import jakarta.transaction.InvalidTransactionException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;



    //RECORD TRANSACTIONS
    @PostMapping("/record-transactions")
    public ResponseEntity<ApiResponse<TransactionResponse>> recordTransaction(
            @Valid @RequestBody TransactionRequestDTO request) throws InvalidTransactionException {
        log.info("Incoming transaction request: {}", request);
        TransactionResponse response = transactionService.recordTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Transaction recorded successfully", response));
    }



}
