package com.walletProject.coreBankingService.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.walletProject.coreBankingService.business.abstracts.TransactionService;
import com.walletProject.coreBankingService.business.responses.GetAllTransactionsResponse;
import com.walletProject.coreBankingService.business.rules.AccountBusinessRules;
import com.walletProject.coreBankingService.business.rules.CustomerBusinessRules;
import com.walletProject.coreBankingService.business.rules.TransactionBusinessRules;
import com.walletProject.coreBankingService.core.utilities.mappers.TransactionMapper;
import com.walletProject.coreBankingService.models.entities.Accounts;
import com.walletProject.coreBankingService.models.entities.Customers;
import com.walletProject.coreBankingService.models.entities.Transactions;
import com.walletProject.coreBankingService.repository.AccountRepository;
import com.walletProject.coreBankingService.repository.CustomerRepository;
import com.walletProject.coreBankingService.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionsManager implements TransactionService {

	private final AccountRepository accountRepository;
	private  final TransactionRepository transactionRepository;
	private final CustomerRepository customerRepository;
	private final AccountBusinessRules accountBusinessRules;
	private final CustomerBusinessRules customerBusinessRules;
	private final TransactionBusinessRules transactionBusinessRules;
	private final TransactionMapper transactionMapper;
	


	@Override
	public List<GetAllTransactionsResponse> getAllTransfersByTcKimlikNo(String tcKimlikNo) {
		this.customerBusinessRules.isExistsByTcKimlikNo(tcKimlikNo);
		Customers customer=this.customerRepository.findByTcKimlikNo(tcKimlikNo);
		this.accountBusinessRules.isCustomerExists(customer.getId());
		Accounts account=this.accountRepository.findByCustomerId(customer.getId());
		
		//this.transactionBusinessRules.isExistsByAccountId(account.getId());
	    //list null olursa hiçbir şey döndürmüyor
		List<Transactions> transactionList =transactionRepository.findAllByAccountId(account.getId());
		List<GetAllTransactionsResponse> responseList=
transactionList.stream().map(transaction->this.transactionMapper.transactionToGetAllTransactionsResponse(transaction)).collect(Collectors.toList());
		return responseList;
	}

	
	
}
