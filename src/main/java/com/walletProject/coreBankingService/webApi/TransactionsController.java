package com.walletProject.coreBankingService.webApi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.walletProject.coreBankingService.business.abstracts.TransactionService;
import com.walletProject.coreBankingService.business.responses.GetAllTransactionsResponse;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class TransactionsController {

	
	private final TransactionService transactionService;
	
	
	@GetMapping("/my-history")
    public ResponseEntity<List<GetAllTransactionsResponse>> getMyTransferHistory(@RequestParam String tcKimlik) {
        // İleride emaili doğrudan JWT'den çekeceğiz ki başkası başkasının geçmişini göremesin
       System.out.println(tcKimlik);
		List<GetAllTransactionsResponse> history = transactionService.getAllTransfersByTcKimlikNo(tcKimlik);
        return ResponseEntity.ok(history);
    }
   @GetMapping("/getAllTransactions")
   public ResponseEntity<List<GetAllTransactionsResponse>> getAllTransactions(@RequestParam int accountId ) {
       // İleride emaili doğrudan JWT'den çekeceğiz ki başkası başkasının geçmişini göremesin
      System.out.println(accountId);
		List<GetAllTransactionsResponse> history = transactionService.getAllTransfersByAccountId(accountId);
       return ResponseEntity.ok(history);
   }

}
