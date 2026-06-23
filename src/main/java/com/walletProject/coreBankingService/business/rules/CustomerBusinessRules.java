package com.walletProject.coreBankingService.business.rules;

import org.springframework.stereotype.Service;

import com.walletProject.coreBankingService.core.utilities.exceptions.CustomerAlreadyExistsException;
import com.walletProject.coreBankingService.core.utilities.exceptions.NoSuchCustomerExistsException;
import com.walletProject.coreBankingService.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerBusinessRules {

	private final CustomerRepository customerRepository;
	
	public void isExistsByTcKimlikNo(String tcKimlik) {
		 
		if(!this.customerRepository.existsByTcKimlikNo(tcKimlik)) {
			throw new RuntimeException("There is no customer related with this TC Kimlik");
		}
		
	}
	public void isNotExistsByTcKimlikNo(String tcKimlik) {
		if(this.customerRepository.existsByTcKimlikNo(tcKimlik)) {
			throw new CustomerAlreadyExistsException("There is an customer related with this TC Kimlik you cannot create a new customer with TC Kimlik");
		}
	}
	

	public void isCustomerExistsByCustomerNumber(String customerNumber) {
		
		if(!this.customerRepository.existsByCustomerNumber(customerNumber)) {
			throw new NoSuchCustomerExistsException("There is no customer related with this customer number try different one");
		}
		
	}
	public void isCustomerNotExistsByCustomerNumber(String customerNumber) {
		// TODO Auto-generated method stub
		if(this.customerRepository.existsByCustomerNumber(customerNumber)) {
			throw new CustomerAlreadyExistsException("There is an customer related with this customer number ");
		}
	}

}
