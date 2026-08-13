package com.practical_assessment.practical_assessment.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
//Account Opening Request
public class AccountRequestDTO {

    @NotBlank(message = "Account Number is required")
    @Pattern(regexp = "^[A-Z0-9]{6,20}$", message = "Account number must be 6-20 uppercase letters/digits")
    private String accountNumber;

    @NotBlank(message = "Owner Name is required")
    private String ownerName;

    @DecimalMin(value = "0.0",message = "Opening Balance cannot be negative")
    private BigDecimal openingBalance;

}
