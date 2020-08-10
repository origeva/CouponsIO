package com.example.exception;

public class CouponNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CouponNotFoundException() {
		super("Coupon was not found!");
	}
	
}
