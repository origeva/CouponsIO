package com.example.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bean.Category;
import com.example.exception.CouponAlreadyPurchasedException;
import com.example.exception.CouponExpiredException;
import com.example.exception.CouponNotFoundException;
import com.example.exception.InvalidAmountException;
import com.example.facade.CustomerFacade;
import com.example.web.annotations.CustomerLogin;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {
	
	@CustomerLogin
	@PostMapping("/purchase/{couponId}/{token}")
	public ResponseEntity<?> purchaseCoupon(@PathVariable String token, @PathVariable(required = false) CustomerFacade customerFacade, @PathVariable int couponId) {
		try {
			customerFacade.purchaseCoupon(couponId);
			return ResponseEntity.ok().build();
		} catch (CouponNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coupon was not found!");
		} catch (InvalidAmountException | CouponExpiredException | CouponAlreadyPurchasedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@CustomerLogin
	@GetMapping("/view/all/{token}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable String token, @PathVariable(required = false) CustomerFacade customerFacade) {
		return ResponseEntity.ok(customerFacade.getCustomerCoupons());
	}
	
	@CustomerLogin
	@GetMapping("/view/max/{maxPrice}/{token}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable String token, @PathVariable(required = false) CustomerFacade customerFacade, @PathVariable int maxPrice) {
		return ResponseEntity.ok(customerFacade.getCustomerCoupons(maxPrice));
	}
	
	@CustomerLogin
	@GetMapping("/view/category/{category}/{token}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable String token, @PathVariable(required = false) CustomerFacade customerFacade, @PathVariable Category category) {
		return ResponseEntity.ok(customerFacade.getCustomerCoupons(category));
	}
	
	@CustomerLogin
	@GetMapping("/view/details/{token}")
	public ResponseEntity<?> getCustomerDetails(@PathVariable String token, @PathVariable(required = false) CustomerFacade customerFacade) {
		return ResponseEntity.ok(customerFacade.getCustomerDetails());
	}
	
}
