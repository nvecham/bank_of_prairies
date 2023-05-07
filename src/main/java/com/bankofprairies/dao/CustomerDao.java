package com.bankofprairies.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.dao.mapper.CustomerMapper;
import com.bankofprairies.util.Util;

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

	// createCustomerAndAccount

	@Transactional
	public void addCustomer(CustomerBean customer) {

		Date birth = customer.getBirth();
		long accountNumber = generateAccountNumber();
		long debitCardNumber = generateDebitCardNumber();
		int debitCvv = generateDebitCVV();
		Date debitCardValidTill = getDebitcardValidTillDate();
		BigDecimal initialAccountBalance = BigDecimal.valueOf(100.00);

		// Insert the customer first
		String customerSql = "INSERT INTO CUSTOMER (FIRST_NAME, LAST_NAME, DOB, GENDER, USERNAME, PASSWORD, EMAIL, MOBILE, SIN, STREET, CITY, PROVINCE, ZIP, COUNTRY, ACTIVE_STATUS, ROLE)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder customerKeyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(customerSql, new String[] { "CUSTOMER_ID" });
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setDate(3, new java.sql.Date(birth.getTime()));
			ps.setString(4, customer.getGender());
			ps.setString(5, customer.getUsername());
			ps.setString(6, customer.getPassword());
			ps.setString(7, customer.getEmail());
			ps.setString(8, customer.getMobile());
			ps.setString(9, customer.getSin());
			ps.setString(10, customer.getStreet());
			ps.setString(11, customer.getCity());
			ps.setString(12, customer.getProvince());
			ps.setString(13, customer.getZip());
			ps.setString(14, customer.getCountry());
			ps.setString(15, customer.getStatus());
			ps.setString(16, customer.getRole());

			return ps;
		}, customerKeyHolder);

		// Get the generated customer id
		int customerId = customerKeyHolder.getKey().intValue();

		// Insert the account with the generated customer id
		String accountSql = "INSERT INTO ACCOUNT (ACC_NUMBER, ACC_BALANCE, DB_CARD_NUMBER, DBCARD_VALID_TILL, DB_CVV, CREATED_BY, CREATED_DATE, CUSTOMER_CUSTOMER_ID) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";

		this.jdbcTemplate.update(accountSql, accountNumber, initialAccountBalance, debitCardNumber, debitCardValidTill,
				debitCvv, "Admin", customerId);
	}

	/*
	 * @Transactional public void addCustomer(CustomerBean customer) {
	 * 
	 * long accountNumber = generateAccountNumber(); long debitCardNumber =
	 * generateDebitCardNumber(); int debitCvv = generateDebitCVV(); Date
	 * debitCardValidTill = getDebitcardValidTillDate(); BigDecimal
	 * initialAccountBalance = BigDecimal.valueOf(100.00); // LocalDateTime now
	 * =LocalDateTime.now();
	 * 
	 * // Insert the customer into the database
	 * 
	 * String customerSql =
	 * "INSERT INTO CUSTOMER (FIRST_NAME, LAST_NAME, DOB, GENDER, USERNAME, PASSWORD, EMAIL, MOBILE, SIN, STREET, CITY, PROVINCE, ZIP, COUNTRY, ACTIVE_STATUS)"
	 * + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	 * 
	 * KeyHolder customerKeyHolder = new GeneratedKeyHolder();
	 * 
	 * this.jdbcTemplate.update(customerSql, customer.getFirstName(),
	 * customer.getLastName(), customer.getBirth(), customer.getGender(),
	 * customer.getUsername(), customer.getPassword(), customer.getEmail(),
	 * customer.getMobile(), customer.getSin(), customer.getStreet(),
	 * customer.getCity(), customer.getProvince(), customer.getZip(),
	 * customer.getCountry(), customer.getStatus());
	 * 
	 * // Insert the account into the database with the associated customer ID
	 * 
	 * String accountSql =
	 * "INSERT INTO ACCOUNT (ACC_NUMBER, ACC_BALANCE, DB_CARD_NUMBER, DBCARD_VALID_TILL, DB_CVV, CREATED_BY, CREATED_DATE,CUSTOMER_CUSTOMER_ID)"
	 * + "VALUES (?,?,?,?,?,?,NOW(),?)";
	 * 
	 * this.jdbcTemplate.update(accountSql, accountNumber, initialAccountBalance,
	 * debitCardNumber, debitCardValidTill, debitCvv, "Admin",
	 * customer.getIdCustomer());
	 * 
	 * }
	 */

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

	// find customer by customer id
	public CustomerBean findCustomer(int idCustomer) {

		String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
		return this.jdbcTemplate.queryForObject(sql, new CustomerMapper(), idCustomer);

	}

	// find customer by customer username
	public CustomerBean findByUsername(String username) {

		String sql = "SELECT * FROM CUSTOMER WHERE USERNAME = ?";
		return this.jdbcTemplate.queryForObject(sql, new CustomerMapper(), username);

	}
	


	/*
	 * // To retrieve role of a customer private String determineUserRole(String
	 * username, String password) { String sql =
	 * "SELECT role FROM users WHERE username = ? AND password = ?"; String role =
	 * jdbcTemplate.queryForObject(sql, String.class, username, password); return
	 * role;
	 * 
	 * 
	 * try { String role = jdbcTemplate.queryForObject(sql, String.class, username,
	 * password); return role; } catch (EmptyResultDataAccessException e) { // User
	 * not found or password incorrect return null; }
	 * 
	 * }
	 */

	public void updateCustomer(CustomerBean customer) {
		String sql = "UPDATE CUSTOMER SET FIRST_NAME = ?, LAST_NAME = ?, DOB = ?, GENDER = ?, USERNAME = ?, EMAIL = ?, MOBILE = ?, SIN = ?, STREET = ?,"
				+ "CITY = ?, PROVINCE = ?, ZIP = ?, COUNTRY = ?, ACTIVE_STATUS = ? , ROLE = ?  WHERE CUSTOMER_ID = ?";

		int status = this.jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName(), customer.getBirth(),
				customer.getGender(), customer.getUsername(), customer.getEmail(), customer.getMobile(),
				customer.getSin(), customer.getStreet(), customer.getCity(), customer.getProvince(), customer.getZip(),
				customer.getCountry(), customer.getStatus(), customer.getRole(), customer.getIdCustomer());

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
