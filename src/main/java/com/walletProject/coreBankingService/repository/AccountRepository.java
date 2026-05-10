package com.walletProject.coreBankingService.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walletProject.coreBankingService.models.entities.Accounts;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Integer> {

	
	public boolean existsById(UUID id );
	
	public Accounts findById(UUID id);
}
