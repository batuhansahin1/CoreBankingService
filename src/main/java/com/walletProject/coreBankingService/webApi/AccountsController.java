package com.walletProject.coreBankingService.webApi;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.walletProject.coreBankingService.business.abstracts.AccountService;
import com.walletProject.coreBankingService.business.requests.CreateAccountRequest;
import com.walletProject.coreBankingService.business.requests.UpdateBalanceRequest;
import com.walletProject.coreBankingService.business.responses.AccountSummaryResponse;
import com.walletProject.coreBankingService.business.responses.GetAllAccountResponse;
import com.walletProject.coreBankingService.models.entities.Accounts;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class AccountsController {

	private final AccountService accountService;
	
	@PostMapping("/add")
	public void add(CreateAccountRequest createAccountRequest) {
		
		accountService.add(createAccountRequest);
	} 
	
	
	// Kullanıcının güncel bakiyesini çeken uç
    @GetMapping("/my-balance")
    public ResponseEntity<AccountSummaryResponse> getMyBalance(@RequestParam String tcKimlik) {
        // İleride buradaki IBAN'i JWT token içinden dinamik alacağız
    	AccountSummaryResponse balance = accountService.getUserSummaryByTcKimlik(tcKimlik);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/checkIbanExists/{iban}")
    public boolean checkIbanExists(@PathVariable("iban") String iban) {
    	
    	return this.accountService.checkIbanExists(iban);
    }
	@GetMapping("/getAllAccounts")
	public List<GetAllAccountResponse> getAllAccounts(){
		return accountService.getAllAccounts();
	}
	@PatchMapping("/update-balance/{accountNumber}")
	public void updateAccountBalance(@PathVariable String accountNumber,UpdateBalanceRequest updateBalanceRequest) {
		this.accountService.updateAccountBalance(accountNumber,updateBalanceRequest);
	}
}
