package com.bankofprairies.dao;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.bean.CustomerBean;

@Repository
public class AccountDao {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void addAccount(AccountBean account) {
		
		/* ACCOUNT_ID int AI PK 
		ACC_NUMBER bigint 
		ACC_BALANCE double 
		DB_CARD_NUMBER bigint 
		DBCARD_VALID_TILL bigint 
		DB_CVV int 
		CREATED_DATE datetime 
		CREATED_BY varchar(45) 
		UPDATED_DATE datetime 
		UPDATED_BY varchar(45) 
		CUSTOMER_CUSTOMER_ID */
		
		String sql = "INSERT INTO ACCOUNT (ACC_NUMBER, ACC_BALANCE, DB_CARD_NUMBER, DBCARD_VALID_TILL, DB_CVV, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE"
				+ "VALUES (?,?,?,?,?,?,?,?,?)";
		this.jdbcTemplate.update(sql, account.getAccountNumber(),account.getAccountBalance(),account.getDebitCardNumber(),account.getDebitCardValidTill(),
				account.getDebitCvv(),account.getCreatedBy(),account.getCreatedDate(),account.getUpdatedBy(),account.getUpdatedDate());
		
		
		
	}
	
	private long generateAccountNumber(CustomerBean customer) {

		Random rand = new Random();
		long accountNumber = rand.nextLong() % 10000000000000000L;
		accountNumber += 10000000000000000L;

		return accountNumber;

	}

		

}
