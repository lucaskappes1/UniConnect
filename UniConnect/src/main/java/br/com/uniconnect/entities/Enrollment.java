package br.com.uniconnect.entities;

import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;

@Entity
public class Enrollment {

	
	@EmbeddedId
	private EnrollmentId id;
	@Enumerated(EnumType.STRING)
	private Status status;
	private LocalDate enrollmentDate;
	
	public Enrollment() {
		
	}
	
	public Enrollment(EnrollmentId id, Status status) {
		this.id = id;
		this.status = status;
	}
	
	@PrePersist
	protected void onCreate() {
		enrollmentDate = LocalDate.now();
	}
	
	public EnrollmentId getId() {
		return id;
	}

	public void setId(EnrollmentId id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

}
