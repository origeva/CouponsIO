package com.example.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.bean.Category;
import com.example.bean.Coupon;
import com.example.bean.Customer;
import com.example.exception.CouponAlreadyPurchasedException;
import com.example.exception.CouponExpiredException;
import com.example.exception.CouponNotFoundException;
import com.example.exception.InvalidAmountException;

/**
 * The facade for customers to use.
 * @author Ori
 *
 */
@Service
@Scope("prototype")
public class CustomerFacade extends ClientFacade {
	
	private int id;
	
	public CustomerFacade(int id) {
		this.id = id;
	}
	
	/**
	 * Adds the coupon purchase to the database and decreases the coupon's amount by one.
	 * @param couponId the id of the coupon to be purchased.
	 * @throws CouponNotFoundException if the given id of the coupon was not found.
	 * @throws InvalidAmountException if the amount of coupons is 0.
	 * @throws CouponExpiredException if the coupon's end date has already passed.
	 * @throws CouponAlreadyPurchasedException if the customer tries purchasing the same coupon more than once.
	 */
	public void purchaseCoupon(int couponId) throws CouponNotFoundException, InvalidAmountException, CouponExpiredException, CouponAlreadyPurchasedException {
		// Working with the database coupon in case the company updates the coupons after the customer gets it.
		Coupon dbCoupon = couponsRepo.findById(couponId).orElseThrow(CouponNotFoundException::new);
		if (dbCoupon.getAmount() < 1)
			throw new InvalidAmountException();
		if (dbCoupon.getEndDate().before(new Date(System.currentTimeMillis())))
			throw new CouponExpiredException(dbCoupon.getTitle());
		Customer customer = getCustomerDetails();
		for (Coupon purchasedCoupon : customer.getCoupons()) {
			if (purchasedCoupon.getId() == dbCoupon.getId())
				throw new CouponAlreadyPurchasedException(dbCoupon.getTitle());
		}
		dbCoupon.setAmount(dbCoupon.getAmount() - 1);
		couponsRepo.save(dbCoupon);
		customer.getCoupons().add(dbCoupon);
		customersRepo.save(customer);
	}
	
	/**
	 * 
	 * @return a {@literal List<Coupon>} of all the coupons this customer has purchased.<br>
	 * returns an empty {@literal List<Coupon>} if this customer hasn't purchased any coupons.
	 */
	public List<Coupon> getCustomerCoupons() {
		return getCustomerDetails().getCoupons();
	}
	
	/**
	 * Gets and filters this customer's coupons by the given category.
	 * @param category the category to have this customer's coupons filtered with.
	 * @return a {@literal List<Coupon>} of all the coupons this customer has purchased that are in the given category.<br>
	 * returns an empty {@literal List<Coupon>} if this customer hasn't purchased any coupons in the given category.
	 */
	public List<Coupon> getCustomerCoupons(Category category) {
		List<Coupon> couponsByCategory = new ArrayList<Coupon>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getCategory() == category)
				couponsByCategory.add(coupon);
		}
		return couponsByCategory;
	}
	
	/**
	 * Gets and filters this customer's coupons by the given max price.
	 * @param maxPrice the max price to have this customer's coupons filtered with.
	 * @return a {@literal List<Coupon>} of all the coupons this customer has purchased that their price is equal or below the given max price.<br>
	 * returns an empty {@literal List<Coupon>} if this customer hasn't purchased any coupons that their price is equal or below the given max price.
	 */
	public List<Coupon> getCustomerCoupons(double maxPrice) {
		List<Coupon> couponsByMaxPrice = new ArrayList<Coupon>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getPrice() <= maxPrice)
				couponsByMaxPrice.add(coupon);
		}
		return couponsByMaxPrice;
	}
	
	/**
	 * @return a <code>Customer</code> object of the customer logged in to <code>this</code> facade, with all the attributes representing them.
	 */
	public Customer getCustomerDetails() {
		return customersRepo.findById(id).get();
	}
	
}
