package com.walletProject.coreBankingService.business.rules;

import com.walletProject.coreBankingService.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionBusinessRules {

	private final TransactionRepository transactionRepo;
	
	public void isReferenceIdExists(String referanceId) {
		
		if(transactionRepo.existsByReferanceId(referanceId)) {
			throw new RuntimeException("There is a record with that pls send new event");
		}
	}
}
