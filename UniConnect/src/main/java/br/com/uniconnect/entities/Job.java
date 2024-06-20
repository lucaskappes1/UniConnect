package br.com.uniconnect.entities;

import java.time.LocalDate;

import br.com.uniconnect.dtos.StateIdDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long stateId;
	private String stateName;
	private String title;
	@Enumerated(EnumType.STRING)
	private Model workStyle;
	@Enumerated(EnumType.STRING)
	private EmploymentType employmentType;
	private String promoter;
	private Double salary;
	private String city;
	@Enumerated(EnumType.STRING)
	private StatusJob status;
	private String description;
	private LocalDate date;

	public Long getIdState() {
		return stateId;
	}

	public void setIdState(Long stateId) {
		this.stateId = stateId;
	}

	public Model getWorkStyle() {
		return workStyle;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setWorkStyle(Model workStyle) {
		this.workStyle = workStyle;
	}

	public Job(String title, String promoter, String city, Model workStyle,
			EmploymentType employmentType, Double salary, String description, Long stateId, String stateName) {
		this.title = title;
		this.stateId = stateId;
		this.promoter = promoter;
		this.city = city;
		this.workStyle = workStyle;
		this.salary = salary;
		this.description = description;
		this.status = StatusJob.ATIVO;
		this.employmentType = employmentType;
		this.stateName = stateName;
	}

	@PrePersist
	protected void onCreate() {
		date = LocalDate.now();
	}

	// getters and setters
	public Long getId() {
		return id;
	}

	public EmploymentType getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(EmploymentType employmentType) {
		this.employmentType = employmentType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPromoter() {
		return promoter;
	}

	public void setPromoter(String promoter) {
		this.promoter = promoter;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StatusJob getStatus() {
		return status;
	}

	public void setStatus(StatusJob status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Job() {

	}

}
