package com.example.bean;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "coupons")
public class Coupon {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	@ManyToOne(targetEntity = Company.class)
//	@ManyToOne
//	@JoinColumn(name = "company_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_company_id"))
//	private Company company;
	@Column(name = "company_id")
	private int companyId;
	@Enumerated
	@Column(name = "category_id")
	private Category category;
	private String title, description;
	private Date startDate, endDate;
	private int amount;
	private double price;
	private String image;
	@ManyToMany(mappedBy = "coupons")
	@JsonIgnore
	private List<Customer> customers;
	
	public Coupon() {
		super();
	}

	public Coupon(Category category, String title, String description, Date startDate,
			Date endDate, int amount, double price, String image) {
		super();
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public int getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (this == obj)
        	return true;
        if (this instanceof Coupon && this.id != 0 && this.id == ((Coupon) obj).getId())
        	return true;
        return false;
	}

	@Override
	public String toString() {
		return id + "\nCategory: " + category + "\n" + title + "\nDescription: "
				+ description + "\nStarts: " + startDate + "\nEnds: " + endDate + "\nPrice" + price + "\nCoupons left: "
				+ amount + "\nImage: " + image;
	}
	
}
