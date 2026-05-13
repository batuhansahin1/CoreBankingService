package com.walletProject.coreBankingService.business.rules;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.walletProject.coreBankingService.models.entities.Accounts;
import com.walletProject.coreBankingService.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountBusinessRules {

	private final AccountRepository accountRepository;
	
	public void isExistsByAccountId(int accountId) {
		
		if(!accountRepository.existsById(accountId)) {
			throw new RuntimeException("There is no account with that id");
		}
	}
	
	public void isBalanceSufficeient(int accountId,BigDecimal amount) {
		isExistsByAccountId(accountId);
		Accounts account= this.accountRepository.findById(accountId);
	   if(account.getAvailableBalance().compareTo(amount)<0) {
		 throw new RuntimeException("Balance is not suffiecient for transfer");  
	   }
	   account.setAvailableBalance(account.getAvailableBalance().subtract(amount));
	   account.setBalance(account.getBalance().subtract(amount));
	}
public void isIbanExists(String ibanNumber) {
		
		if(!accountRepository.existsByIbanNumber(ibanNumber)) {
			throw new RuntimeException("Bu iban kayıtlı değil");
		}
	}
}
