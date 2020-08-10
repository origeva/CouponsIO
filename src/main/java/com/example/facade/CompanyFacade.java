package com.example.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.bean.Category;
import com.example.bean.Company;
import com.example.bean.Coupon;
import com.example.exception.CouponNotFoundException;
import com.example.exception.CouponTitleAlreadyUsedException;

/**
 * The facade for companies to use.
 * @author Ori
 *
 */
@Service
@Scope("prototype")
public class CompanyFacade extends ClientFacade {
	
	private int id;
	
	public CompanyFacade(int id) {
		this.id = id;
	}
	
	/**
	 * Adds the coupon to the database, a company cannot have more than one coupon with a certain title.
	 * @param coupon Coupon's <code>Company</code> will be replaced with the logged in company details.
	 * @throws CouponTitleAlreadyUsedException if the <b>coupon</b>'s title was also used in another coupon by the same company.
	 */
	public void addCoupon(Coupon coupon) throws CouponTitleAlreadyUsedException {
		coupon.setCompanyId(getCompanyDetails().getId());
		for (Coupon couponByTitle : couponsRepo.findByTitle(coupon.getTitle())) {
			if (couponByTitle.getCompanyId() == id)
				throw new CouponTitleAlreadyUsedException();
		}
		couponsRepo.save(coupon);
	}
	
	/**
	 * Updates the coupon in the database according to the given coupon.
	 * @param coupon the coupon to be updated with the changed attributes.
	 * @throws CouponNotFoundException if the given coupon's id was not found in the database (the coupon does not exist).
	 * @throws CouponTitleAlreadyUsedException if the <b>coupon</b>'s title was also used in another coupon by the same company.
	 */
	public void updateCoupon(Coupon coupon) throws CouponNotFoundException, CouponTitleAlreadyUsedException {
		coupon.setCompanyId(getCompanyDetails().getId());
		Optional<Coupon> dbCoupon = couponsRepo.findById(coupon.getId());
		if (dbCoupon.isEmpty())
			throw new CouponNotFoundException();
		for (Coupon couponByTitle : couponsRepo.findByTitle(coupon.getTitle())) {
			System.out.println(couponByTitle);
			if (couponByTitle.getCompanyId() == id && couponByTitle.getId() != dbCoupon.get().getId())
				throw new CouponTitleAlreadyUsedException();
		}
		couponsRepo.save(coupon);
	}
	
	/**
	 * Deletes the coupon with the same id as the given <b>couponId</b>.
	 * @param couponId the if of the coupon to be deleted.
	 * @throws CouponNotFoundException if no coupon with the given id was found or the company is trying to delete a coupon that's not theirs.
	 */
	public void deleteCoupon(int couponId) throws CouponNotFoundException {
		Optional<Coupon> dbCoupon = couponsRepo.findById(couponId);
		if (dbCoupon.isEmpty() || dbCoupon.get().getCompanyId() != id)
			throw new CouponNotFoundException();
		couponsRepo.deletePurchases(couponId);
		couponsRepo.deleteById(couponId);
	}
	
	/**
	 * 
	 * @return a {@literal List<Coupon>} of all the coupons this company has added.<br>
	 * returns an empty {@literal List<Coupon>} if this company hasn't added any coupons.
	 */
	public List<Coupon> getCompanyCoupons() {
		return getCompanyDetails().getCoupons();
	}
	
	/**
	 * Gets and filters this company's coupons by the given category.
	 * @param category the category to have this customer's coupons filtered with.
	 * @return a {@literal List<Coupon>} of all the coupons this company has added that are in the given category.<br>
	 * returns an empty {@literal List<Coupon>} if this company hasn't added any coupons that are in the given category.
	 */
	public List<Coupon> getCompanyCoupons(Category category) {
		List<Coupon> couponsByCategory = new ArrayList<Coupon>();
		for (Coupon coupon : getCompanyCoupons()) {
			if (coupon.getCategory() == category)
				couponsByCategory.add(coupon);
		}
		return couponsByCategory;
	}
	
	/**
	 * Gets and filters this customer's coupons by the given max price.
	 * @param maxPrice the max price to have this company's coupons filtered with.
	 * @return a {@literal List<Coupon>} of all the coupons this company has added that their price is equal or less than the given max price.<br>
	 * returns an empty {@literal List<Coupon>} if this company hasn't added any coupons that their price is equal or less than the given max price.
	 */
	public List<Coupon> getCompanyCoupons(double maxPrice) {
		List<Coupon> couponsByMaxPrice = new ArrayList<Coupon>();
		for (Coupon coupon : getCompanyCoupons()) {
			if (coupon.getPrice() <= maxPrice)
				couponsByMaxPrice.add(coupon);
		}
		return couponsByMaxPrice;
	}
	
	/**
	 * 
	 * @return a <code>Company</code> object of the company logged in to <code>this</code> facade, with all the attributes representing it.
	 */
	public Company getCompanyDetails() {
		return companiesRepo.findById(id).get();
	}
	
}
