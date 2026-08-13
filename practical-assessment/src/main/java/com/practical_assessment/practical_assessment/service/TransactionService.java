package com.practical_assessment.practical_assessment.service;

import com.practical_assessment.practical_assessment.dto.TransactionRequestDTO;
import com.practical_assessment.practical_assessment.dto.TransferRequestDTO;
import com.practical_assessment.practical_assessment.response.TransactionResponse;
import jakarta.transaction.InvalidTransactionException;

public interface TransactionService {

    //RECORD CUSTOMER TRANSACTIONS
    TransactionResponse recordTransaction(TransactionRequestDTO transactionRequestDTO) throws InvalidTransactionException;

    //TRANSFER FUNDS FROM ONE ACCOUNT TO ANOTHER
    TransactionResponse transferFunds(TransferRequestDTO transferRequestDTO) throws InvalidTransactionException;


}
