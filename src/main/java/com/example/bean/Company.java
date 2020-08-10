package com.example.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "companies")
@Component
public class Company {
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(updatable = false)
	private String name;
	private String email, password;
	@OneToMany(mappedBy = "companyId", fetch = FetchType.EAGER)
	private List<Coupon> coupons;
	
	public Company() {
		super();
	}

	public Company(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (this == obj)
        	return true;
        if (obj instanceof Company && this.id != 0 && this.id == ((Company) obj).getId())
        	return true;
        return false;
	}

	@Override
	public String toString() {
//		StringBuilder str = new StringBuilder();
//		if (coupons.size() == 0) {
//			str.append("\nNo coupons owned.");
//		} else {
//			str.append("\nCoupons:");
//			for (Coupon coupon : coupons) {
//				str.append("\n" + coupon.getTitle());
//			}
//		}
		return id + " " + name + "\nEmail: " + email + "\nPassword: " + password;
	}
	
}
