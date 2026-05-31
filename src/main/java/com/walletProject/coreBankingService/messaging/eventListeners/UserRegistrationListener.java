package com.walletProject.coreBankingService.messaging.eventListeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.walletProject.coreBankingService.business.concretes.AccountManager;
import com.walletProject.coreBankingService.messaging.events.UserRegisteredEvent;
import com.walletProject.coreBankingService.models.entities.Customers;
import com.walletProject.coreBankingService.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRegistrationListener {

    private final CustomerRepository customerRepository;
    private final AccountManager accountManager;

    // Constructor injection...

    @RabbitListener(queues = "user.registered.queue") // Kuyruk adını kendi ayarlarına göre düzenle
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        
        // 1. Gelen veriyle önce Müşteriyi (Customer) oluştur
        Customers customer = new Customers();
        customer.setTcKimlikNo(event.getTcKimlik());
        customer.setFirstName(event.getFirstName());
        customer.setLastName(event.getLastName());
        customer.setType(event.getCustomerType());
        // ... diğer alanlar
        
        Customers savedCustomer = customerRepository.save(customer);

        // 2. Müşteri oluştuğuna göre, onun ID'si ile yeni bir Banka Hesabı (Account) aç
        // Bu metodu AccountManager içinde yazmış olmalısın (Varsayılan bakiye 0, yeni IBAN üretimi vb.)
        accountManager.createDefaultAccountForCustomer(savedCustomer);
        
        System.out.println("Yeni müşteri ve hesabı başarıyla oluşturuldu: " + event.getTcKimlik());
    }
}