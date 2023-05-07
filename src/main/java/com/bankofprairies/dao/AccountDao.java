package com.bankofprairies.dao;

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

			String sql = "SELECT * FROM ACCOUNT A INNER JOIN CUSTOMER C ON C.CUSTOMER_ID = A.CUSTOMER_CUSTOMER_ID WHERE CUSTOMER_ID = ?;";
			
			return this.jdbcTemplate.queryForObject(sql, new AccountMapper(), idCustomer);

		}

		

}
