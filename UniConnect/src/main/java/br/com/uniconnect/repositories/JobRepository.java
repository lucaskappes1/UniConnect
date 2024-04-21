package br.com.uniconnect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.uniconnect.entities.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
	
	@Query("SELECT j FROM Job j ORDER BY j.jobDate DESC FETCH FIRST 10 ROWS ONLY")
	public List<Job> findTop10OrderByJobDateDesc();
	
	

}


