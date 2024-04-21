package br.com.uniconnect.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uniconnect.dtos.EnrollmentRequestDTO;
import br.com.uniconnect.dtos.EnrollmentUpdateDTO;
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
	public ResponseEntity postEnrollment(@RequestBody EnrollmentRequestDTO data) {
		EnrollmentId id = new EnrollmentId(data.userId(), data.jobId());
		Enrollment enrollment = new Enrollment(id, Status.AGUARDANDO);
		enrollmentRepository.save(enrollment);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity getEnrollmentData(@RequestBody EnrollmentRequestDTO data) {
		Optional<Enrollment> enrollment = enrollmentRepository.findById(new EnrollmentId(data.jobId(), data.userId()));
		return ResponseEntity.ok(enrollment);
	}
	
	@GetMapping("/list")
	public ResponseEntity getEnrollmentList() {
		List<Enrollment> enrollmentList = enrollmentRepository.findAll();
		return ResponseEntity.ok(enrollmentList);
	}
	
	@PostMapping("/update")
	public ResponseEntity updateEnrollment(@RequestBody EnrollmentUpdateDTO data) {
		Optional<Enrollment> enrollment = enrollmentRepository.findById(new EnrollmentId(data.jobId(), data.userId()));
		enrollment.get().setStatus(data.status());
		enrollmentRepository.save(enrollment.get());
		return ResponseEntity.ok().build();
	}
	
	
	
}