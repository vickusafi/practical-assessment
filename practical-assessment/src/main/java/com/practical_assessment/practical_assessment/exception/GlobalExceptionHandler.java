package com.practical_assessment.practical_assessment.exception;


import com.practical_assessment.practical_assessment.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j

@RestControllerAdvice
public class GlobalExceptionHandler {

    //HANDLE 404
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccountNotFound(AccountNotFoundException ex) {
        log.warn("Account not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }

    //Handle Duplicate Account
    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateAccount(DuplicateAccountException ex) {
        log.warn("Duplicate account: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage()));
    }
}
