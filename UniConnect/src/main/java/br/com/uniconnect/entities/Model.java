package br.com.uniconnect.entities;

public enum Model {

	REMOTO("remoto"),
	HIBRIDO("hibrido"),
	PRESENCIAL("presencial");
	
	private String model;
	
	Model(String model) {
		this.model = model;
	}
	
	public String getModel() {
		return this.model;
	}
}
