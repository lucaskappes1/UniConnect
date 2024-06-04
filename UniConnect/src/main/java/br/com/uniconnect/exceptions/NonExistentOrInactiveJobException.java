package br.com.uniconnect.exceptions;

public class NonExistentOrInactiveJobException extends RuntimeException{

	public NonExistentOrInactiveJobException(String message) {
		super(message);
	}
}
