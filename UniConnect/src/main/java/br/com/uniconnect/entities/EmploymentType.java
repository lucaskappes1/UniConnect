package br.com.uniconnect.entities;

public enum EmploymentType {

	TRABALHO("trabalho"),
	ESTAGIO("estagio");
	
	private String employmentType;
	
	EmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	
	public String getModel() {
		return this.employmentType;
	}
}
