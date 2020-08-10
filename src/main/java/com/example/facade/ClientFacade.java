package com.example.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.repositories.CompanyRepository;
import com.example.repositories.CouponRepository;
import com.example.repositories.CustomerRepository;

/**
 * The facade for all client types to use, each with their given methods (permissions).
 * @author Ori
 *
 */
@Service
@Scope("prototype")
public abstract class ClientFacade {
	
	@Autowired
	protected CompanyRepository companiesRepo;
	@Autowired
	protected CustomerRepository customersRepo;
	@Autowired
	protected CouponRepository couponsRepo;

}
