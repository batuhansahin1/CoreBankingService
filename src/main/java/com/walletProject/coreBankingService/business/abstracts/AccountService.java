package com.walletProject.coreBankingService.business.abstracts;

import java.util.UUID;

import com.walletProject.coreBankingService.business.requests.CreateAccountRequest;
import com.walletProject.coreBankingService.business.requests.CreateTransactionRequest;
import com.walletProject.coreBankingService.messaging.events.TransferCreatedEvent;

public interface AccountService {

	void add(CreateAccountRequest createAccountRequest);

	void withdraw(int accountId, CreateTransactionRequest request);

	void deposit(int accountId, CreateTransactionRequest request);

	void processTransfer(TransferCreatedEvent event);

	
}
