package com.walletProject.coreBankingService.business.abstracts;

import java.util.List;

import com.walletProject.coreBankingService.business.requests.CreateCustomerRequest;
import com.walletProject.coreBankingService.business.requests.UpdateCustomerStatusRequest;
import com.walletProject.coreBankingService.business.responses.GetAllCustomerResponse;
import com.walletProject.coreBankingService.business.responses.GetCustomerResponse;

import jakarta.validation.Valid;

public interface CustomerService {

	List<GetAllCustomerResponse> getAllCustomers();

	void add(@Valid CreateCustomerRequest createCustomerRequest);

	void updateCustomerStatus(String customerNumber, @Valid UpdateCustomerStatusRequest updateCustomerStatusRequest);

	void delete(String customerNumber);

	GetCustomerResponse getCustomerByCustomerNumber(String customerNumber);

	

}
