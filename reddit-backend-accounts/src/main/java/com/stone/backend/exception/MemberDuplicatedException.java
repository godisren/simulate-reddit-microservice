package com.stone.backend.exception;

public class MemberDuplicatedException extends RuntimeException{

	public MemberDuplicatedException() {
		super("the member has already registed.");
	}
	
	
}
