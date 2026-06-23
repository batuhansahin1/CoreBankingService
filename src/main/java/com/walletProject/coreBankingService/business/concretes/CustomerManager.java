package com.walletProject.coreBankingService.business.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.walletProject.coreBankingService.business.abstracts.CustomerService;
import com.walletProject.coreBankingService.business.requests.CreateCustomerRequest;
import com.walletProject.coreBankingService.business.requests.UpdateCustomerStatusRequest;
import com.walletProject.coreBankingService.business.responses.GetAllCustomerResponse;
import com.walletProject.coreBankingService.business.responses.GetCustomerResponse;
import com.walletProject.coreBankingService.business.rules.CustomerBusinessRules;
import com.walletProject.coreBankingService.core.utilities.mappers.CustomerMapper;
import com.walletProject.coreBankingService.models.entities.Customers;
import com.walletProject.coreBankingService.repository.CustomerRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor 
public class CustomerManager implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	private final CustomerNumberGeneratorService customerNumberGenerator; 
	private final AccountManager accountManager;
	private final CustomerBusinessRules customerBusinessRules;
	
	@Override
	public List<GetAllCustomerResponse> getAllCustomers() {
		List<GetAllCustomerResponse> customerList=this.customerRepository.findAll().stream().map(customer->this.customerMapper.customerToGetAllCustomerResponse(customer))
				.collect(Collectors.toList());
		return customerList;
	}
	
	@Override 
	//gerek yok normalde zaten müşteri ve hesap otomatik registration event üzerine oluşturuluyor
	public void add(@Valid CreateCustomerRequest createCustomerRequest) {
		this.customerBusinessRules.isNotExistsByTcKimlikNo(createCustomerRequest.getTcKimlikNo());
		Customers customer=this.customerMapper.createCustomerRequestToCustomer(createCustomerRequest);
		customer.setStatus("ACTIVE");
		
		String customerNumber=this.customerNumberGenerator.generateCustomerNumberFromId(createCustomerRequest.getTcKimlikNo());
		customer.setCustomerNumber(customerNumber);
		customer.setCreatedAt(LocalDateTime.now());
		//account oluşturacağız otomatik
		 Customers savedCustomer = customerRepository.save(customer);

		 
	        accountManager.createDefaultAccountForCustomer(savedCustomer);
	}
	@Override
	public void updateCustomerStatus(String customerNumber,
			@Valid UpdateCustomerStatusRequest updateCustomerStatusRequest) {
	this.customerBusinessRules.isCustomerExistsByCustomerNumber(customerNumber);
	Customers customer=	this.customerRepository.findByCustomerNumber(customerNumber);
		
	customer.setStatus(updateCustomerStatusRequest.getStatus());
	this.customerRepository.save(customer);
	}
	@Override
	public void delete(String customerNumber) {
		this.customerBusinessRules.isCustomerExistsByCustomerNumber(customerNumber);
		Customers customer=	this.customerRepository.findByCustomerNumber(customerNumber);
		this.customerRepository.delete(customer);
		
	}

	@Override
	public GetCustomerResponse getCustomerByCustomerNumber(String customerNumber) {
		// TODO Auto-generated method stub
		this.customerBusinessRules.isCustomerExistsByCustomerNumber(customerNumber);
		Customers customer=this.customerRepository.findByCustomerNumber(customerNumber);
		
		return this.customerMapper.customerToGetCustomerResponse(customer) ;
	}


}
