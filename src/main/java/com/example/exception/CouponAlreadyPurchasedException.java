package com.example.exception;

public class CouponAlreadyPurchasedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CouponAlreadyPurchasedException() {
		super("The coupon was already purchased! You can only purchase a certain coupon once!");
	}
	
	public CouponAlreadyPurchasedException(String title) {
		super(title + " was already purchased! You can only purchase a certain coupon once!");
	}
	
}
