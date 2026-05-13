package com.walletProject.coreBankingService.webApi;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walletProject.coreBankingService.business.abstracts.AccountService;
import com.walletProject.coreBankingService.business.requests.CreateTransactionRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("internal/api/v1/accounts")
@RequiredArgsConstructor
public class AccountsInternalController {

	
	private final AccountService accountService;
	
	@PostMapping("/{accountId}/withdraw")
	public ResponseEntity<Void> withdraw(@PathVariable int accountId
			,@RequestBody CreateTransactionRequest request){
		
		accountService.withdraw(accountId,request);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/{accountId}/deposit")
	public ResponseEntity<Void> deposit(@PathVariable int accountId
			,@RequestBody CreateTransactionRequest request){
		
		accountService.deposit(accountId,request);
		
		return ResponseEntity.ok().build();
	}
}
