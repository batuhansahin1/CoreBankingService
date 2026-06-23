package com.walletProject.coreBankingService.business.requests;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBalanceRequest {

	@NotBlank(message = "Bakiye")
	private BigDecimal balance;
}
