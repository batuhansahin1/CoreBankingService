package com.walletProject.coreBankingService.core.utilities.exceptions;

public class NoSuchCustomerExistsException extends RuntimeException {

	public NoSuchCustomerExistsException(String message) {
		super(message);
	}
}
