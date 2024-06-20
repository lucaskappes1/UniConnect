package br.com.uniconnect.dtos;

import br.com.uniconnect.entities.Model;

public record JobRequestUpdateDTO(
		//id é utilizado para encontrar o job no banco de dados
		//portanto ele nunca poderá ser alterado, visto que ele 
		//será sempre o identificador para encontrar o job no 
		//banco de dados, e nunca um valor a ser alterado
		Long jobId,
		String title, 
		String promoter, 
		Long stateId,
		String stateName,
		String city, 
		Model workStyle, 
		Double salary, 
		String description
		
		) {

}
