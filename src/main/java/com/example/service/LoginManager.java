package com.example.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.example.repositories.CompanyRepository;
import com.example.repositories.CouponRepository;
import com.example.repositories.CustomerRepository;
import com.example.bean.Company;
import com.example.bean.Coupon;
import com.example.bean.Customer;
import com.example.facade.AdminFacade;
import com.example.facade.ClientFacade;
import com.example.facade.CompanyFacade;
import com.example.facade.CustomerFacade;

/**
 * Spring Context singleton class, has the login method to get the <code>ClientFacade</code>.
 * @author Ori
 *
 */
@Service
public class LoginManager {
	
	@Autowired
	private CompanyRepository companiesRepo;
	@Autowired
	private CustomerRepository customersRepo;
	@Autowired
	private CouponRepository couponsRepo;
	@Autowired
	private ConfigurableApplicationContext ctx;
	
	public LoginManager(CompanyRepository companiesRepo, CustomerRepository customersRepo, CouponRepository couponsRepo, ConfigurableApplicationContext ctx) {
		super();
		this.companiesRepo = companiesRepo;
		this.customersRepo = customersRepo;
		this.couponsRepo = couponsRepo;
		this.ctx = ctx;
	}

	/**
	 * Checks the database for a matching entity with the given email and password of the <code>ClientType</code> type.
	 * @param email
	 * @param password
	 * @param client the type or "role" of the client that's logging in.
	 * @return the facade that has been asked for using the <code>ClientType</code>, if the login credentials were correct.<br>
	 * returns null if the credentials were incorrect.
	 */
	public ClientFacade login(String email, String password, ClientType client) {
		try {
			if (client == ClientType.ADMINISTRATOR) {
				if (email.equalsIgnoreCase("admin@admin.com") && password.equals("admin"))
					return ctx.getBean(AdminFacade.class);
			} else if (client == ClientType.COMPANY) {
				Company company = companiesRepo.findByEmail(email).orElseThrow();
				if (company.getPassword().equals(password))
					return ctx.getBean(CompanyFacade.class, company.getId());
			} else if (client == ClientType.CUSTOMER) {
				Customer customer = customersRepo.findByEmail(email).orElseThrow();
				if (customer.getPassword().equals(password))
					return ctx.getBean(CustomerFacade.class, customer.getId());
//				return ctx.getBean(CustomerFacade.class, customersRepo.findByEmail(email).orElseThrow().getId()); // Not case sensitive
			}
		} catch (NoSuchElementException e) {}
		return null;
	}
	
	public boolean isEmailAvailable(String email, ClientType clientType) {
//		return (client == ClientType.CUSTOMER && customersRepo.findByEmail(email).isEmpty()) || (client == ClientType.COMPANY && companiesRepo.findByEmail(email).isEmpty());
		if (clientType == ClientType.CUSTOMER && customersRepo.findByEmail(email).isEmpty())
			return true;
		if (clientType == ClientType.COMPANY && companiesRepo.findByEmail(email).isEmpty()) // Not in one line for less checks.
			return true;
		return false;
	}
	
//	public void register(String email, String password, ClientType client) {
//		if (client == ClientType.COMPANY) {
//			if (companiesRepo.findByEmail(email).isPresent())
//				throw new CompanyEmailTakenException(email);
//			if (companiesRepo.findByName("").isPresent())
//				throw new CompanyNameTakenException();
//			companiesRepo.save(company);
//		}
//			
//	}
	
	public List<Coupon> getAllCoupons() {
		return couponsRepo.findAll();
	}
	
}
