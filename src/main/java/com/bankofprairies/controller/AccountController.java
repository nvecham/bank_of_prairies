package com.bankofprairies.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.bean.TransactionBean;
import com.bankofprairies.dao.AccountDao;
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

	@Autowired
	TransactionBean transactionBean;
	
	@Autowired
	AccountDao accountDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ModelAttribute("username")
	public String getUsername(Principal principal) {
		logger.debug(principal.getName());
		String username = principal.getName();
		CustomerBean customer = adminService.findByUsername(username);
		String fullName = customer.getFirstName()+ ' '+customer.getLastName();

		
		return fullName;
	}

	@GetMapping("/customerDashboard")
	public String customerDashboard(Model model, Authentication authentication) {

		String username = authentication.getName();
		CustomerBean customer = adminService.findByUsername(username);
		int idCustomer = customer.getIdCustomer();

		String fullName = customer.getFirstName() + ' ' + customer.getLastName();
		model.addAttribute("fullName", fullName);

		AccountBean account = accountService.getAccountById(idCustomer);
		int idAccount = account.getIdAccount();

		BigDecimal accountBalance = account.getAccountBalance();
		model.addAttribute("accountBalance", accountBalance);
		
		List<TransactionBean> transactions = this.accountService.totalTransactions(idAccount);
		
		BigDecimal totalCreditAmount = BigDecimal.ZERO;
		BigDecimal totalDebitAmount = BigDecimal.ZERO;

		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH) + 1; // Add 1 because Calendar.MONTH is zero-based

		for (TransactionBean transaction : transactions) {
		    if (transaction.getTransacType().equalsIgnoreCase("credit")) {
		        calendar.setTime(transaction.getTransacDate());
		        int transactionMonth = calendar.get(Calendar.MONTH) + 1; // Add 1 because Calendar.MONTH is zero-based
		        if (transactionMonth == currentMonth) {
		            totalCreditAmount = totalCreditAmount.add(transaction.getTranscAmount());
		        }
		    }
		    else if(transaction.getTransacType().equalsIgnoreCase("debit")) {
		    	calendar.setTime(transaction.getTransacDate());
		        int transactionMonth = calendar.get(Calendar.MONTH) + 1; // Add 1 because Calendar.MONTH is zero-based
		        if (transactionMonth == currentMonth) {
		            totalDebitAmount = totalDebitAmount.add(transaction.getTranscAmount());
		        }
		    }
		}
		model.addAttribute("totalCrAmount", totalCreditAmount);
		model.addAttribute("totalDebitAmount", totalDebitAmount);
		
		
		
		

		/*
		 * TransactionBean totalDebit =
		 * accountService.getCurrentMonthDbAmount(idAccount); //BigDecimal totalDebit =
		 * transaction.getTotalDebit(); model.addAttribute("totalDebit", totalDebit);
		 */

		return "customerDashboard";
	}

	
	/*
	 * public double getTotalAmountForTypeAndMonth(String type, LocalDate month) {
	 * TransactionBean transactions = accountDao.findByTypeAndMonth(type, month);
	 * BigDecimal totalAmount = BigDecimal.ZERO; for (TransactionBean transaction :
	 * transactions) { totalAmount = totalAmount.add(transaction.getTranscAmount());
	 * } return totalAmount; }
	 */
	
	@GetMapping("/accountDetails")
	public String getAccountDetails(Model model, Authentication authentication) {

		String username = authentication.getName();
		CustomerBean customer = adminService.findByUsername(username);
		int idCustomer = customer.getIdCustomer();

		String accountdHolderName = customer.getFirstName() + ' ' + customer.getLastName();
		model.addAttribute("accountdHolderName", accountdHolderName);

		AccountBean account = accountService.getAccountById(idCustomer);

		long accountNumber = account.getAccountNumber();
		model.addAttribute("accountNumber", accountNumber);

		BigDecimal accountBalance = account.getAccountBalance();
		model.addAttribute("accountBalance", accountBalance);

		return "accountDetails";
	}

	@GetMapping("/allTransactions")
	public ModelAndView listTransactions(Authentication authentication) {
		logger.debug("Listing Transactions");
		
		String username = authentication.getName();
		CustomerBean customer = adminService.findByUsername(username);
		int idCustomer = customer.getIdCustomer();
		
		AccountBean account = accountService.getAccountById(idCustomer);
		
		int idAccount = account.getIdAccount();

		List<TransactionBean> transactions = this.accountService.recentTransactions(idAccount);

		return new ModelAndView("allTransactions", "transactions", transactions);
	}

	@GetMapping("/depositMoney")
	public String depositMoneyForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		CustomerBean customer = adminService.findByUsername(username);
		int idCustomer = customer.getIdCustomer();
		AccountBean account = accountService.getAccountById(idCustomer);
		long accountNumber = account.getAccountNumber();
		model.addAttribute("accountNumber", accountNumber);
		return "/depositMoney";
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

		String transcDescription = request.getParameter("desc");

		// Update the account balance with the deposit amount
		accountService.depositMOney(accountId, depositAmount, transcDescription);

		// Set the updated account balance as a model attribute
		// AccountBean updatedAccount = accountService.getAccountById(accountId);
		// model.addAttribute("account", account);

		// Redirect to the account details page with a success message
		model.addAttribute("successMsg", "Deposit of " + depositAmount + " successful.");
		return "redirect:/allTransactions";

	}

	@GetMapping("/withdrawMoney")
	public String withdrawMoneyForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		CustomerBean customer = adminService.findByUsername(username);
		int idCustomer = customer.getIdCustomer();
		AccountBean account = accountService.getAccountById(idCustomer);
		long accountNumber = account.getAccountNumber();
		model.addAttribute("accountNumber", accountNumber);
		return "/withdrawMoney";
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

		String transcDescription = request.getParameter("desc");

		// Update the account balance with the deposit amount
		accountService.withdrawMoney(accountId, withdrawtAmount, transcDescription);

		// Set the updated account balance as a model attribute
		// AccountBean updatedAccount = accountService.getAccountById(accountId);
		model.addAttribute("account", account);
		model.addAttribute("accountNumber", accountNumber);

		// Redirect to the account details page with a success message
		model.addAttribute("successMsg", "Deposit of " + withdrawtAmount + " successful.");
		return "redirect:/allTransactions";

	}
	
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
