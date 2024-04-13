package br.com.uniconnect.dtos;

import br.com.uniconnect.entities.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {

}
