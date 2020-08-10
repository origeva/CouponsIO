package com.example.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bean.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	Optional<Customer> findByEmail(String email);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "DELETE FROM purchases WHERE customers_id = ?1")
	void deletePurchases(int customerId);
	
}
