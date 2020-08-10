package com.example.exception;

public class CustomerEmailTakenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomerEmailTakenException() {
		super("The email used was taken by another customer!");
	}
	
	public CustomerEmailTakenException(String email) {
		super(email + " was taken by another customer!");
	}
	
}
