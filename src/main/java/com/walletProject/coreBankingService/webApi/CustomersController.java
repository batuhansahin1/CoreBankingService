package com.walletProject.coreBankingService.webApi;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walletProject.coreBankingService.business.abstracts.CustomerService;
import com.walletProject.coreBankingService.business.requests.CreateCustomerRequest;
import com.walletProject.coreBankingService.business.requests.UpdateCustomerStatusRequest;
import com.walletProject.coreBankingService.business.responses.GetAllCustomerResponse;
import com.walletProject.coreBankingService.business.responses.GetCustomerResponse;
import com.walletProject.coreBankingService.models.entities.Customers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomersController {

	private final CustomerService customerService;
	
	@GetMapping("/getAllCustomers")
	List<GetAllCustomerResponse> getAllCustomers(){
		return customerService.getAllCustomers();
	}

	@GetMapping("/get-customer/{customerNumber}")
	public GetCustomerResponse getCustomer(@PathVariable String customerNumber) {
		return this.customerService.getCustomerByCustomerNumber(customerNumber);
	}
	
	
	@PostMapping("/add-customer")
	public void add(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
		
		this.customerService.add(createCustomerRequest);
	}
	
	@DeleteMapping("delete-customer/{customerNumber}")
	public void delete (@PathVariable String customerNumber) {
		
		this.customerService.delete(customerNumber);
	}
	@PatchMapping("/update-customer-status/{customerNumber}")
	void updateCustomerStatus (@PathVariable String customerNumber,@Valid @RequestBody UpdateCustomerStatusRequest updateCustomerStatusRequest) {
		this.customerService.updateCustomerStatus(customerNumber,updateCustomerStatusRequest); 
	}
}
