package com.bankofprairies.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.service.AccountService;
import com.bankofprairies.service.AdminService;
import com.bankofprairies.service.EmailService;
import com.bankofprairies.util.Util;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EmailService emailService;

	@Autowired
	AdminService adminService;

	@Autowired
	AccountService accountService;

	/*
	 * @GetMapping("/") public String index() { logger.debug("Landing page"); return
	 * "index"; }
	 */

	/*
	 * @PostMapping("/login") public RedirectView login(@RequestParam("username")
	 * String username,
	 * 
	 * @RequestParam("password") String password, HttpServletRequest request) {
	 * 
	 * // Perform authentication and determine user role String role =
	 * this.determineUserRole(username, password);
	 * 
	 * // Create redirect URL based on user role String redirectUrl; if
	 * (role.equals("USER")) { redirectUrl = "/user/home"; } else if
	 * (role.equals("ADMIN")) { redirectUrl = "/admin/home"; } else { redirectUrl =
	 * "/"; }
	 * 
	 * // Create redirect response to the appropriate URL RedirectView redirectView
	 * = new RedirectView(request.getContextPath() + redirectUrl);
	 * redirectView.setExposeModelAttributes(false); return redirectView; }
	 */
	
	/*
	 * @PostMapping("/login") public String doLogin(Authentication authentication) {
	 * if (authentication.getAuthorities().contains(new
	 * SimpleGrantedAuthority("ADMIN"))) { return "redirect:/index"; } else if
	 * (authentication.getAuthorities().contains(new
	 * SimpleGrantedAuthority("CUSTOMER"))) { return "redirect:/userDashboard"; }
	 * else { return "redirect:/login?error"; } }
	 */
	
	
	@GetMapping("/adminDashboard")
	public String adminDashboard() {
	    return "adminDashboard";
	}

	@GetMapping("/customerDashboard")
	public String customerDashboard() {
	    return "customerDashboard";
	}
	

	@GetMapping("/list_customers")
	public ModelAndView listCustomers() {
		logger.debug("Listing customers");

		List<CustomerBean> customers = this.adminService.listCustomers();
		return new ModelAndView("list", "customers", customers);
		// (template, object, parameter for thymeleaf in html)
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
		String zip = req.getParameter("zip");
		String country = req.getParameter("country");
		String status = req.getParameter("status");
		String role = req.getParameter("role");

		CustomerBean customer = this.createCustomerBean(null, firstName, lastName, birth, gender, username, password,
				email, mobile, sin, street, city, province, zip, country, status, role);

		this.adminService.addCustomer(customer);

		this.emailService.sendEmail( // 
				customer.getEmail(), //
				"Welcome to Bank of Prairies", //
				"You are registered successfully. Please find your username and password below:" + "Username : "
						+ customer.getUsername() + " Password : " + customer.getPassword());
		return "redirect:/list_customers";
	}

	@GetMapping("/update/{id}")
	public ModelAndView showUpdateUser(@PathVariable int id) {

		logger.debug("Show Update Customer");
		CustomerBean customer = this.adminService.findCustomer(id);
		return new ModelAndView("updateCustomer", "customer", customer);
	}

	@PostMapping("/updateCustomer")
	public String updateCustomer(HttpServletRequest req) {

		logger.debug("Update Customer");

		String id = req.getParameter("id");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String birth = req.getParameter("birth");
		String gender = req.getParameter("gender");
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");
		String sin = req.getParameter("sin");
		String street = req.getParameter("street");
		String city = req.getParameter("city");
		String province = req.getParameter("province");
		String zip = req.getParameter("zip");
		String country = req.getParameter("country");
		String status = req.getParameter("status");
		String role = req.getParameter("role");

		CustomerBean customer = this.createCustomerBean(id, firstName, lastName, birth, gender, username, null, email,
				mobile, sin, street, city, province, zip, country, status, role);

		this.adminService.updateCustomer(customer);
		return "redirect:/list_customers";
	}

	@GetMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable int id) {

		logger.debug("Delete Customer");

		this.adminService.deleteCustomer(id);

		return "redirect:/list_customers";
	}

	private CustomerBean createCustomerBean(String id, String firstName, String lastName, String birth, String gender,
			String username, String password, String email, String mobile, String sin, String street, String city,
			String province, String zip, String country, String status, String role) {

		CustomerBean customer = new CustomerBean(Util.parseId(id), firstName, lastName, Util.parseDate(birth), gender,
				username, password, email, mobile, sin, street, city, province, zip, country, status, role);

		return customer;

	}

}
