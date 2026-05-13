package com.walletProject.coreBankingService.messaging.eventListeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.walletProject.coreBankingService.business.abstracts.AccountService;
import com.walletProject.coreBankingService.messaging.events.TransferCompletedEvent;
import com.walletProject.coreBankingService.messaging.events.TransferCreatedEvent;
import com.walletProject.coreBankingService.messaging.events.TransferFailedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferEventHandler {

	private final RabbitTemplate rabbitTemplate;
	private final AccountService accountService;
	
	@Value("${rabbitmq.exchange.transfer}")
    private String exchange;

    @Value("${rabbitmq.routing.key.transfer-completed}")
    private String completedRoutingKey;
    
    @Value("${rabbitmq.routing.key.transfer-failed}")
    private String failedRoutingKey;
	// Kuyruk ismini application.properties'den dinamik olarak okuyoruz
    @RabbitListener(queues = "${rabbitmq.queue.transfer-created}")
    public void handleTransferCreated(TransferCreatedEvent event) {
        
        log.info("RabbitMQ'dan yeni bir transfer talebi yakalandı! Referans ID: {}", event.getTransferReferenceId());
        
        try {
            // İşlemi yapmak üzere servise gönder
            accountService.processTransfer(event);
            TransferCompletedEvent completedEvent = new TransferCompletedEvent(event.getTransferReferenceId());
            
            rabbitTemplate.convertAndSend(exchange, completedRoutingKey, completedEvent);
        } catch (Exception e) {
            // Eğer hesap yoksa veya bakiye yetersizse buraya düşer.
            log.error("Transfer işlenirken kritik hata! Referans ID: {}. Sebep: {}", 
                      event.getTransferReferenceId(), e.getMessage());
                      
            // İleride burada Transfer servisine "İşlem Başarısız (FAILED)" mesajı atacağız.
            TransferFailedEvent failedEvent = new TransferFailedEvent(event.getTransferReferenceId(), e.getMessage());
            rabbitTemplate.convertAndSend(exchange, failedRoutingKey, failedEvent);
            System.out.println("Hata mesajı Transfer servisine fırlatıldı.");
        }
    }
    
    
}
