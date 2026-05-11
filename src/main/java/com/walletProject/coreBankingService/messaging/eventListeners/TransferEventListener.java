package com.walletProject.coreBankingService.messaging.eventListeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.walletProject.coreBankingService.business.abstracts.AccountService;
import com.walletProject.coreBankingService.messaging.events.TransferCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferEventListener {

	private final AccountService accountService;
	
	// Kuyruk ismini application.properties'den dinamik olarak okuyoruz
    @RabbitListener(queues = "${rabbitmq.queue.transfer-created}")
    public void handleTransferCreated(TransferCreatedEvent event) {
        
        log.info("RabbitMQ'dan yeni bir transfer talebi yakalandı! Referans ID: {}", event.getTransferReferenceId());
        
        try {
            // İşlemi yapmak üzere servise gönder
            accountService.processTransfer(event);
        } catch (Exception e) {
            // Eğer hesap yoksa veya bakiye yetersizse buraya düşer.
            log.error("Transfer işlenirken kritik hata! Referans ID: {}. Sebep: {}", 
                      event.getTransferReferenceId(), e.getMessage());
                      
            // İleride burada Transfer servisine "İşlem Başarısız (FAILED)" mesajı atacağız.
        }
    }
}
