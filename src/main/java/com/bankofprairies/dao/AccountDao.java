package com.bankofprairies.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
	
	/*
	 * public TransactionBean getTransactionByAccountId(int idAccount) { String sql
	 * = "SELECT * FROM bank_of_prairies.transaction where account_id = ?;"; return
	 * this.jdbcTemplate.queryForObject(sql, new TransactionMapper(),idAccount ); }
	 */
	
	
	/*
	 * public TransactionBean getCurrentMonthDbAmount(int idAccount) {
	 * 
	 * String sql =
	 * "SELECT SUM(TR_AMOUNT) AS TOTALDEBIT FROM bank_of_prairies.transaction WHERE TR_TYPE = 'Debit' AND ACCOUNT_ID = ? AND YEAR(TR_DATE) = YEAR(CURDATE()) AND MONTH(TR_DATE) = MONTH(CURDATE())"
	 * ; return this.jdbcTemplate.queryForObject(sql, new TransactionMapper(),
	 * idAccount);
	 * 
	 * }
	 */
	
	/*
	 * public TransactionBean getCurrentMonthDbAmount(int idAccount) { String sql
	 * ="SELECT * FROM bank_of_prairies.transaction t inner join\r\n" + "\r\n" +
	 * "(SELECT ACCOUNT_ID,SUM(TR_AMOUNT) AS TOTALBEBIT\r\n" +
	 * "FROM bank_of_prairies.transaction \r\n" + "WHERE TR_TYPE = 'Debit' \r\n" +
	 * "AND ACCOUNT_ID = ? \r\n" + "AND YEAR(TR_DATE) = YEAR(CURDATE()) \r\n" +
	 * "AND MONTH(TR_DATE) = MONTH(CURDATE()) group by account_id) t1 on t1.ACCOUNT_ID = t.account_id;"
	 * ; return this.jdbcTemplate.queryForObject(sql,new
	 * TransactionMapper(),idAccount);
	 * 
	 * }
	 */
	

	
	
	
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
			
			String sql = "SELECT * FROM TRANSACTION WHERE ACCOUNT_ID = ? ORDER BY TR_DATE DESC LIMIT 5";
			
			return this.jdbcTemplate.query(sql, new TransactionMapper(),idAccount);
		}
		
		public List<TransactionBean> totalTransactions(int idAccount) {
			
			String sql = "SELECT * FROM TRANSACTION WHERE ACCOUNT_ID = ? ORDER BY TR_DATE";
			
			return this.jdbcTemplate.query(sql, new TransactionMapper(),idAccount);
		}
		
	
	

		

}
