package com.walletProject.coreBankingService.models.entities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accounts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	
	//default değer
	private BigDecimal balance;
	//default değer
	private BigDecimal availableBalance;
	//biz oluşturacağız
	private String ibanNumber;
	//biz oluşturacağız
	private String accountNumber;
	
	private String currency;
	private LocalDateTime createdAt;

	//optimistic lock sürümü
	private int version;
	
	//ACTIVE, FROZEN, CLOSED
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "cuatomer_id")
	private Customers customer;
	@OneToMany(mappedBy = "account")
	private List<Transactions> transactionList;
	 
}
