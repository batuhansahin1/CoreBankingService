package com.walletProject.coreBankingService.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferFailedEvent {

	private String transferReferenceId;
    private String errorMessage;
}
