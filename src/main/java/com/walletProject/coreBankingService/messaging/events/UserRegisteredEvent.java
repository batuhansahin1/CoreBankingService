package com.walletProject.coreBankingService.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Her iki servise de bu sınıfı ekle (Örn: UserRegisteredEvent.java)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredEvent {
 private String tcKimlik;
 private String firstName;
 private String lastName;
 private String email;
 private String customerType; // "BIREYSEL" veya "KURUMSAL"
}
