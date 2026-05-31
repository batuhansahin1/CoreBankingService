package com.walletProject.coreBankingService.business.abstracts;

import java.util.List;

import com.walletProject.coreBankingService.business.responses.GetAllTransactionsResponse;

public interface TransactionService {

	

	List<GetAllTransactionsResponse> getAllTransfersByTcKimlikNo(String tcKimlikNo);

}
