package com.bankofprairies.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.dao.AccountDao;

@Service
public class AccountService {

	@Autowired
	AccountDao accountDao;

	public AccountBean getAccountById(int idCustomer) {

		return this.accountDao.getAccountById(idCustomer);
	}
	
	public void depositMOney(int idAccount, BigDecimal amount) {
		this.accountDao.depositMoney(idAccount, amount);
	}

	public AccountBean getAccountByUsername(String username) {
		return this.accountDao.getAccountByUsername(username);
		
	}

	public void withdrawMoney(int accountId, BigDecimal withdrawtAmount) {
		this.accountDao.withdraw(accountId, withdrawtAmount);
	}

}
