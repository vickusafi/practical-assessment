package com.practical_assessment.practical_assessment.entity;

import com.practical_assessment.practical_assessment.enums.TransactionStatus;
import com.practical_assessment.practical_assessment.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "transactions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 40)
    private String referenceNumber;

    @Column
    private String fromAccountNumber;


    @Column(nullable = false)
    private String toAccountNumber;

    @Column(nullable = false,precision = 19,scale = 4)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private TransactionStatus status;

    private String remarks;

    @Column(updatable = false)
    private LocalDateTime createdAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = TransactionStatus.PENDING;
        }
    }
}
