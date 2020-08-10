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

import com.example.bean.Company;
import com.example.bean.Customer;
import com.example.exception.CompanyEmailTakenException;
import com.example.exception.CompanyNameTakenException;
import com.example.exception.CompanyNotFoundException;
import com.example.exception.CustomerEmailTakenException;
import com.example.exception.CustomerNotFoundException;
import com.example.facade.AdminFacade;
import com.example.web.annotations.AdminLogin;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	
	@AdminLogin
	@PostMapping("/company/add/{token}")
	public ResponseEntity<?> addCompany(@PathVariable String token, @PathVariable(required = false) AdminFacade adminFacade, @RequestBody Company company) {
		try {
			adminFacade.addCompany(company);
			return ResponseEntity.status(HttpStatus.CREATED).body(company.getId());
		} catch (CompanyEmailTakenException | CompanyNameTakenException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@AdminLogin
	@PutMapping("/company/update/{token}")
	public ResponseEntity<?> updateCompany(@PathVariable String token, @PathVariable(required = false) AdminFacade adminFacade, @RequestBody Company company) {
		try {
			adminFacade.updateCompany(company);
			return ResponseEntity.accepted().build();
		} catch (CompanyNotFoundException | CompanyEmailTakenException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@AdminLogin
	@DeleteMapping("/company/delete/{companyId}/{token}")
	public ResponseEntity<?> deleteCompany(@PathVariable String token, @PathVariable(required = false) AdminFacade adminFacade, @PathVariable int companyId) {
		try {
			adminFacade.deleteCompany(companyId);
			return ResponseEntity.ok().build();
		} catch (CompanyNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@AdminLogin
	@GetMapping("/company/getall/{token}")
	public ResponseEntity<?> getAllCompanies(@PathVariable String token, @PathVariable(required = false) AdminFacade adminFacade) {
		return ResponseEntity.ok(adminFacade.getAllCompanies());
	}
	
	@AdminLogin
	@GetMapping("/company/getone/{companyId}/{token}")
	public ResponseEntity<?> getOneCompany(@PathVariable String token, @PathVariable(required = false) AdminFacade adminFacade, @PathVariable int companyId) {
		try {
			return ResponseEntity.ok(adminFacade.getOneCompany(companyId));
		} catch (CompanyNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@AdminLogin
	@PostMapping("/customer/add/{token}")
	public ResponseEntity<?> addCustomer(@PathVariable String token, @PathVariable(required = false) AdminFacade adminFacade, @RequestBody Customer customer) {
		try {
			adminFacade.addCustomer(customer);
			return ResponseEntity.status(HttpStatus.CREATED).body(customer.getId());
		} catch (CustomerEmailTakenException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@AdminLogin
	@PutMapping("/customer/update/{token}")
	public ResponseEntity<?> updateCustomer(@PathVariable String token, @PathVariable(required = false) AdminFacade adminFacade, @RequestBody Customer customer) {
		try {
			adminFacade.updateCustomer(customer);
			return ResponseEntity.accepted().build();
		} catch (CustomerNotFoundException | CustomerEmailTakenException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@AdminLogin
	@DeleteMapping("/customer/delete/{customerId}/{token}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String token, @PathVariable(required = false) AdminFacade adminFacade, @PathVariable int customerId) {
		try {
			adminFacade.deleteCustomer(customerId);
			return ResponseEntity.ok().build();
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@AdminLogin
	@GetMapping("/customer/getall/{token}")
	public ResponseEntity<?> getAllCustomers(@PathVariable String token, @PathVariable(required = false) AdminFacade adminFacade) {
		return ResponseEntity.ok(adminFacade.getAllCustomers());
	}
	
	@AdminLogin
	@GetMapping("/customer/getone/{customerId}/{token}")
	public ResponseEntity<?> getOneCustomer(@PathVariable String token, @PathVariable(required = false) AdminFacade adminFacade, @PathVariable int customerId) {
		try {
			return ResponseEntity.ok(adminFacade.getOneCustomer(customerId));
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer was not found!");
		}
	}
	
}
