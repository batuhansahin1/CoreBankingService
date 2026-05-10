package com.walletProject.coreBankingService.webApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walletProject.coreBankingService.business.abstracts.AccountService;
import com.walletProject.coreBankingService.business.requests.CreateAccountRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
public class AccountsController {

	private final AccountService accountService;
	
	@PostMapping("/add")
	public void add(CreateAccountRequest createAccountRequest) {
		
		accountService.add(createAccountRequest);
	}
	

	
}
