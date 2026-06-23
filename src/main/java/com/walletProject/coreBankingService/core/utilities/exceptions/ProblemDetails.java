package com.walletProject.coreBankingService.core.utilities.exceptions;

import java.time.LocalDateTime;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProblemDetails {

    private String type;
    private String title;
    private String detail;
    private int status;
    private LocalDateTime timestamp;
}
