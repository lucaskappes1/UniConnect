package br.com.uniconnect.dtos;

import java.time.LocalDate;

import br.com.uniconnect.entities.EmploymentType;
import br.com.uniconnect.entities.Model;

public record JobResponseDTO(
		Long id, 
		String title,
		Model workStyle,
		EmploymentType employmentType,
		String description,
		String promoter,
		Double salary,
		String city, 
		StateIdDTO state,
		LocalDate date
		) {

}
