package com.walletProject.coreBankingService.messaging.eventListeners;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.walletProject.coreBankingService.business.concretes.AccountManager;
import com.walletProject.coreBankingService.business.concretes.CustomerNumberGeneratorService;
import com.walletProject.coreBankingService.core.utilities.mappers.CustomerMapper;
import com.walletProject.coreBankingService.messaging.events.UserRegisteredEvent;
import com.walletProject.coreBankingService.models.entities.Customers;
import com.walletProject.coreBankingService.models.enums.CustomerStatus;
import com.walletProject.coreBankingService.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRegistrationListener {

    private final CustomerRepository customerRepository;
    private final AccountManager accountManager;
    private final CustomerNumberGeneratorService customerNumberGenerator;
	private final CustomerMapper customerMapper;
   
 
    @RabbitListener(queues = "user.registered.queue") 
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        
        
        Customers customer = this.customerMapper.createUserRegisteredEventToCustomer(event);
        
        
        customer.setStatus("ACTIVE");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCustomerNumber(customerNumberGenerator.generateCustomerNumberFromId(event.getTcKimlik()));
        
        Customers savedCustomer = customerRepository.save(customer);

       
        accountManager.createDefaultAccountForCustomer(savedCustomer);
        
        System.out.println("Yeni müşteri ve hesabı başarıyla oluşturuldu: " + event.getTcKimlik());
    }
}