package com.walletProject.coreBankingService.business.concretes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.walletProject.coreBankingService.business.abstracts.AccountService;
import com.walletProject.coreBankingService.business.requests.CreateAccountRequest;
import com.walletProject.coreBankingService.business.requests.CreateTransactionRequest;
import com.walletProject.coreBankingService.business.requests.UpdateBalanceRequest;
import com.walletProject.coreBankingService.business.responses.AccountSummaryResponse;
import com.walletProject.coreBankingService.business.responses.GetAllAccountResponse;
import com.walletProject.coreBankingService.business.rules.AccountBusinessRules;
import com.walletProject.coreBankingService.business.rules.CustomerBusinessRules;
import com.walletProject.coreBankingService.business.rules.TransactionBusinessRules;
import com.walletProject.coreBankingService.core.utilities.mappers.AccountMapper;
import com.walletProject.coreBankingService.messaging.events.TransferCreatedEvent;
import com.walletProject.coreBankingService.models.entities.Accounts;
import com.walletProject.coreBankingService.models.entities.Customers;
import com.walletProject.coreBankingService.models.entities.Transactions;
import com.walletProject.coreBankingService.models.enums.AccountStatus;
import com.walletProject.coreBankingService.models.enums.TransactionType;
import com.walletProject.coreBankingService.models.enums.TransferStatus;
import com.walletProject.coreBankingService.repository.AccountRepository;
import com.walletProject.coreBankingService.repository.CustomerRepository;
import com.walletProject.coreBankingService.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service 
@RequiredArgsConstructor
public class AccountManager implements AccountService{
 
	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;
	private final TransactionBusinessRules transactionBusinessRules;
	private final AccountBusinessRules accountBusinessRules;
	private final CustomerBusinessRules customerBusinessRules;
	private final CustomerRepository customerRepository;
	private final AccountMapper accountMapper;
	
	@Override
	public void add(CreateAccountRequest createAccountRequest) {
		   
		   
		
	}
	@Transactional
    public void createDefaultAccountForCustomer(Customers customer) { 
        
        Accounts newAccount = new Accounts();
        
        
        newAccount.setCustomer(customer); 
        
        
        String generatedIban = generateRandomIban();
        newAccount.setIbanNumber(generatedIban);
   
        newAccount.setAccountNumber(generatedIban.substring(10)); 
        
        
        newAccount.setBalance(BigDecimal.TEN);
        newAccount.setAvailableBalance(BigDecimal.TEN);
        
        
        newAccount.setCurrency("TRY");
        

        newAccount.setStatus(AccountStatus.ACTIVE); 
        
        
        accountRepository.save(newAccount);
        
        // Loglama yaparak konsolda işlemin başarılı olduğunu görmek hayat kurtarır
        System.out.println("Otomatik hesap oluşturuldu. Müşteri ID: " + customer.getId() + " | IBAN: " + generatedIban);
    }
	
