package com.bankofprairies.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.dao.mapper.AccountMapper;

@Repository
public class AccountDao {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// find account by customer id
		public AccountBean getAccountById(int idCustomer) {

			String sql = "SELECT * FROM bank_of_prairies.account A INNER JOIN bank_of_prairies.customer C ON C.CUSTOMER_ID = A.CUSTOMER_CUSTOMER_ID WHERE CUSTOMER_ID = ?;";
			
			return this.jdbcTemplate.queryForObject(sql, new AccountMapper(), idCustomer);

		}
		
		public AccountBean getAccountByUsername(String username) {

			String sql = "SELECT * FROM ACCOUNT A INNER JOIN CUSTOMER C ON C.CUSTOMER_ID = A.CUSTOMER_CUSTOMER_ID WHERE USERNAME = ?;";
			
			return this.jdbcTemplate.queryForObject(sql, new AccountMapper(), username);
		}
		
		/*
		 * int idAccount; 
		 * private long accountNumber; 
		 * private BigDecimal accountBalance;
		 * private long debitCardNumber; 
		 * private Date debitCardValidTill; 
		 * int debitCvv;
		 * String createdBy; Date createdDate; 
		 * String updatedBy; Date updatedDate;
		 */
		
		public Boolean withdraw(int idAccount, BigDecimal amount) {
	        String sql = "UPDATE ACCOUNT SET ACC_BALANCE = ACC_BALANCE - ? WHERE ACCOUNT_ID = ?";
	        int rowsUpdated = jdbcTemplate.update(sql, amount, idAccount);
	        
	     	if (rowsUpdated==1) {
				this.creditInTransaction(amount, idAccount);
				return true;
			}
			return false;
	    }

	    
	    private void creditInTransaction(BigDecimal amount, int idAccount) {
	    	String sql = "INSERT INTO TRANSACTION(TR_AMOUNT, TR_TYPE, TR_DATE, ACCOUNT_ID) VALUES (?,'DEBIT',NOW(),?)";
	        jdbcTemplate.update(sql, amount, idAccount);
		}

	    
		public Boolean depositMoney(int idAccount, BigDecimal amount) {
	        String sql = "UPDATE ACCOUNT SET ACC_BALANCE = ACC_BALANCE + ? WHERE ACCOUNT_ID = ?";
	        int rowsUpdated = jdbcTemplate.update(sql, amount, idAccount);
	      	if (rowsUpdated==1) {
				this.depositInTransaction(amount, idAccount);
				return true;
			}
			return false;
	    }

	    

	    
	    private void depositInTransaction(BigDecimal amount, int idAccount) {
	    	String sql = "INSERT INTO TRANSACTION(TR_AMOUNT, TR_TYPE, TR_DATE, ACCOUNT_ID) VALUES (?,'CREDIT',NOW(),?)";
	        jdbcTemplate.update(sql, amount, idAccount);
		}

		public void transfer(int sourceAccountId, int destinationAccountId, BigDecimal amount) {
	        String sql = "UPDATE ACCOUNT SET ACC_BALANCE = ACC_BALANCE - ? WHERE ACCOUNT_ID = ?";
	        jdbcTemplate.update(sql, amount, sourceAccountId);

	        sql = "UPDATE ACCOUNT SET ACC_BALANCE = ACC_BALANCE + ? WHERE ACCOUNT_ID = ?";
	        jdbcTemplate.update(sql, amount, destinationAccountId);
	    }

	

		

}
