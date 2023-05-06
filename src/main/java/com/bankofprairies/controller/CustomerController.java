package com.bankofprairies.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.service.AccountService;



@Controller
public class CustomerController {
	
	
	@Autowired
	AccountService accountService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/cardDetails/{id}")
	public String showCardDetails(@PathVariable int idCustomer, Model model){
		logger.debug("CardDEtails page");
		
		AccountBean account = accountService.getAccountById(idCustomer);
	    long debitCardNumber = account.getDebitCardNumber();
	   // Date debitCardValidity = account.getDebitCardValidTill();
	    int cvv = account.getDebitCvv();
	    model.addAttribute("debitCardNumber", debitCardNumber);
	   // model.addAttribute("debitCardValidity", debitCardValidity);
	    model.addAttribute("cvv", cvv);
	  
		
		
		
		return "cardDetails";
		
	}

}
