package com.walletProject.coreBankingService.business.rules;

import org.springframework.stereotype.Service;

import com.walletProject.coreBankingService.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TransactionBusinessRules {

	private final TransactionRepository transactionRepo;
	
	public void isReferenceIdExists(String referanceId) {
		
		if(transactionRepo.existsByReferanceId(referanceId)) {
			throw new RuntimeException("There is a record with that pls send new event");
		}
	}


}
