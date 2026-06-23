package com.walletProject.coreBankingService.business.concretes;

import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class CustomerNumberGeneratorService {

    /**
     * TCKN'yi kullanarak, KVKK'ya uygun 8 haneli müşteri numarası üretir.
     * TCKN geri döndürülemez şekilde şifrelenir (SHA-256).
     * Son hane bankacılık standartları gereği Luhn doğrulama basamağıdır.
     */
    public String generateCustomerNumberFromId(String tcKimlik) {
        try {
            // 1. TCKN'yi SHA-256 algoritmasıyla şifrele (Geri döndürülemez)
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(tcKimlik.getBytes());

            // 2. Hashlenmiş verinin ilk birkaç byte'ını alarak pozitif matematiksel bir sayıya çevir
            long numericValue = Math.abs(
                    ((long)(hash[0] & 0xFF) << 24) |
                    ((long)(hash[1] & 0xFF) << 16) |
                    ((long)(hash[2] & 0xFF) << 8)  |
                    ((long)(hash[3] & 0xFF))
            );

            // 3. Çıkan rastgele ama tutarlı sayıyı 7 haneli olacak şekilde sınırla
            String baseNumber = String.format("%07d", numericValue % 10000000);

            // 4. Sonuna Luhn kontrol basamağını ekle (Toplam 8 hane)
            int checkDigit = calculateLuhnCheckDigit(baseNumber);
            
            return baseNumber + checkDigit;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algoritması sistemde bulunamadı!", e);
        }
    }

    /**
     * Luhn Algoritması (Doğrulama Basamağı)
     */
    private int calculateLuhnCheckDigit(String baseNumber) {
        int sum = 0;
        boolean alternate = true;
        for (int i = baseNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(baseNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }
}
