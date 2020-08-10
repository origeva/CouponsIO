package com.example.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bean.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	List<Coupon> findByTitle(String title);
	
	List<Coupon> findByTitleContains(String title);
	
	List<Coupon> findByTitleLike(String title);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM purchases WHERE coupons_id = ?1", nativeQuery = true)
	void deletePurchases(int couponId);
	
}
