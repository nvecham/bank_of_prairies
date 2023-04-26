package com.bankofprairies.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bankofprairies.bean.CustomerBean;

public class CustomerMapper implements RowMapper<CustomerBean> {

	@Override
	public CustomerBean mapRow(ResultSet rs, int rowNum) throws SQLException {

		CustomerBean customer = new CustomerBean();
		
		//customer.setIdCustomer(rs.getInt());
		
		
		return null;
	}

}
