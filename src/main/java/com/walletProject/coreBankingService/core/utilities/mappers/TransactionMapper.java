package com.walletProject.coreBankingService.core.utilities.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.walletProject.coreBankingService.business.responses.GetAllTransactionsResponse;
import com.walletProject.coreBankingService.models.entities.Transactions;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

	
	@Mapping(source="account.ibanNumber",target="ibanNumber")
	@Mapping(source="amount",target="amount")
	@Mapping(source="type",target="type")
	@Mapping(source="referanceId",target="referanceId")
	@Mapping(source="currency",target="currency")
	GetAllTransactionsResponse transactionToGetAllTransactionsResponse(Transactions transaction);
}
