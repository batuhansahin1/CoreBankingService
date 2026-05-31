package com.walletProject.coreBankingService.business.requests;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateTransactionRequest {

	
	private BigDecimal amount;
	
	private String referanceId;
}
