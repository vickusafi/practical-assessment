package com.practical_assessment.practical_assessment.repository;

import com.practical_assessment.practical_assessment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Optional<Transaction> findByReferenceNumber(String referenceNumber);

    List<Transaction> findByFromAccountNumberOrToAccountNumber(String from, String to);
}
