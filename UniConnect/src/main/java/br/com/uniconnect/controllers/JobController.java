package br.com.uniconnect.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uniconnect.dtos.JobDeleteIdDTO;
import br.com.uniconnect.dtos.JobRequestDTO;
import br.com.uniconnect.dtos.JobRequestUpdateDTO;
import br.com.uniconnect.entities.Enrollment;
import br.com.uniconnect.repositories.EnrollmentRepository;
import br.com.uniconnect.repositories.JobRepository;
import br.com.uniconnect.services.JobService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/job")
//@CrossOrigin(origins = "*", maxAge = 3600, methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET, RequestMethod.PATCH})
public class JobController {
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	JobService jobService;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@GetMapping
	public ResponseEntity getJobData(@RequestBody Long id) {
		return ResponseEntity.ok(jobService.getJobData(id));
	}
	
	@GetMapping("/list")
	public ResponseEntity getJobList() {
		return ResponseEntity.ok(jobService.getJobList());
	}
	
	//apenas para admin
	@PostMapping("/new")
	public ResponseEntity createJob(@Valid @RequestBody JobRequestDTO jobRequest) {
		jobService.createJob(jobRequest);
		return ResponseEntity.ok().build();
	}
	
	//apenas para admin
	@PostMapping("/delete")
	public ResponseEntity deleteJob(@RequestBody JobDeleteIdDTO id) {
		jobRepository.deleteById(id.id());
		enrollmentRepository.deleteByJobId(id.id());
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/edit")
	public ResponseEntity deactivateJob(@RequestBody JobRequestUpdateDTO data) {
		jobService.editJob(data);
		return ResponseEntity.ok().build();
	}

}
