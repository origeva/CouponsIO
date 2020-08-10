package com.example.exception;

public class CouponExpiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CouponExpiredException() {
		super("The coupon has already expired and cannot be purchased!");
	}
	
	public CouponExpiredException(String title) {
		super(title + " has already expired and cannot be purchased!");
	}
	
}
