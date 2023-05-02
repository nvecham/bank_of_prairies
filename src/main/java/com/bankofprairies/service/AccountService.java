package com.bankofprairies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.dao.AccountDao;

@Service
public class AccountService {
	
	@Autowired
	AccountDao accountDao; 
	
	public void addAccount(AccountBean account) {
		
		this.accountDao.addAccount(account);
	}

}
