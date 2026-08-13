package com.practical_assessment.practical_assessment.service;

import com.practical_assessment.practical_assessment.dto.TransactionRequestDTO;
import com.practical_assessment.practical_assessment.response.TransactionResponse;
import jakarta.transaction.InvalidTransactionException;

public interface TransactionService {

    TransactionResponse recordTransaction(TransactionRequestDTO transactionRequestDTO) throws InvalidTransactionException;

}
