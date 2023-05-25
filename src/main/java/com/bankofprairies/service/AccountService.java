package com.bankofprairies.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.bean.TransactionBean;
import com.bankofprairies.dao.AccountDao;

@Service
public class AccountService {

	@Autowired
	AccountDao accountDao;

	public AccountBean getAccountById(int idCustomer) {

		return this.accountDao.getAccountById(idCustomer);
	}
	
	public void depositMOney(int idAccount, BigDecimal amount, String transcDescription) {
		this.accountDao.depositMoney(idAccount, amount, transcDescription);
	}
	
	public void transferMoney(long fromAccountNumber,long toAccountNumber, BigDecimal amount, String transcDescription) {
		this.accountDao.transferMoney(fromAccountNumber,toAccountNumber , amount, transcDescription);
	}

	public AccountBean getAccountByUsername(String username) {
		return this.accountDao.getAccountByUsername(username);
		
	}
	
	public AccountBean getAccountByAccountNumber(long accountNumber) {
		return this.accountDao.getAccountByAccountNumber(accountNumber);
		
	}

	public void withdrawMoney(int accountId, BigDecimal withdrawtAmount,String transcDescription) {
		this.accountDao.withdraw(accountId, withdrawtAmount,transcDescription);
	}


	public List<TransactionBean> recentTransactions(int idAccount) {
		List<TransactionBean> transactions = this.accountDao.recentTransactions(idAccount);
		return transactions;
	
	}
	
	public List<TransactionBean> totalTransactions(int idAccount) {
		List<TransactionBean> transactions = this.accountDao.totalTransactions(idAccount);
		return transactions;
	
	}
	
	
	
	/*
	 * public TransactionBean getCurrentMonthDbAmount(int idAccount) { return
	 * this.accountDao.getCurrentMonthDbAmount(idAccount); }
	 * 
	 */

}
