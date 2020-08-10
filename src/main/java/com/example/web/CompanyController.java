package com.example.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bean.Category;
import com.example.bean.Coupon;
import com.example.exception.CouponNotFoundException;
import com.example.exception.CouponTitleAlreadyUsedException;
import com.example.facade.CompanyFacade;
import com.example.web.annotations.CompanyLogin;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {
	
	@CompanyLogin
	@PostMapping("/add/{token}")
	public ResponseEntity<?> addCoupon(@PathVariable String token, @PathVariable(required = false) CompanyFacade companyFacade, @RequestBody Coupon coupon) {
		try {
			companyFacade.addCoupon(coupon);
			return ResponseEntity.status(HttpStatus.CREATED).body(coupon.getId());
		} catch (CouponTitleAlreadyUsedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@CompanyLogin
	@PutMapping("/update/{token}")
	public ResponseEntity<?> updateCoupon(@PathVariable String token, @PathVariable(required = false) CompanyFacade companyFacade, @RequestBody Coupon coupon) {
		try {
			companyFacade.updateCoupon(coupon);
			return ResponseEntity.ok().build();
		} catch (CouponNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (CouponTitleAlreadyUsedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@CompanyLogin
	@DeleteMapping("/delete/{couponId}/{token}")
	public ResponseEntity<?> deleteCoupon(@PathVariable String token, @PathVariable(required = false) CompanyFacade companyFacade, @PathVariable int couponId) {
		try {
			companyFacade.deleteCoupon(couponId);
			return ResponseEntity.ok().build();
		} catch (CouponNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coupon was not found!");
		}
	}
	
	@CompanyLogin
	@GetMapping("/view/all/{token}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable String token, @PathVariable(required = false) CompanyFacade companyFacade) {
		return ResponseEntity.ok(companyFacade.getCompanyCoupons());
	}
	
	@CompanyLogin
	@GetMapping("/view/max/{maxPrice}/{token}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable String token, @PathVariable(required = false) CompanyFacade companyFacade, @PathVariable int maxPrice) {
		return ResponseEntity.ok(companyFacade.getCompanyCoupons(maxPrice));
	}
	
	@CompanyLogin
	@GetMapping("/view/category/{category}/{token}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable String token, @PathVariable(required = false) CompanyFacade companyFacade, @PathVariable Category category) {
		return ResponseEntity.ok(companyFacade.getCompanyCoupons(category));
	}
	
	@CompanyLogin
	@GetMapping("/view/details/{token}")
	public ResponseEntity<?> getCompanyDetails(@PathVariable String token, @PathVariable(required = false) CompanyFacade companyFacade) {
		return ResponseEntity.ok(companyFacade.getCompanyDetails());
	}
	
}
