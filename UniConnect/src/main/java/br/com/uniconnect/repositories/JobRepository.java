package br.com.uniconnect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.uniconnect.entities.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

	
	

}


