package br.com.uniconnect.entities;

public enum StatusJob {
	
	ATIVO("ativo"),
	INATIVO("inativo");
	
	private String statusJob;
	
	StatusJob(String statusJob) {
		this.statusJob = statusJob;
	}
	
	public String getStatusJob() {
		return this.statusJob;
	}

}