	@Transactional
    public void processTransfer(TransferCreatedEvent event) {
        log.info("Transfer işlemi işleniyor. Referans: {}", event.getTransferReferenceId());

        
        this.accountBusinessRules.isIbanExists(event.getSenderIban());
        Accounts senderAccount = accountRepository.findByIbanNumber(event.getSenderIban());

        if (senderAccount.getBalance().compareTo(event.getAmount()) < 0) {
            log.error("Yetersiz bakiye! Gönderen IBAN: {}", event.getSenderIban());
            throw new RuntimeException("Yetersiz bakiye!"); // Saga'da burası patlarsa iptal süreci başlar
        }

        
        this.accountBusinessRules.isIbanExists(event.getReceiverIban());
        Accounts receiverAccount = accountRepository.findByIbanNumber(event.getReceiverIban());
       
         CreateTransactionRequest request=new CreateTransactionRequest();
         request.setAmount(event.getAmount());
         request.setReferanceId(event.getTransferReferenceId());
         this.withdraw(senderAccount.getId(), request);
        this.deposit(receiverAccount.getId(), request);
         
        senderAccount.setBalance(senderAccount.getBalance().subtract(event.getAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(event.getAmount()));

       

        log.info("Transfer başarıyla tamamlandı. Referans: {}. {} tutar {} hesabından {} hesabına aktarıldı.", 
                 event.getTransferReferenceId(), event.getAmount(), event.getSenderIban(), event.getReceiverIban());
                 

    }
	
	@Override
	@Transactional
	public void withdraw(int accountId, CreateTransactionRequest request) {// hesaptan para 
		//çekme birine para gönderdiğinde de bu çalışır
	
		this.accountBusinessRules.isExistsByAccountId(accountId);
		this.transactionBusinessRules.isReferenceIdExists(request.getReferanceId());
		Accounts account=this.accountRepository.findById(accountId);
		//withdraw işlemi
		accountBusinessRules.isBalanceSufficeient(accountId, request.getAmount());
		BigDecimal newBalance = account.getBalance().subtract(request.getAmount());
	    BigDecimal newAvailableBalance = account.getAvailableBalance().subtract(request.getAmount());
		account.setBalance(newBalance);
		account.setAvailableBalance(newAvailableBalance);
	    
	    Transactions transaction=new Transactions();
		transaction.setAccount(account);
		transaction.setAmount(request.getAmount());
		transaction.setCurrency(account.getCurrency());
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setReferanceId(request.getReferanceId());
		transaction.setStatus(TransferStatus.COMPLETED);
		transaction.setType(TransactionType.DEBIT);
	
		accountRepository.save(account);
		
		transactionRepository.save(transaction);
		
	}

	@Override
	@Transactional
	public void deposit(int accountId, CreateTransactionRequest request) {
		//System.out.println( this.transactionRepository.existsByReferanceId(request.getReferanceId()));
		//this.transactionBusinessRules.isReferenceIdExists(request.getReferanceId());
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
		transaction.setReferanceId(request.getReferanceId());
		transaction.setStatus(TransferStatus.COMPLETED);
		transaction.setType(TransactionType.CREDIT);
		accountRepository.save(account);
		transactionRepository.save(transaction);
	}

	@Override
	public BigDecimal getBalanceByIban(String iban) {
		this.accountBusinessRules.isIbanExists(iban);
		Accounts account =this.accountRepository.findByIbanNumber(iban);
		
		
		return account.getBalance();
	}



	@Override
	public AccountSummaryResponse getUserSummaryByTcKimlik(String tcKimlik) {
		this.customerBusinessRules.isExistsByTcKimlikNo(tcKimlik);
		Customers customer =this.customerRepository.findByTcKimlikNo(tcKimlik);
		
		this.accountBusinessRules.isCustomerExists(customer.getId());
		Accounts account=this.accountRepository.findByCustomerId(customer.getId());
		AccountSummaryResponse accountSummary=this.accountMapper.accountToAccountSummary(account);
		return accountSummary;
	}
	private String generateRandomIban() {
	
    StringBuilder iban = new StringBuilder("TR");
    Random random = new Random();
    
    
    for (int i = 0; i < 24; i++) {
        iban.append(random.nextInt(10)); 
    }
    
    return iban.toString();
}
	@Override
	public boolean checkIbanExists(String iban) {
		
		return this.accountRepository.existsByIbanNumber(iban);
	}
	@Override
	public List<GetAllAccountResponse> getAllAccounts() {
		 List<Accounts>accountList=this.accountRepository.findAll();
		 
		List<GetAllAccountResponse> accountSummaryList=accountList.stream().map(account->this.accountMapper.accountToGetAllAccountResponse(account)).collect(Collectors.toList());
	
		return accountSummaryList;
	}
	@Override
	public void updateAccountBalance(String accountNumber, UpdateBalanceRequest updateBalanceRequest) {
		this.accountBusinessRules.isExistsByAccountNumber(accountNumber);
		Accounts account=this.accountRepository.findByIbanNumber(accountNumber);
		account.setBalance(updateBalanceRequest.getBalance());
		account.setAvailableBalance(updateBalanceRequest.getBalance());
		this.accountRepository.save(account);
	}



}
