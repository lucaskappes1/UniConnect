package br.com.uniconnect.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uniconnect.dtos.JobRequestDTO;
import br.com.uniconnect.dtos.JobRequestUpdateDTO;
import br.com.uniconnect.dtos.JobResponseDTO;
import br.com.uniconnect.dtos.StateIdDTO;
import br.com.uniconnect.entities.Job;
import br.com.uniconnect.entities.StatusJob;
import br.com.uniconnect.repositories.JobRepository;

@Service
public class JobService {
	
	@Autowired
	private JobRepository jobRepository;

	public JobResponseDTO getJobData(Long id) {
		Optional<Job> oJob = jobRepository.findById(id);
		Job job = oJob.get();
		return new JobResponseDTO(
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
				);
	}
	
	public List<JobResponseDTO> getJobList() {
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
		return responseList;
	}
	
	public boolean createJob(JobRequestDTO jobRequest) {
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
		return true;
	}
	
	public boolean editJob(JobRequestUpdateDTO data) {
		Optional<Job> optionalJob = jobRepository.findById(data.jobId());
		if(optionalJob.isEmpty()) {
			throw new RuntimeException("id do job não existe");
		}
		Job job = optionalJob.get();
		
		try {
			//passa por todos os itens do JobRequestUpdateDTO que possuem algum valor. Ignora os que são null
			for(java.lang.reflect.Field field : JobRequestUpdateDTO.class.getDeclaredFields()) {
				field.setAccessible(true);
				Object value = field.get(data);
				if(value != null) {
					BeanUtils.setProperty(job, field.getName(), value);
				}
			}
		} catch(IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Failed to update job: ", e);
		}
		jobRepository.save(job);
		return true;
	}
}
