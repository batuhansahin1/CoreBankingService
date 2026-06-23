package com.walletProject.coreBankingService.core.utilities.exceptions;

import java.time.LocalDateTime;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
	
 private LocalDateTime timeStamp;
 private int statusCode;
 private String error;
 private String message;
 private String path;
}
