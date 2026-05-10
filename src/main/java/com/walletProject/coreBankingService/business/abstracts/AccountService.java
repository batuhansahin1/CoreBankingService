package com.walletProject.coreBankingService.business.abstracts;

import java.util.UUID;

import com.walletProject.coreBankingService.business.requests.CreateAccountRequest;
import com.walletProject.coreBankingService.business.requests.CreateTransactionRequest;

public interface AccountService {

	void add(CreateAccountRequest createAccountRequest);

	void withdraw(UUID accountId, CreateTransactionRequest request);

	void deposit(UUID accountId, CreateTransactionRequest request);

	
}
