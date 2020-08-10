package com.example.jobs;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bean.Coupon;
import com.example.repositories.CouponRepository;

/**
 * Implements <code>Runnable</code><br>
 * the run() method checks the database every 24 hours for any expired coupons and deletes them from the database.
 * @author Ori
 *
 */
@Service
public class CouponExpirationDailyJob implements Runnable {
	
	private boolean quit;
	
	@Autowired
	CouponRepository couponsRepo;
	
	@Override
	public void run() {
		
		while(!quit) {
			
			Date nowDate = new Date(System.currentTimeMillis());
			List<Coupon> allCoupons = couponsRepo.findAll();
			for (Coupon coupon : allCoupons) {
				if (nowDate.after(coupon.getEndDate())) {
					couponsRepo.deletePurchases(coupon.getId());
					couponsRepo.deleteById(coupon.getId());
				}
			}
			
			try {
				Thread.sleep(1000 * 60 * 60 * 24);
//				Thread.sleep(30000); // Don't forget to comment out job.quit() on the main method for testing.
			} catch (InterruptedException e) {}
			
		}
		
	}
	
	public void quit() {
		quit = true;
	}
	
}
