package com.walletProject.coreBankingService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walletProject.coreBankingService.models.entities.Transactions;


@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

	
	boolean existsByReferanceId(String referanceId);
}
