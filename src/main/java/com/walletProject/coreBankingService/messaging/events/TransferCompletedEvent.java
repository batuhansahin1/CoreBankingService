package com.walletProject.coreBankingService.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferCompletedEvent {
	private String transferReferanceId;
}
