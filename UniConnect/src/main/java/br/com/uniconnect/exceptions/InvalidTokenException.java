package br.com.uniconnect.exceptions;

public class InvalidTokenException extends RuntimeException{

	public InvalidTokenException(String message) {
		super(message);
	}
}
