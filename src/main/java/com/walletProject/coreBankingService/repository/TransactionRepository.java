package com.walletProject.coreBankingService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walletProject.coreBankingService.models.entities.Transactions;


@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

	
	boolean existsByReferanceId(String referanceId);

	List<Transactions> findAllByAccountId(int accountId);
	
	@EntityGraph(attributePaths= {"account"})
	List<Transactions> findAll();
}
