package com.example.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.bean.Company;
import com.example.bean.Coupon;
import com.example.bean.Customer;
import com.example.exception.CompanyEmailTakenException;
import com.example.exception.CompanyNameTakenException;
import com.example.exception.CompanyNotFoundException;
import com.example.exception.CustomerEmailTakenException;
import com.example.exception.CustomerNotFoundException;

/**
 * The facade for administrators to use.
 * @author Ori {@literal <<<} I'm an author now!
 * 
 */
@Service
@Scope("prototype")
public class AdminFacade extends ClientFacade {
	
	/**
	 * Adds the company to the database.
	 * @param company a new company to be added to the database.
	 * @throws CompanyEmailTakenException if another company has already registered with the same email as in <b>company</b>.
	 * @throws CompanyNameTakenException if another company has already registered with the same name as in <b>company</b>.
	 */
	public void addCompany(Company company) throws CompanyEmailTakenException, CompanyNameTakenException { // IF ID != 0?
		if (companiesRepo.findByEmail(company.getEmail()).isPresent())
			throw new CompanyEmailTakenException(company.getEmail());
		if (companiesRepo.findByName(company.getName()).isPresent())
			throw new CompanyNameTakenException();
		companiesRepo.save(company);
	}
	
	/**
	 * Updates the company (by <b>company</b>'s id).
	 * @param company an already existing company with changed attributes.
	 * @throws CompanyNotFoundException if the given <code>Company</code> is not found as an already existing company in the database.
	 * @throws CompanyEmailTakenException if another company has already registered with the same name as in <b>company</b>.
	 */
	public void updateCompany(Company company) throws CompanyNotFoundException, CompanyEmailTakenException { // Coupons list updated?
		Company dbCompany = companiesRepo.findById(company.getId()).orElseThrow(CompanyNotFoundException::new);
		company.setName(dbCompany.getName()); // So any name change is ignored
		Optional<Company> companyByEmail = companiesRepo.findByEmail(company.getEmail());
		if (companyByEmail.isEmpty() || companyByEmail.get().getId() == company.getId())
			companiesRepo.save(company);
		else
			throw new CompanyEmailTakenException(company.getEmail());
	}
	
	/**
	 * Deletes the company with the same id as the given <b>companyId</b>.
	 * @param companyId the id of the company to be deleted.
	 * @throws CompanyNotFoundException if no company with the given id was found.
	 */
	public void deleteCompany(int companyId) throws CompanyNotFoundException {
		for (Coupon coupon : getOneCompany(companyId).getCoupons()) {
			couponsRepo.deletePurchases(coupon.getId());
			couponsRepo.deleteById(coupon.getId());
		}
		companiesRepo.deleteById(companyId);
	}
	
	/**
	 * @return returns a {@literal List<Company>} of all the companies that are in the database.<br>
	 * returns an empty {@literal List<Company>}.
	 */
	public List<Company> getAllCompanies() {
		return companiesRepo.findAll();
	}
	
	/**
	 * @param companyId the id of the company to be retrieved.
	 * @return returns the retrieved company from the database that's with the given id.
	 * @throws CompanyNotFoundException if no company with the given id was found.
	 */
	public Company getOneCompany(int companyId) throws CompanyNotFoundException {
		Optional<Company> company = companiesRepo.findById(companyId);
		if (company.isEmpty())
			throw new CompanyNotFoundException();
		return company.get();
	}
	
	/**
	 * Adds the customer to the database.
	 * @param customer a new customer to be added to the database.
	 * @throws CustomerEmailTakenException if another customer has already registered with the same email as in <b>customer</b>.
	 */
	public void addCustomer(Customer customer) throws CustomerEmailTakenException {
		if (customersRepo.findByEmail(customer.getEmail()).isPresent())
			throw new CustomerEmailTakenException(customer.getEmail());
		customersRepo.save(customer);
	}
	
	/**
	 * Updates the customer (by <b>customer</b>'s id).
	 * @param customer an already existing customer with changed attributes.
	 * @throws CustomerNotFoundException if the given <code>Customer</code> is not found as an already existing customer in the database.
	 * @throws CustomerEmailTakenException if another customer has already registered with the same email as in <b>customer</b>.
	 */
	public void updateCustomer(Customer customer) throws CustomerNotFoundException, CustomerEmailTakenException {
		if (customersRepo.findById(customer.getId()).isEmpty())
			throw new CustomerNotFoundException();
		Optional<Customer> customerByEmail = customersRepo.findByEmail(customer.getEmail());
		if (customerByEmail.isEmpty() || customerByEmail.get().getId() == customer.getId())
			customersRepo.save(customer);
		else
			throw new CustomerEmailTakenException(customer.getEmail());
		
	}
	
	/**
	 * Deletes the company with the same id as the given <b>companyId</b>.
	 * @param customerId the id of the customer to be deleted.
	 * @throws CustomerNotFoundException if the given <code>Customer</code> is not found as an already existing customer in the database.
	 */
	public void deleteCustomer(int customerId) throws CustomerNotFoundException {
		getOneCustomer(customerId); // To check whether the customer even exists.
		customersRepo.deletePurchases(customerId);
		customersRepo.deleteById(customerId);
		
	}
	
	/**
	 * 
	 * @return returns a {@literal List<Customer>} of all the customer that are in the database.<br>
	 * returns an empty {@literal List<Customer>}.
	 */
	public List<Customer> getAllCustomers() {
		return customersRepo.findAll();
	}
	
	/**
	 * 
	 * @param customerId the id of the customer to be retrieved.
	 * @return returns the retrieved customer from the database that's with the given id.
	 * @throws CustomerNotFoundException if no customer with the given id was found.
	 */
	public Customer getOneCustomer(int customerId) throws CustomerNotFoundException {
		Optional<Customer> customer = customersRepo.findById(customerId);
		if (customer.isEmpty())
			throw new CustomerNotFoundException();
		return customer.get();
	}
	
}
