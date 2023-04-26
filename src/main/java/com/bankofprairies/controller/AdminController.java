package com.bankofprairies.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.service.AdminService;

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
	
	
}
