package br.com.uniconnect.dtos;

import br.com.uniconnect.entities.UserRole;

public record UserResponseDTO(
		String name, 
		String email, 
		Long id, 
		String phone,
		String adress,
		String city,
		Long stateId,
		UserRole role,
		byte[] profilePicture
		) {

}
