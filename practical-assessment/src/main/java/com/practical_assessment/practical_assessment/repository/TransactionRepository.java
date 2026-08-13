package com.practical_assessment.practical_assessment.repository;

import com.practical_assessment.practical_assessment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
