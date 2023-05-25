package com.bankofprairies.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.service.AccountService;
import com.bankofprairies.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomerController {

	@Autowired
	AccountService accountService;

	@Autowired
	AdminService adminService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * @ModelAttribute("username") public String getUsername(Principal principal) {
	 * logger.debug(principal.getName()); String username = principal.getName();
	 * CustomerBean customer = adminService.findByUsername(username); String
	 * fullName = customer.getFirstName()+ ' ' + customer.getLastName(); return
	 * fullName; }
	 */
	
	@ModelAttribute("username")
	public void getUsername(Principal principal, Model model) {
		logger.debug(principal.getName());
		String username = principal.getName();
		CustomerBean customer = adminService.findByUsername(username);
		String fullName = customer.getFirstName()+ ' '+customer.getLastName();
		
		model.addAttribute("fullName", fullName);
		
		//return fullName;
	}
	
	
	
	@GetMapping("/passwordChange")
	public String passwordChangeForm() {
		
		return "passwordChange";
	}
	
	@PostMapping("/passwordChange")
	public String updatePassword(HttpServletRequest request, Model model) {
		
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    
	    String username = authentication.getName();
	        
	    CustomerBean customer = adminService.findByUsername(username);
	    
	    int idCustomer = customer.getIdCustomer();
	    //String currentPassword = customer.getPassword();
	    
	    //String currentPassword = request.getParameter("oldPassword");
	    
		/*
		 * if(!currentDbPassword.equals(currentPassword)) {
		 * System.out.println("Old password is not correct"); }
		 */
	    
	    String newPassword = request.getParameter("newPassword");
	    this.adminService.updatePassword(newPassword,idCustomer);

		return"redirect:/customerDashboard";
		
	}

}
