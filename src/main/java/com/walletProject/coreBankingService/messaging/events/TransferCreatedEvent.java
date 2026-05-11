package com.walletProject.coreBankingService.messaging.events;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferCreatedEvent {
	// Transferin referans ID'si (Saga takibi için çok önemli)
    private String transferReferenceId; 
    private String senderIban;
    private String receiverIban;
    private BigDecimal amount;
}
