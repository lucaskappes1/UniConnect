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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.uniconnect.dtos.JobDeleteIdDTO;
import br.com.uniconnect.dtos.JobRequestDTO;
import br.com.uniconnect.dtos.JobResponseDTO;
import br.com.uniconnect.dtos.StateIdDTO;
import br.com.uniconnect.entities.Job;
import br.com.uniconnect.entities.StatusJob;
import br.com.uniconnect.repositories.JobRepository;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/job")
@CrossOrigin(origins = "*", maxAge = 3600, methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET})
public class JobController {
	
	@Autowired
	JobRepository jobRepository;
	
	@GetMapping
	public ResponseEntity getJobData(@RequestBody Long id) {
		Optional<Job> oJob = jobRepository.findById(id);
		Job job = oJob.get();
		return ResponseEntity.ok(new JobResponseDTO(
				job.getId(),
				job.getTitle(),
				job.getWorkStyle(),
				job.getEmploymentType(),
				job.getDescription(),
				job.getPromoter(),
				job.getSalary(),
				job.getCity(),
				new StateIdDTO(job.getIdState(), job.getStateName()),
				job.getDate()
				));
	}
	
	@GetMapping("/list")
	public ResponseEntity getJobList() {
		List<Job> repoList = jobRepository.findAll();
		List<JobResponseDTO> responseList = new ArrayList<>();
		for(Job job : repoList) {
			if(job.getStatus() == StatusJob.ATIVO) {
				responseList.add(new JobResponseDTO(
						job.getId(),
						job.getTitle(),
						job.getWorkStyle(),
						job.getEmploymentType(),
						job.getDescription(),
						job.getPromoter(),
						job.getSalary(),
						job.getCity(),
						new StateIdDTO(job.getIdState(), job.getStateName()),
						job.getDate()
					));
			}
		}
		return ResponseEntity.ok(responseList);
	}
	
	//apenas para admin
	@PostMapping
	public ResponseEntity createJob(@Valid @RequestBody JobRequestDTO jobRequest) {
		Job job = new Job(
				jobRequest.title(),
				jobRequest.promoter(),
				jobRequest.city(),
				jobRequest.workStyle(),
				jobRequest.employmentType(),
				jobRequest.salary(),
				jobRequest.description(),
				jobRequest.stateId(),
				jobRequest.stateName()
				);
		jobRepository.save(job);
		return ResponseEntity.ok().build();
	}
	
	//apenas para admin
	@PostMapping("/delete")
	public ResponseEntity deleteJob(@RequestBody JobDeleteIdDTO id) {
		jobRepository.deleteById(id.id());
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping
	public ResponseEntity deactivateJob(@RequestBody Long id) {
		Optional<Job> oJob = jobRepository.findById(id);
		oJob.get().setStatus(StatusJob.INATIVO);
		jobRepository.save(oJob.get());
		return ResponseEntity.ok().build();
	}
	
	/*@PatchMapping
	public ResponseEntity updateJob(@RequestBody JobRequestUpdateDTO data) {
		Optional<Job> job = jobRepository.findById(data.jobId());
	}*/

}
