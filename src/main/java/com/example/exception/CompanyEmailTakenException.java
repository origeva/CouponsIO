package com.example.exception;

public class CompanyEmailTakenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CompanyEmailTakenException() {
		super("The email used was taken by another company!");
	}
	
	public CompanyEmailTakenException(String email) {
		super(email + " was taken by another company!");
	}

}
