package br.com.uniconnect.dtos;

import br.com.uniconnect.entities.Model;

public record JobRequestUpdateDTO(Long jobId,
		String title, 
		String promoter, 
		Long idState,
		String text,
		String city, 
		Model workStyle, 
		Double salary, 
		String description
		) {

}
