package br.com.uniconnect.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(
		@NotNull(message = "email cannot be null")
		@Email(message = "email must be valid")
		String email, 
		@NotNull(message = "password cannot be null")
		String password) {

}
