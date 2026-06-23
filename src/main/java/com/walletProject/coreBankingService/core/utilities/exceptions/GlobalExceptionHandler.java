package com.walletProject.coreBankingService.core.utilities.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.walletProject.coreBankingService.core.utilities.generics.ResponseEntity;

import io.swagger.v3.oas.annotations.Hidden;





@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

	
	
	@ExceptionHandler(exception = BusinessException.class)
	public ResponseEntity<ProblemDetails> getException(BusinessException be) {
		
				ProblemDetails pd= new ProblemDetails("https://example.com/problem/business-error",  
	            "İş kuralı hatası",
	            be.getMessage(),
	            HttpStatus.BAD_REQUEST.value(),
	            LocalDateTime.now()); 
				return  new ResponseEntity<ProblemDetails>(pd);
	}
	
	@ExceptionHandler(exception = CustomerAlreadyExistsException.class)
	public @ResponseBody ErrorResponse customerAlreadyExists(CustomerAlreadyExistsException exception) {
		
		return new ErrorResponse(LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),exception.getMessage()
				,"api/v1/customers/add-customer/");
	}
	@ExceptionHandler(exception = NoSuchCustomerExistsException.class)
	public ErrorResponse noCustomerExists(NoSuchCustomerExistsException nspee) {
		return new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value()
				,HttpStatus.BAD_REQUEST.getReasonPhrase(),nspee.getMessage(),"api/v1/customers");
	}
}
