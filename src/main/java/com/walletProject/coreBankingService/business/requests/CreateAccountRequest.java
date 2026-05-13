package com.walletProject.coreBankingService.business.requests;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

	private String currency;
	
	private int customerId;
	private String ibanNumber;
	private String balance;
	
	
}
