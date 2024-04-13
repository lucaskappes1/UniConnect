package br.com.uniconnect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uniconnect.entities.Enrollment;
import br.com.uniconnect.entities.EnrollmentId;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId>{

}
