package com.bankofprairies.dao;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.dao.mapper.CustomerMapper;

@Repository
public class CustomerDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	List<CustomerBean> customers;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<CustomerBean> listCustomers() {

		String sql = "SELECT * FROM CUSTOMER";

		return jdbcTemplate.query(sql, new CustomerMapper());

	}

	public void addCustomer(CustomerBean customer) {

		String sql = "INSERT INTO CUSTOMER (FIRST_NAME, LAST_NAME, DOB, GENDER, USERNAME, PASSWORD, EMAIL, MOBILE, SIN, STREET, CITY, PROVINCE, ZIP, COUNTRY, ACTIVE_STATUS)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		this.jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName(), customer.getBirth(),
				customer.getGender(), customer.getUsername(), customer.getPassword(), customer.getEmail(),
				customer.getMobile(), customer.getSin(), customer.getStreet(), customer.getCity(),
				customer.getProvince(), customer.getZip(), customer.getCountry(), customer.getStatus());

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

	private long generateAccountNumber(CustomerBean customer) {

		Random rand = new Random();
		long accountNumber = rand.nextLong() % 10000000000000000L;
		accountNumber += 10000000000000000L;

		return accountNumber;

	}

}
