package com.walletProject.coreBankingService.models.entities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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
	private int id;
	
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts account;
    //transfer serviceden gelecek id dış servis
    private String referanceId;
    //Enum olacak(debit,credit) bu businessta belli olacak 
	private String type;
	
	
	private BigDecimal amount;
	private String currency;
	//enum olacak pending,completed failed
	private String status;
	private LocalDateTime createdAt;
}
