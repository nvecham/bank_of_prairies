package com.bankofprairies.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.service.AdminService;
import com.bankofprairies.util.Util;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/")
	public String index() {
		logger.debug("Landing page");
		return "index";
	}

	@GetMapping("/list_customers")
	public ModelAndView listCustomers() {
		logger.debug("Listing customers");
		
		List<CustomerBean> customers = this.adminService.listCustomers();
		return new ModelAndView("list","customers", customers);
							//(template, object, parameter for thymeleaf in html)
	}
	
	@GetMapping("/addcustomer")
	public String showRegisterForm() {
		
		logger.debug("Show register form");
		return "addCustomer";
		
	}
	
	@PostMapping("/addcustomer")
	public String addCustomer(HttpServletRequest req) {
		
		logger.debug("Add Customer");
		
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String birth = req.getParameter("birth");
		String gender = req.getParameter("gender");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");
		String sin = req.getParameter("sin");
		String street = req.getParameter("street");
		String city = req.getParameter("city");
		String province = req.getParameter("province");
		String zip =req.getParameter("zip");
		String country = req.getParameter("country");
		String status = req.getParameter("status");
		
		CustomerBean customer = this.createCustomerBean(firstName, lastName, birth, gender, username, password, email, mobile, sin, street, city, province, zip, country, status);
		
		this.adminService.addCustomer(customer);
		
		return "redirect:/list_customers";
	}
	
	private CustomerBean createCustomerBean(String firstName, String lastName, String birth, String gender, String username,
			String password, String email, String mobile, String sin, String street, String city, String province,
			String zip, String country, String status) {
		
		CustomerBean customer = new CustomerBean(0,firstName,lastName,Util.parseDate(birth),gender,username,password,email,mobile,sin,
				street,city,province,zip,country,status);
		
		return customer;
		
	}
	
}
