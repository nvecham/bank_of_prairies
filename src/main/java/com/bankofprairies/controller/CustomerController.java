package com.bankofprairies.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.service.AccountService;
import com.bankofprairies.service.AdminService;

@Controller
public class CustomerController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	AdminService adminService;
	
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/*
	 * @GetMapping("/cardDetails/{idCustomer}") public ModelAndView
	 * showCardDetails(@PathVariable("idCustomer") int idCustomer,Model model){
	 * logger.debug("CardDetails page");
	 * 
	 * AccountBean account = accountService.getAccountById(idCustomer); long
	 * debitCardNumber = account.getDebitCardNumber(); // Date debitCardValidity =
	 * account.getDebitCardValidTill(); int cvv = account.getDebitCvv();
	 * 
	 * model.addAttribute("debitCardNumber", debitCardNumber); //
	 * model.addAttribute("debitCardValidity", debitCardValidity);
	 * model.addAttribute("cvv", cvv);
	 * 
	 * logger.debug("Return card details");
	 * 
	 * return new ModelAndView("cardDetails");
	 * 
	 * }
	 */
	 @ModelAttribute("username")
	    public String getUsername(Principal principal) {
		 logger.debug(principal.getName());
	    	return principal.getName();
	    }
	 
	 	@GetMapping("/accountDetails")
	 	public String getAccountDetails(Model model, Authentication authentication) {
	 		
	 		
	 		return "accountDetails";
	 	}
	 
	 
		/*
		 * @GetMapping("/") public String home(Model model, Principal principal) {
		 * String username = principal.getName(); CustomerBean customer =
		 * adminService.findByUsername(username); String fullName =
		 * customer.getFirstName() + ' ' +customer.getLastName();
		 * model.addAttribute("fullName", fullName); return "customerHeader"; }
		 */
		
		
		  @GetMapping("/cardDetails") 
		  public String getCardDetails(Model model, Authentication authentication) { 
			  
			  String username = authentication.getName();
			  CustomerBean customer = adminService.findByUsername(username); 
			  int idCustomer = customer.getIdCustomer(); 
			  AccountBean account = accountService.getAccountById(idCustomer);
		  
		  	  long debitCardNumber = account.getDebitCardNumber(); 
			 
				/*
				 * String cardNumberString = Long.toString(debitCardNumber); List<String>
				 * cardNumberList = new ArrayList<>(); for (int i = 0; i <
				 * cardNumberString.length(); i += 4) {
				 * cardNumberList.add(cardNumberString.substring(i, Math.min(i + 4,
				 * cardNumberString.length()))); }
				 */
			  
			  Date debitCardValidity = account.getDebitCardValidTill(); 
			  int cvv = account.getDebitCvv(); 
			  String cardHolderName = customer.getFirstName()+ ' ' + customer.getLastName();
			  model.addAttribute("debitCardNumber",debitCardNumber); 
			  model.addAttribute("debitCardValidity", debitCardValidity); 
			  model.addAttribute("cvv", cvv);
			  model.addAttribute("cardHolderName", cardHolderName);
		  
		  return "cardDetails";
		  
		  }
		 
	}


