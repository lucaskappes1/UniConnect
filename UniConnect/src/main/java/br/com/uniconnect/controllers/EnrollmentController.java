package br.com.uniconnect.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uniconnect.dtos.EnrollmentRequestDTO;
import br.com.uniconnect.dtos.EnrollmentUpdateDTO;
import br.com.uniconnect.entities.Enrollment;
import br.com.uniconnect.entities.EnrollmentId;
import br.com.uniconnect.entities.Job;
import br.com.uniconnect.entities.Status;
import br.com.uniconnect.entities.StatusJob;
import br.com.uniconnect.entities.User;
import br.com.uniconnect.exceptions.NonExistentOrInactiveJobException;
import br.com.uniconnect.repositories.EnrollmentRepository;
import br.com.uniconnect.repositories.JobRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/enrollment")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class EnrollmentController {
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	JobRepository jobRepository;
	
	@PostMapping
	public ResponseEntity postEnrollment(HttpServletRequest request, @RequestBody EnrollmentRequestDTO data) {
		User user = (User) request.getAttribute("user");
		Optional<Job> job = jobRepository.findById(data.id());
		if(job.isEmpty() || job.get().getStatus() == StatusJob.INATIVO) {
			throw new NonExistentOrInactiveJobException("esse job não existe ou está inativo");
		}
		EnrollmentId id = new EnrollmentId(user.getId(), data.id());
		Enrollment enrollment = new Enrollment(id, Status.AGUARDANDO);
		enrollmentRepository.save(enrollment);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity getEnrollmentData(HttpServletRequest request, @RequestBody EnrollmentRequestDTO data) {
		User user = (User) request.getAttribute("user");
		Optional<Enrollment> enrollment = enrollmentRepository.findById(new EnrollmentId(data.id(), user.getId()));
		return ResponseEntity.ok(enrollment);
	}
	
	@GetMapping("/list")
	public ResponseEntity getEnrollmentList(HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		List<Enrollment> enrollmentList = enrollmentRepository.findByUserId(user.getId());
		ArrayList<Job> responseList = new ArrayList<>();
		for(Enrollment e : enrollmentList) {
			responseList.add(jobRepository.findById(e.getId().getIdVaga()).get());
		}
		
		return ResponseEntity.ok(responseList);
		
		
	}
	
	@PatchMapping()
	public ResponseEntity updateEnrollment(@RequestBody EnrollmentUpdateDTO data) {
		Optional<Enrollment> enrollment = enrollmentRepository.findById(new EnrollmentId(data.jobId(), data.userId()));
		enrollment.get().setStatus(data.status());
		enrollmentRepository.save(enrollment.get());
		return ResponseEntity.ok().build();
	}
	
	
	
}