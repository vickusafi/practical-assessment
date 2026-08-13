package com.practical_assessment.practical_assessment.response;

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
public class AccountResponse {
    private String accountNumber;
    private String ownerName;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
