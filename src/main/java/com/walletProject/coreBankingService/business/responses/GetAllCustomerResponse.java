package com.walletProject.coreBankingService.business.responses;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCustomerResponse {

	private String firstName;
	private String lastName;
	private String customerNumber;
	private String tcKimlikNo;
}
