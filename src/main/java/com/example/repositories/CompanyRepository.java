package com.example.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bean.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	Optional<Company> findByEmail(String email);
	
	Optional<Company> findByName(String name);
	
}
