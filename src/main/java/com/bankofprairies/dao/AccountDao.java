package com.bankofprairies.dao;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.bean.TransactionBean;
import com.bankofprairies.dao.mapper.AccountMapper;
import com.bankofprairies.dao.mapper.TransactionMapper;

@Repository
public class AccountDao {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	TransactionBean transactionBean;
	
	List<TransactionBean> transactions;
	
	
	public List<TransactionBean> getListByDebit() {
	    String sql = "SELECT * \r\n"
	    		+ "FROM bank_of_prairies.transaction \r\n"
	    		+ "WHERE TR_TYPE = 'Debit' \r\n"
	    		+ "AND YEAR(TR_DATE) = YEAR(CURDATE()) \r\n"
	    		+ "AND MONTH(TR_DATE) = MONTH(CURDATE())";
	    return this.jdbcTemplate.query(sql, new TransactionMapper());	}
	
	
	// find account by customer id
		public AccountBean getAccountById(int idCustomer) {

			String sql = "SELECT * FROM bank_of_prairies.account A INNER JOIN bank_of_prairies.customer C ON C.CUSTOMER_ID = A.CUSTOMER_CUSTOMER_ID WHERE CUSTOMER_ID = ?;";
			
			return this.jdbcTemplate.queryForObject(sql, new AccountMapper(), idCustomer);

		}
		
		public AccountBean getAccountByUsername(String username) {

			String sql = "SELECT * FROM ACCOUNT A INNER JOIN CUSTOMER C ON C.CUSTOMER_ID = A.CUSTOMER_CUSTOMER_ID WHERE USERNAME = ?;";
			
			return this.jdbcTemplate.queryForObject(sql, new AccountMapper(), username);
		}
		
		//getAccountByAccountNumber
		
		public AccountBean getAccountByAccountNumber(long accountNumber) {

			String sql = "SELECT * FROM ACCOUNT A INNER JOIN CUSTOMER C ON C.CUSTOMER_ID = A.CUSTOMER_CUSTOMER_ID WHERE ACC_NUMBER = ?;";
			
			return this.jdbcTemplate.queryForObject(sql, new AccountMapper(), accountNumber);
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
		
		
		
		
		public Boolean withdraw(int idAccount, BigDecimal amount, String transcDescription) {
	        String sql = "UPDATE ACCOUNT SET ACC_BALANCE = ACC_BALANCE - ? WHERE ACCOUNT_ID = ?";
	        int rowsUpdated = jdbcTemplate.update(sql, amount, idAccount);
	        
	     	if (rowsUpdated==1) {
				this.creditInTransaction(amount, idAccount,transcDescription);
				return true;
			}
			return false;
	    }
	    
	    private void creditInTransaction(BigDecimal amount, int idAccount, String transcDescription) {
	    	String sql = "INSERT INTO TRANSACTION(TR_AMOUNT, TR_TYPE,TR_DESCRIPTION, TR_DATE, ACCOUNT_ID) VALUES (?,'Debit',?,NOW(),?)";
	        jdbcTemplate.update(sql, amount,transcDescription, idAccount);
		}
	    
		public Boolean depositMoney(int idAccount, BigDecimal amount, String transcDescription) {
	        String sql = "UPDATE ACCOUNT SET ACC_BALANCE = ACC_BALANCE + ? WHERE ACCOUNT_ID = ?";
	        int rowsUpdated = jdbcTemplate.update(sql, amount, idAccount);
	      	if (rowsUpdated==1) {
				this.depositInTransaction(amount, idAccount, transcDescription);
				return true;
			}
			return false;
	    }
	    
	    private void depositInTransaction(BigDecimal amount, int idAccount, String transcDescription) {
	    	String sql = "INSERT INTO TRANSACTION(TR_AMOUNT, TR_TYPE,TR_DESCRIPTION, TR_DATE, ACCOUNT_ID) VALUES (?,'Credit',?,NOW(),?)";
	        jdbcTemplate.update(sql, amount,transcDescription, idAccount);
		}

		public void transfer(int sourceAccountId, int destinationAccountId, BigDecimal amount) {
	        String sql = "UPDATE ACCOUNT SET ACC_BALANCE = ACC_BALANCE - ? WHERE ACCOUNT_ID = ?";
	        jdbcTemplate.update(sql, amount, sourceAccountId);

	        sql = "UPDATE ACCOUNT SET ACC_BALANCE = ACC_BALANCE + ? WHERE ACCOUNT_ID = ?";
	        jdbcTemplate.update(sql, amount, destinationAccountId);
	    }
		
		
		public List<TransactionBean> recentTransactions(int idAccount) {
			
			//String sql = "SELECT * FROM TRANSACTION WHERE ACCOUNT_ID = ? ORDER BY TR_DATE DESC LIMIT 5";
			String sql = "SELECT T.* , A.ACC_BALANCE FROM TRANSACTION T LEFT JOIN ACCOUNT A ON T.ACCOUNT_ID= A.ACCOUNT_ID WHERE A.ACCOUNT_ID = ? ORDER BY TR_DATE DESC LIMIT 5";
			
			return this.jdbcTemplate.query(sql, new TransactionMapper(),idAccount);
		}
		
		public List<TransactionBean> totalTransactions(int idAccount) {
			
			String sql = "SELECT T.* , A.ACC_BALANCE FROM TRANSACTION T LEFT JOIN ACCOUNT A ON T.ACCOUNT_ID= A.ACCOUNT_ID WHERE A.ACCOUNT_ID = ? ORDER BY TR_DATE";
			
			return this.jdbcTemplate.query(sql, new TransactionMapper(),idAccount);
		}

		@Transactional
		public boolean transferMoney(long fromAccountNumber, long toAccountNumber, BigDecimal amount,String transcDescription) {
			
			AccountBean fromAccount = getAccountByAccountNumber(fromAccountNumber);
			int fromAccountId = fromAccount.getIdAccount();
			
			AccountBean toAccount = getAccountByAccountNumber(toAccountNumber);
			int toAccountId = toAccount.getIdAccount();
			
			if(toAccount.getAccountBalance().compareTo(amount)<0) {
				return false;
			}
			
			 String sql = "UPDATE ACCOUNT SET ACC_BALANCE = ACC_BALANCE - ? WHERE ACCOUNT_ID = ?";
		     jdbcTemplate.update(sql, amount, fromAccountId);
		     
		     sql = "UPDATE ACCOUNT SET ACC_BALANCE = ACC_BALANCE + ? WHERE ACCOUNT_ID = ?";
		     jdbcTemplate.update(sql, amount, toAccountId);
		     
		     transferInTransaction(amount, "Debit", transcDescription, fromAccountId);
		     transferInTransaction(amount, "Credit", transcDescription, toAccountId);
		
		     return true;
		}
		
		private void transferInTransaction(BigDecimal amount, String type, String transcDescription, int idAccount) {
			String sql = "INSERT INTO TRANSACTION (TR_AMOUNT, TR_TYPE, TR_DESCRIPTION, TR_DATE, ACCOUNT_ID) " +
                    "VALUES (?, ?, ?, NOW(), ?)";
			jdbcTemplate.update(sql, amount, type, transcDescription, idAccount);
	    }
}