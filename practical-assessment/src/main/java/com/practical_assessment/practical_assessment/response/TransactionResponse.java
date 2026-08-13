package com.practical_assessment.practical_assessment.response;

import com.practical_assessment.practical_assessment.enums.TransactionStatus;
import com.practical_assessment.practical_assessment.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String referenceNumber;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private String remarks;
    private LocalDateTime createdAt;

}
