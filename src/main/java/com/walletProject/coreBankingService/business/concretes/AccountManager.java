package com.walletProject.coreBankingService.business.concretes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.walletProject.coreBankingService.business.abstracts.AccountService;
import com.walletProject.coreBankingService.business.requests.CreateAccountRequest;
import com.walletProject.coreBankingService.business.requests.CreateTransactionRequest;
import com.walletProject.coreBankingService.business.rules.AccountBusinessRules;
import com.walletProject.coreBankingService.business.rules.TransactionBusinessRules;
import com.walletProject.coreBankingService.models.entities.Accounts;
import com.walletProject.coreBankingService.models.entities.Transactions;
import com.walletProject.coreBankingService.repository.AccountRepository;
import com.walletProject.coreBankingService.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AccountManager implements AccountService{

	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;
	private final TransactionBusinessRules transactionBusinessRules;
	private final AccountBusinessRules accountBusinessRules;
	
	@Override
	public void add(CreateAccountRequest createAccountRequest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void withdraw(UUID accountId, CreateTransactionRequest request) {// hesaptan para 
		//çekme birine para gönderdiğinde de bu çalışır
	
		this.accountBusinessRules.isExistsByAccountId(accountId);
		this.transactionBusinessRules.isReferenceIdExists(request.getReferenceId());
		Accounts account=this.accountRepository.findById(accountId);
		//withdraw işlemi
		accountBusinessRules.isBalanceSufficeient(accountId, request.getAmount());
		
		Transactions transaction=new Transactions();
		transaction.setAccount(account);
		transaction.setAmount(request.getAmount());
		transaction.setCurrency(account.getCurrency());
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setReferanceId(request.getReferenceId());
		transaction.setStatus("PENDING");
		transaction.setType("DEBIT");
		//önce account'u savelememiz lazım ve updated olması lazım bu update işlemi gibi
		accountRepository.save(account);
		
		transactionRepository.save(transaction);
		
	}

	@Override
	@Transactional
	public void deposit(UUID accountId, CreateTransactionRequest request) {
		this.transactionBusinessRules.isReferenceIdExists(request.getReferenceId());
		Accounts account=this.accountRepository.findById(accountId);
		BigDecimal newAvBalance= account.getAvailableBalance().add(request.getAmount());
		BigDecimal newBalance=account.getBalance().add(request.getAmount());
		account.setAvailableBalance(newAvBalance);
		account.setBalance(newBalance);
		
		Transactions transaction=new Transactions();
		transaction.setAccount(account);
		transaction.setAmount(request.getAmount());
		transaction.setCurrency(account.getCurrency());
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setReferanceId(request.getReferenceId());
		transaction.setStatus("PENDING");
		transaction.setType("CREDIT");
		accountRepository.save(account);
		transactionRepository.save(transaction);
	}

}
