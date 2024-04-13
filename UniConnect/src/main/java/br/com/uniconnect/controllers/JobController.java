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

import br.com.uniconnect.dtos.JobIdDTO;
import br.com.uniconnect.dtos.JobRequestDTO;
import br.com.uniconnect.entities.Job;
import br.com.uniconnect.repositories.JobRepository;


@RestController
@RequestMapping("/job")
public class JobController {
	
	@Autowired
	JobRepository jobRepository;
	
	@GetMapping
	public ResponseEntity getJobData(@RequestBody JobIdDTO jobId) {
		Optional<Job> job = jobRepository.findById(jobId.id());
		return ResponseEntity.ok(job);
	}
	
	@GetMapping("/list")
	public ResponseEntity getJobList() {
		List<Job> list = jobRepository.findAll();
		return ResponseEntity.ok(list);
	}
	
	@PostMapping
	public ResponseEntity createJob(@RequestBody JobRequestDTO jobRequest) {
		
		Job job= new Job(jobRequest.type(), jobRequest.promoter(), jobRequest.city(),
				jobRequest.model(), jobRequest.salary(), jobRequest.description());
		jobRepository.save(job);
		return ResponseEntity.ok().build();
	}

}

