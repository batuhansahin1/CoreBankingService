package com.walletProject.coreBankingService.business.rules;

import org.springframework.stereotype.Service;

import com.walletProject.coreBankingService.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerBusinessRules {

	private final CustomerRepository customerRepository;
	
	public void isExistsByTcKimlikNo(String tcKimlik) {
		 
		if(!this.customerRepository.existsByTcKimlikNo(tcKimlik)) {
			throw new RuntimeException("There is no account related with this TC Kimlik");
		}
		
	}

}
