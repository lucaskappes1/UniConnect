package br.com.uniconnect.dtos;

import br.com.uniconnect.entities.Status;

public record EnrollmentUpdateDTO(Long jobId, Long userId, Status status) {

}
