package com.walletProject.coreBankingService.business.abstracts;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.walletProject.coreBankingService.business.requests.CreateAccountRequest;
import com.walletProject.coreBankingService.business.requests.CreateTransactionRequest;
import com.walletProject.coreBankingService.business.responses.AccountSummaryResponse;
import com.walletProject.coreBankingService.messaging.events.TransferCreatedEvent;
import com.walletProject.coreBankingService.models.entities.Accounts;


public interface AccountService {

	void add(CreateAccountRequest createAccountRequest);

	void withdraw(int accountId, CreateTransactionRequest request);

	void deposit(int accountId, CreateTransactionRequest request);

	void processTransfer(TransferCreatedEvent event);

	BigDecimal getBalanceByIban(String iban);



	AccountSummaryResponse getUserSummaryByTcKimlik(String tcKimlik);

	boolean checkIbanExists(String iban);


	
}
