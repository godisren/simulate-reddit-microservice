package com.stone.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// global REST controller exception handler
@ControllerAdvice
public class MyExceptionController {
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<Object> runtimeException(RuntimeException exception) {
		return new ResponseEntity<>(new GenericExceptionResponse("runtime exception occured"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = MemberDuplicatedException.class)
	public ResponseEntity<Object> memberDuplicatedException(MemberDuplicatedException exception) {
		return new ResponseEntity<>(new GenericExceptionResponse(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException exception) {
		return new ResponseEntity<>(new GenericExceptionResponse(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
