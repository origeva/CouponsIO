package com.example.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String email, password;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "purchases")
	private List<Coupon> coupons;
	
	public Customer() {
		super();
	}

	public Customer(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
        if (obj instanceof Customer && this.id != 0 && this.id == ((Customer) obj).getId())
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
		return id + " " + firstName + " " + lastName + "\nEmail: " + email + "\nPassword:"
				+ password;
	}
	
}
