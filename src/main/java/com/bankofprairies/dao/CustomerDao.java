package com.bankofprairies.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.dao.mapper.CustomerMapper;

@Repository
public class CustomerDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	List<CustomerBean> customers;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	AccountBean account;

	public List<CustomerBean> listCustomers() {

		String sql = "SELECT * FROM CUSTOMER";

		return jdbcTemplate.query(sql, new CustomerMapper());

	}
	
	@Transactional
	public void addCustomer(CustomerBean customer) {
		
		long accountNumber = generateAccountNumber();
		long debitCardNumber = generateDebitCardNumber();
		int debitCvv = generateDebitCVV();
		Date debitCardValidTill = getDebitcardValidTillDate();
		BigDecimal initialAccountBalance = BigDecimal.valueOf(100.00);
		//LocalDateTime now = LocalDateTime.now();
		
 
		//Insert the customer into the database		
				
		String customerSql = "INSERT INTO CUSTOMER (FIRST_NAME, LAST_NAME, DOB, GENDER, USERNAME, PASSWORD, EMAIL, MOBILE, SIN, STREET, CITY, PROVINCE, ZIP, COUNTRY, ACTIVE_STATUS)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		KeyHolder customerKeyHolder = new GeneratedKeyHolder();
		
		this.jdbcTemplate.update(customerSql, customer.getFirstName(), customer.getLastName(), customer.getBirth(),
				customer.getGender(), customer.getUsername(), customer.getPassword(), customer.getEmail(),
				customer.getMobile(), customer.getSin(), customer.getStreet(), customer.getCity(),
				customer.getProvince(), customer.getZip(), customer.getCountry(), customer.getStatus());
		
		// Insert the account into the database with the associated customer ID
		
		
		
		String accountSql = "INSERT INTO ACCOUNT (ACC_NUMBER, ACC_BALANCE, DB_CARD_NUMBER, DBCARD_VALID_TILL, DB_CVV, CREATED_BY, CREATED_DATE,CUSTOMER_CUSTOMER_ID)"
				+ "VALUES (?,?,?,?,?,?,NOW(),?)";
		/*this.jdbcTemplate.update(accountSql, accountNumber,account.setAccountBalance(BigDecimal.ZERO),account.getDebitCardNumber(),account.getDebitCardValidTill(),
				account.getDebitCvv(),account.getCreatedBy(),account.getCreatedDate(),account.getUpdatedBy(),account.getUpdatedDate(),customer.getIdCustomer());*/

		this.jdbcTemplate.update(accountSql, accountNumber,initialAccountBalance,debitCardNumber,debitCardValidTill,debitCvv,"Admin",customer.getIdCustomer());
	}

	// Generate a random 12 digit account number for a newly registered customer
	private long generateAccountNumber() {

		Random random = new Random();
        long accountNumber = random.nextLong() % 1000000000000L;
        if (accountNumber < 0) {
            accountNumber += 1000000000000L;
        }
        return accountNumber;
		
	}
	
	 // Generate a random debit card number for a newly registered customer
    private long generateDebitCardNumber() {
        Random random = new Random();
        long debitCardNumber = random.nextLong() % 10000000000000000L;
        if (debitCardNumber < 0) {
            debitCardNumber += 10000000000000000L;
        }
        return debitCardNumber;
    }
    
    public static int generateDebitCVV() {
        Random rand = new Random();
        int debitCvv = rand.nextInt(900) + 100; // Generates a random number between 100 and 999
        return debitCvv;
    }
    
    public static Date getDebitcardValidTillDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 5); // Add 5 years to the current date
        Date debitcardValidTill = calendar.getTime();
        return debitcardValidTill;
    }
    
    

	public CustomerBean findCustomer(int idCustomer) {

		String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
		return this.jdbcTemplate.queryForObject(sql, new CustomerMapper(), idCustomer);

	}

	public void updateCustomer(CustomerBean customer) {

		/*
		 * String sql = "UPDATE USER SET USERNAME = ?, FIRST_NAME = ?, " +
		 * "LAST_NAME = ?, BIRTH = ? WHERE ID_USER = ?";
		 */

		String sql = "UPDATE CUSTOMER SET FIRST_NAME = ?, LAST_NAME = ?, DOB = ?, GENDER = ?, USERNAME = ?, EMAIL = ?, MOBILE = ?, SIN = ?, STREET = ?,"
				+ "CITY = ?, PROVINCE = ?, ZIP = ?, COUNTRY = ?, ACTIVE_STATUS = ? WHERE CUSTOMER_ID = ?";

		int status = this.jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName(), customer.getBirth(),
				customer.getGender(), customer.getUsername(), customer.getEmail(), customer.getMobile(),
				customer.getSin(), customer.getStreet(), customer.getCity(), customer.getProvince(), customer.getZip(),
				customer.getCountry(), customer.getStatus(), customer.getIdCustomer());

		if (status == 0) {
			logger.error("Error while updating user: ", customer);
		}

	}

	public void deleteCustomer(int idCustomer) {

		String sql = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";

		int status = this.jdbcTemplate.update(sql, idCustomer);

		if (status == 0) {
			logger.error("Error while deleting user: ", idCustomer);
		}

	}

	

	
	
	
	
	

}
