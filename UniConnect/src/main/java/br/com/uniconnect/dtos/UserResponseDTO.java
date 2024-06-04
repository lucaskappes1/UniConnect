package br.com.uniconnect.dtos;

import java.util.List;

public record UserResponseDTO(
		String name, 
		String email, 
		Long id, 
		String phone,
		String adress,
		String city,
		Long stateId,
		byte[] profilePicture
		) {

}
