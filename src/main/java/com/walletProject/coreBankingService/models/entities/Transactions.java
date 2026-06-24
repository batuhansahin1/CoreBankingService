package com.walletProject.coreBankingService.models.entities;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import com.walletProject.coreBankingService.models.enums.TransactionType;
import com.walletProject.coreBankingService.models.enums.TransferStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Accounts account;
    //transfer serviceden gelecek id dış servis
    private String referanceId;
    //Enum olacak(debit,credit) bu businessta belli olacak 
    @Enumerated(EnumType.STRING)
	private TransactionType type;
	
	
	private BigDecimal amount;
	private String currency;
	//enum olacak pending,completed failed
	private TransferStatus status;
	private LocalDateTime createdAt;
}
