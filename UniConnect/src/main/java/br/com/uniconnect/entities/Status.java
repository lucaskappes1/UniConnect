package br.com.uniconnect.entities;

public enum Status {

	REJEITADO("rejeitado"),
	CANCELADO("cancelado"),
	AGUARDANDO("aguardando"),
	ACEITO("aceito");
	
	private String status;
	
	Status(String status) {
		this.status= status;
	}
	
	public String getModel() {
		return this.status;
	}
}
