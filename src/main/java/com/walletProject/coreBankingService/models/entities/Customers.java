package com.walletProject.coreBankingService.models.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Customers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String customerNumber;
	private String firstName;
	private String lastName;
	private String tcKimlikNo;
	//türü olacak
	private String type;
	private String kycStatus;
	private String status;
	@OneToMany(mappedBy = "customer")
	private List<Accounts> accounts;
	private LocalDateTime createdAt;
	private LocalDateTime updateAt;
}
