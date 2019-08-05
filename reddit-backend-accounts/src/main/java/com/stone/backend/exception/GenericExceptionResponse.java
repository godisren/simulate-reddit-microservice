package com.stone.backend.exception;

public class GenericExceptionResponse {
	private String message;

	public GenericExceptionResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
