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
import com.walletProject.coreBankingService.messaging.events.TransferCreatedEvent;
import com.walletProject.coreBankingService.models.entities.Accounts;
import com.walletProject.coreBankingService.models.entities.Transactions;
import com.walletProject.coreBankingService.models.enums.TransactionType;
import com.walletProject.coreBankingService.models.enums.TransferStatus;
import com.walletProject.coreBankingService.repository.AccountRepository;
import com.walletProject.coreBankingService.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
	
	@Override
	public void add(CreateAccountRequest createAccountRequest) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
    public void processTransfer(TransferCreatedEvent event) {
        log.info("Transfer işlemi işleniyor. Referans: {}", event.getTransferReferenceId());

        // 1. Gönderenin Hesabını Bul ve Kontrol Et
        this.accountBusinessRules.isIbanExists(event.getSenderIban());
        Accounts senderAccount = accountRepository.findByIbanNumber(event.getSenderIban());

        if (senderAccount.getBalance().compareTo(event.getAmount()) < 0) {
            log.error("Yetersiz bakiye! Gönderen IBAN: {}", event.getSenderIban());
            throw new RuntimeException("Yetersiz bakiye!"); // Saga'da burası patlarsa iptal süreci başlar
        }

        // 2. Alıcının Hesabını Bul
        Accounts receiverAccount = accountRepository.findByIbanNumber(event.getReceiverIban());
        this.accountBusinessRules.isIbanExists(event.getReceiverIban());

        // 3. Parayı Aktar (Hesapla)
        senderAccount.setBalance(senderAccount.getBalance().subtract(event.getAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(event.getAmount()));

        // 4. Veritabanına Kaydet
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        log.info("Transfer başarıyla tamamlandı. Referans: {}. {} tutar {} hesabından {} hesabına aktarıldı.", 
                 event.getTransferReferenceId(), event.getAmount(), event.getSenderIban(), event.getReceiverIban());
                 
        // NOT: İleride Saga mimarisini tamamlarken, burada işlemin başarılı olduğuna dair
        // Transfer servisine "TransferCompletedEvent" fırlatacağız.
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
		transaction.setStatus(TransferStatus.PENDING);
		transaction.setType(TransactionType.DEBIT);
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
		transaction.setStatus(TransferStatus.PENDING);
		transaction.setType(TransactionType.CREDIT);
		accountRepository.save(account);
		transactionRepository.save(transaction);
	}

}
