package com.walletProject.coreBankingService.core.utilities.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.walletProject.coreBankingService.business.requests.CreateCustomerRequest;
import com.walletProject.coreBankingService.business.responses.GetAllCustomerResponse;
import com.walletProject.coreBankingService.business.responses.GetCustomerResponse;
import com.walletProject.coreBankingService.messaging.events.UserRegisteredEvent;
import com.walletProject.coreBankingService.models.entities.Customers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	
	@Mapping(source="firstName",target="firstName")
	@Mapping(source="lastName",target="lastName")
	@Mapping(source="tcKimlikNo",target="tcKimlikNo")
	@Mapping(source="type",target="type")
	Customers createCustomerRequestToCustomer(CreateCustomerRequest createCustomerRequest);
	 
	@Mapping(source="firstName",target="firstName")
	@Mapping(source="lastName",target="lastName")
	@Mapping(source="tcKimlik",target="tcKimlikNo")
	@Mapping(source="customerType",target="type")
	Customers createUserRegisteredEventToCustomer(UserRegisteredEvent event);
	
	@Mapping(source="firstName",target="firstName")
	@Mapping(source="lastName",target="lastName")
	@Mapping(source="tcKimlikNo",target="tcKimlikNo")
	@Mapping(source="customerNumber",target="customerNumber")
	GetAllCustomerResponse customerToGetAllCustomerResponse(Customers customer);
	
	@Mapping(source="firstName",target="firstName")
	@Mapping(source="lastName",target="lastName")
	@Mapping(source="tcKimlikNo",target="tcKimlikNo")
	GetCustomerResponse customerToGetCustomerResponse(Customers customer);
}
