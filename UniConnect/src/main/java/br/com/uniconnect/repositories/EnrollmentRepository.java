package br.com.uniconnect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.uniconnect.entities.Enrollment;
import br.com.uniconnect.entities.EnrollmentId;
import jakarta.transaction.Transactional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId>{

	@Modifying
    @Transactional
    @Query("DELETE FROM Enrollment e WHERE e.id.idVaga = :jobId")
    void deleteByJobId(@Param("jobId") Long jobId);
	
	@Query("SELECT e FROM Enrollment e WHERE e.id.idUsuario = :userId")
    List<Enrollment> findByUserId(@Param("userId") Long userId);
}
