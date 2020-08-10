package com.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bean.Coupon;
import com.example.repositories.CouponRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
	
	@Autowired
	CouponRepository couponsRepo;
	
	@GetMapping("/allcoupons")
	public List<Coupon> getAllCoupons() {
		return couponsRepo.findAll();
	}
	
}
