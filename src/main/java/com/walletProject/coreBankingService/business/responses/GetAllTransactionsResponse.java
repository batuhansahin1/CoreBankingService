package com.walletProject.coreBankingService.business.responses;

import java.math.BigDecimal;

import com.walletProject.coreBankingService.models.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllTransactionsResponse {

	
	private String ibanNumber;
	private BigDecimal amount;
	private TransactionType type;
	private String referanceId;
}
