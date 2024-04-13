package br.com.uniconnect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uniconnect.dtos.EnrollmentDTO;
import br.com.uniconnect.entities.Enrollment;
import br.com.uniconnect.entities.EnrollmentId;
import br.com.uniconnect.entities.Status;
import br.com.uniconnect.repositories.EnrollmentRepository;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@PostMapping
	public ResponseEntity postEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
		EnrollmentId id = new EnrollmentId(enrollmentDTO.userId(), enrollmentDTO.jobId());
		Enrollment enrollment = new Enrollment(id, Status.AGUARDANDO);
		enrollmentRepository.save(enrollment);
		
		return ResponseEntity.ok().build();
	}
}