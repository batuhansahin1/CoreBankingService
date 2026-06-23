package com.walletProject.coreBankingService.core.utilities.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.walletProject.coreBankingService.business.requests.CreateAccountRequest;
import com.walletProject.coreBankingService.business.responses.AccountSummaryResponse;
import com.walletProject.coreBankingService.business.responses.GetAllAccountResponse;
import com.walletProject.coreBankingService.models.entities.Accounts;

@Mapper(componentModel = "spring")
public interface AccountMapper {

	 
	@Mapping(source="balance",target="balance")
	@Mapping(source="ibanNumber",target="userIban")
	@Mapping(source="customer.firstName",target="firstName")
	@Mapping(source="customer.lastName",target="lastName")
	@Mapping(source="currency",target="currency")
	AccountSummaryResponse accountToAccountSummary(Accounts account);

	@Mapping(source="balance",target="balance")
	@Mapping(source="ibanNumber",target="userIban")
	@Mapping(source="customer.firstName",target="firstName")
	@Mapping(source="customer.lastName",target="lastName")
	@Mapping(source="currency",target="currency")
	GetAllAccountResponse accountToGetAllAccountResponse(Accounts account);
	
	//customerid var ama bunu veritabanından bulup setlemek lazım
	@Mapping(source="balance",target="balance")
	@Mapping(source="ibanNumber",target="ibanNumber")
	@Mapping(source="currency",target="currency")
	Accounts CreateAccountRequestToAccounts(CreateAccountRequest createAccountRequest);
	
}
