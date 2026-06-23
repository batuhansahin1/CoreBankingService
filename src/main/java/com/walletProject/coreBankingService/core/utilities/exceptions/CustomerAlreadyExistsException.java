package com.walletProject.coreBankingService.core.utilities.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException{
	
	public CustomerAlreadyExistsException(String message) {
		super(message);
	}

}
