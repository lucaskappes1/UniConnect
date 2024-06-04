package br.com.uniconnect.dtos;

import br.com.uniconnect.entities.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
		@NotNull(message = "name cannot be null")
		String name,
		@NotNull(message = "email cannot be null")
		@Email(message = "email must be valid")
		String email, 
		@NotNull(message = "password cannot be null")
		String password,
		UserRole role, 
		@NotNull(message = "phone cannot be null")
		String phone, 
		@NotNull(message = "address cannot be null")
		String address,
		@NotNull(message = "citycannot be null")
		String city,
		@NotNull(message = "stateId cannot be null")
		Long stateId) {

}
