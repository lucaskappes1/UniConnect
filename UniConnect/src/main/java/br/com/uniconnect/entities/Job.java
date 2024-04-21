package br.com.uniconnect.entities;

import java.time.LocalDate;

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
	private String type;
	private String promoter;
	private String city;
	@Enumerated(EnumType.STRING)
	private Model model;
	private Double salary;
	private String description;
	private LocalDate jobDate;
	
	
	
	public Job(String type, String promoter, String city, Model model, Double salary, String description) {
		this.type = type;
		this.promoter = promoter;
		this.city = city;
		this.model = model;
		this.salary = salary;
		this.description = description;
	}
	
	@PrePersist
	protected void onCreate() {
		jobDate = LocalDate.now();
	}
	
	//getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
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
	
	public LocalDate getJobDate() {
		return jobDate;
	}

	public void setJobDate(LocalDate jobDate) {
		this.jobDate = jobDate;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Job() {
		
	}

	
}
