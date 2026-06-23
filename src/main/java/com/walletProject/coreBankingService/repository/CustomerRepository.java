package com.walletProject.coreBankingService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walletProject.coreBankingService.models.entities.Customers;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Integer> {

	boolean existsByTcKimlikNo(String tcKimlik);

	Customers findByTcKimlikNo(String tcKimlik);

	Customers findByCustomerNumber(String customerNumber);

	boolean existsByCustomerNumber(String customerNumber);
	
	

}
