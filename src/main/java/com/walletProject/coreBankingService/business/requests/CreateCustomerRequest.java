package com.walletProject.coreBankingService.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {

	@NotBlank(message = "İsim boş bırakılamaz")
	private String firstName;
	@NotBlank(message = "Soyisim boş bırakılamaz")
	private String lastName;
	@NotBlank(message = "TC kimlik no boş bırakılamaz")
    @Size(min = 11, max = 11, message = "TC kimlik no 11 karakter olmalıdır")
	private String tcKimlikNo;
	//türü olacak
	@NotBlank(message = "Müşteri tiği boş bırakılamaz")
	private String type;
}
