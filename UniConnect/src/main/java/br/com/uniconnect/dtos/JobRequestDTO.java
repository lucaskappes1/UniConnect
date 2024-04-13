package br.com.uniconnect.dtos;

import br.com.uniconnect.entities.Model;

public record JobRequestDTO(String type, String promoter, String city, Model model, Double salary, String description) {

}
