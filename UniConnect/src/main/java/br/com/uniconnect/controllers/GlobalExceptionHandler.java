package br.com.uniconnect.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.uniconnect.exceptions.InvalidTokenException;
import br.com.uniconnect.exceptions.NonExistentOrInactiveJobException;

@ControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler({InvalidTokenException.class})
	    public ResponseEntity handleInvalidTokenException(InvalidTokenException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	    }
	 
	 @ExceptionHandler({NonExistentOrInactiveJobException.class})
	 public ResponseEntity handleNonExistentOrInactiveJobException(NonExistentOrInactiveJobException ex) {
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	 }
	 
	 
}
