package br.com.uniconnect.entities;

import java.io.Serializable;

public class EnrollmentId implements Serializable{

	private Long idUsuario;
	private Long idVaga;
	
	public EnrollmentId() {
		
	}

	public EnrollmentId(Long idUsuario, Long idVaga) {
		this.idUsuario = idUsuario;
		this.idVaga = idVaga;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdVaga() {
		return idVaga;
	}

	public void setIdVaga(Long idVaga) {
		this.idVaga = idVaga;
	}
	
	
}
