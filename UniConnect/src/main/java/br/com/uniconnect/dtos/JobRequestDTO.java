package br.com.uniconnect.dtos;

import br.com.uniconnect.entities.EmploymentType;
import br.com.uniconnect.entities.Model;
import jakarta.validation.constraints.NotNull;

public record JobRequestDTO(
		@NotNull(message = "title cannot be null")
		String title, 
		@NotNull(message = "promoter cannot be null")
		String promoter, 
		@NotNull(message = "citycannot be null")
		String city, 
		@NotNull(message = "workStyle cannot be null")
		Model workStyle, 
		@NotNull(message = "employmentType cannot be null")
		EmploymentType employmentType,
		@NotNull(message = "salary cannot be null")
		Double salary, 
		@NotNull(message = "description cannot be null")
		String description,
		@NotNull(message = "state number cannot be null")
		Long stateId,
		@NotNull(message = "state name cannot be null")
		String stateName 
		) {
}
