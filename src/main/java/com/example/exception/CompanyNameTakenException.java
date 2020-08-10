package com.example.exception;

public class CompanyNameTakenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CompanyNameTakenException() {
		super("The name used was taken by another company!");
	}
	
	public CompanyNameTakenException(String name) {
		super(name + " was taken by another company!");
	}
	
}
