package com.bankofprairies.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.bean.CustomerBean;

public class CustomerMapper implements RowMapper<CustomerBean> {

	@Override
	public CustomerBean mapRow(ResultSet rs, int rowNum) throws SQLException {

		CustomerBean customer = new CustomerBean();
		
		customer.setIdCustomer(rs.getInt("CUSTOMER_ID"));
		customer.setFirstName(rs.getString("FIRST_NAME"));
		customer.setLastName(rs.getString("LAST_NAME"));
		customer.setBirth(rs.getDate("DOB"));
		customer.setGender(rs.getString("GENDER"));
		customer.setUsername(rs.getString("USERNAME"));
		customer.setPassword(rs.getString("PASSWORD"));
		customer.setEmail(rs.getString("EMAIL"));
		customer.setMobile(rs.getString("MOBILE"));
		customer.setSin(rs.getString("SIN"));
		customer.setStreet(rs.getString("STREET"));
		customer.setCity(rs.getString("CITY"));
		customer.setProvince(rs.getString("PROVINCE"));
		customer.setZip(rs.getString("ZIP"));
		customer.setCountry(rs.getString("COUNTRY"));
		customer.setRole(rs.getString("ROLE"));
		customer.setStatus(rs.getString("ACTIVE_STATUS"));
		
		AccountBean account = new AccountBean();
		account.setAccountNumber(rs.getLong("ACC_NUMBER"));
		
		customer.setAccount(account);
		
		
		return customer;
	}

}
