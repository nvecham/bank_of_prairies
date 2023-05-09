package com.bankofprairies.controller;

import java.math.BigDecimal;
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

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.service.AccountService;
import com.bankofprairies.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class AccountController {
	
	@Autowired
	AccountBean accountBean;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	AdminService adminService;
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 @ModelAttribute("username")
	    public String getUsername(Principal principal) {
		 logger.debug(principal.getName());
	    	return principal.getName();
	    }

	 
	 @GetMapping("/allTransactions")
	 public String allTransactions() {
		return "allTransactions";
		 
	 }
	 
	 @GetMapping("/depositMoney")
	 public String depositMoneyForm() {
		 
		 return"/depositMoney";
	 }
	 
	 @PostMapping("/deposit")
	 public String depositMoney(HttpServletRequest request, Model model) {
		logger.debug("Deposit Money");
		
		// Get the logged in user's account details
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    CustomerBean customer = adminService.findByUsername(username); 
		int idCustomer = customer.getIdCustomer(); 
		AccountBean account = accountService.getAccountById(idCustomer);
		int accountId = account.getIdAccount();

	    // Get the deposit amount from the request parameter
	    String depositAmountStr = request.getParameter("amount");
	    BigDecimal depositAmount = new BigDecimal(depositAmountStr);


	    // Update the account balance with the deposit amount
	    accountService.depositMOney(accountId, depositAmount);

	    // Set the updated account balance as a model attribute
	    //AccountBean updatedAccount = accountService.getAccountById(accountId);
	    model.addAttribute("account", account);

	    // Redirect to the account details page with a success message
	    model.addAttribute("successMsg", "Deposit of " + depositAmount + " successful.");
	    return "redirect:/allTransactions";
		 
	 }
	 
	 @GetMapping("/withdrawMoney")
	 public String withdrawMoneyForm() {
		 
		 return"/withdrawMoney";
	 }
	 
	 
	 @PostMapping("/withdraw")
	 public String withdrawMoney(HttpServletRequest request, Model model) {
		logger.debug("Withdraw Money");
		
		// Get the logged in user's account details
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    CustomerBean customer = adminService.findByUsername(username); 
		int idCustomer = customer.getIdCustomer(); 
		AccountBean account = accountService.getAccountById(idCustomer);
		int accountId = account.getIdAccount();
		long accountNumber = account.getAccountNumber();

	    // Get the deposit amount from the request parameter
	    String withdrawAmountStr = request.getParameter("amount");
	    BigDecimal withdrawtAmount = new BigDecimal(withdrawAmountStr);


	    // Update the account balance with the deposit amount
	    accountService.withdrawMoney(accountId, withdrawtAmount);

	    // Set the updated account balance as a model attribute
	    //AccountBean updatedAccount = accountService.getAccountById(accountId);
	    model.addAttribute("account", account);
	    model.addAttribute("accountNumber", accountNumber);

	    // Redirect to the account details page with a success message
	    model.addAttribute("successMsg", "Deposit of " + withdrawtAmount + " successful.");
	    return "redirect:/allTransactions";
		 
	 }
	 
		 
	 }

