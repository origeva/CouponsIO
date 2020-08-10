package com.example.exception;

public class InvalidAmountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidAmountException() {
		super("There are no more coupons left to purchase :/");
	}
	
}
