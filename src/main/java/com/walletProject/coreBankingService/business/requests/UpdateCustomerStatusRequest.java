package com.walletProject.coreBankingService.business.requests;

import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class UpdateCustomerStatusRequest {
	@Pattern(regexp = "^(ACTIVE|PASSIVE)$", 
            message = "Durum alanı sadece 'ACTIVE' veya 'PASSIVE'  olabilir.")
   private String status;
}
