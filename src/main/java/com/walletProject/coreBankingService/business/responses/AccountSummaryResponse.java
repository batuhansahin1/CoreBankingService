package com.walletProject.coreBankingService.business.responses;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSummaryResponse {

	private BigDecimal balance;
	private String userIban;
	private String firstName;
    private String lastName;
    private String currency;
}
