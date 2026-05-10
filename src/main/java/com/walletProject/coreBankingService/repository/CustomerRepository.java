package com.walletProject.coreBankingService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walletProject.coreBankingService.models.entities.Customers;

public interface CustomerRepository extends JpaRepository<Customers, Integer> {

}
