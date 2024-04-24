package br.com.uniconnect.dtos;

import br.com.uniconnect.entities.UserRole;

public record UserDataDTO(
Long id, 
String email, 
String name, 
String telefone, 
String logradouro,
String numero,
String bairro,
String cidade,
String estado,
String curso,
byte[] imageData) {
	
}

	
