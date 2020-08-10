package com.example;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.bean.Category;
import com.example.bean.Company;
import com.example.bean.Coupon;
import com.example.bean.Customer;
import com.example.repositories.CompanyRepository;
import com.example.repositories.CouponRepository;
import com.example.repositories.CustomerRepository;

@SpringBootApplication
public class CouponsIoApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext ctx = SpringApplication.run(CouponsIoApplication.class, args);
		
		dummyData(ctx);
		
	}
	
	public static void dummyData(ConfigurableApplicationContext ctx) {
		try {
			
			CompanyRepository companyRepo = ctx.getBean(CompanyRepository.class);
			CustomerRepository customerRepo = ctx.getBean(CustomerRepository.class);
			CouponRepository couponRepo = ctx.getBean(CouponRepository.class);
			
			if (customerRepo.findAll().size() > 1)
				throw new Exception("Database is not empty");
			
			Random random = new Random();
			
			Date nowDate = new Date(System.currentTimeMillis());
			Date dateIn5Days = new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000);
			Date dateIn5Years = new Date(System.currentTimeMillis() + 5 * 365 * 24 * 60 * 60 * 1000);
			
			Company theBest = new Company("The Best Company", "thebest@company.com", "pass");
			Company theSecond = new Company("The Second Best", "thesecond@company.com", "pass");
			Company anotherCompany = new Company("Another Company", "another@company.com", "pass");
			List<Company> companies = new ArrayList<Company>( Arrays.asList(theBest, theSecond, anotherCompany) );
			for (Company company : companies)
				companyRepo.save(company);
			
			Customer customer1 = new Customer("Dan", "Ross", "dross@customer.com", "pass");
			Customer customer2 = new Customer("Jane", "Ashton", "jashton@customer.com", "pass");
			Customer customer3 = new Customer("John", "Doe", "jdoe@customer.com", "pass");
			Customer customer4 = new Customer("Brianna", "Smith", "bsmith@customer.com", "pass");
			Customer customer5 = new Customer("Ronald", "Trump", "rtrump@customer.com", "pass");
			List<Customer> customers = new ArrayList<Customer>( Arrays.asList(customer1, customer2, customer3, customer4, customer5) );
			for (Customer customer : customers)
				customerRepo.save(customer);
			
			Coupon coupon1 = new Coupon(Category.Clothing, "Doggo T-Shirt", "Great t-shirt with a dog print!", nowDate, dateIn5Days, 15, 9.99, "https://images-na.ssl-images-amazon.com/images/I/51Th-f39mBL._AC_UX385_.jpg");
			Coupon coupon2 = new Coupon(Category.Electronics, "Smart Kettle", "Smart electric tea kettle with temperature control gets most flavorful tea & coffee or simply warms up or boils water.", nowDate, dateIn5Days, 25, 49.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSco5ZgbD1US959FcH32zAKtF97Yb44FeYZ5Q");
			Coupon coupon3 = new Coupon(Category.Entertainment, "Bo Burnham Tickets", "You should actually check out his shows, he's a great comedian.", nowDate, dateIn5Days, 25, 74.99, "https://images.livemint.com/rf/Image-621x414/LiveMint/Period2/2016/11/19/Photos/Processed/bo-k56D--621x414@LiveMint.jpg");
			Coupon coupon4 = new Coupon(Category.Restaurants, "Misada Alayam", "Shakshuka for a couple", nowDate, dateIn5Days, 30, 17.50, "https://media-cdn.tripadvisor.com/media/photo-s/17/08/a2/69/shakshuka.jpg");			
			List<Coupon> coupons = new ArrayList<Coupon>( Arrays.asList(coupon1, coupon2, coupon3, coupon4) );
			
			for (Company company : companies) {
				int amount = random.nextInt(coupons.size());
				int i = 0;
				do {
					Coupon toAdd = coupons.get(random.nextInt(coupons.size()));
					toAdd.setCompanyId(company.getId());
					couponRepo.save(toAdd);
					i++;
				} while (i < amount);
			}
			
			Company aCompany = new Company("A Company", "a@company.com", "pass");
			companyRepo.save(aCompany);
			Coupon aCoupon = new Coupon(Category.Accessories, "Mad Pro Wheels", "The new iStea.. I mean, iWheel kit for your recently bought cheese grater! Now only for 698.99 instead of 699!!!!!!1!!", nowDate, dateIn5Years, 100, 698.99, "https://www.imore.com/sites/imore.com/files/styles/large/public/field/image/2019/06/mac-pro-2019-wheels.jpeg");
			aCoupon.setCompanyId(aCompany.getId());
			couponRepo.save(aCoupon);
			
		} catch (Exception e) {
			System.out.println("Entered dummy data method exception: " + e.getMessage());
		}
	}

}
