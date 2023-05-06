package com.bankofprairies.dao;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bankofprairies.bean.AccountBean;
import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.dao.mapper.AccountMapper;
import com.bankofprairies.dao.mapper.CustomerMapper;

@Repository
public class AccountDao {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// find account by customer id
		public AccountBean getAccountById(int idCustomer) {

			String sql = "SELECT * FROM bank_of_prairies.account A INNER JOIN bank_of_prairies.CUSTOMER C ON C.CUSTOMER_ID = A.CUSTOMER_CUSTOMER_ID WHERE CUSTOMER_ID = 25;";
			return this.jdbcTemplate.queryForObject(sql, new AccountMapper(), idCustomer);

		}

		

}
