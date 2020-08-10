package com.example.exception;

public class CouponTitleAlreadyUsedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CouponTitleAlreadyUsedException() {
		super("Your company already has a coupon with the same title!");
	}
	
}
