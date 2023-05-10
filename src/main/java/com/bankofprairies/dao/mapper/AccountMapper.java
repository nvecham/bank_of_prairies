package com.bankofprairies.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bankofprairies.bean.AccountBean;

public class AccountMapper implements RowMapper<AccountBean> {

	@Override
	public AccountBean mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		AccountBean account = new AccountBean();
		
		/* int idAccount;
	private long accountNumber;
	private double accountBalance;
	private long debitCardNumber;
	private Date debitcardValidTill;
	int debitCvv;
	String createdBy;
	Date createdDate;
	String updatedBy;
	Date updatedDate */
		
		account.setIdAccount(rs.getInt("ACCOUNT_ID"));
		account.setAccountNumber(rs.getLong("ACC_NUMBER"));
		account.setAccountBalance(rs.getBigDecimal("ACC_BALANCE"));
		account.setDebitCardNumber(rs.getLong("DB_CARD_NUMBER"));
		account.setDebitCardValidTill(rs.getDate("DBCARD_VALID_TILL"));
		account.setDebitCvv(rs.getInt("DB_CVV"));
		account.setCreatedBy(rs.getString("CREATED_BY"));
		account.setCreatedDate(rs.getDate("CREATED_DATE"));
		account.setUpdatedBy(rs.getString("UPDATED_BY"));
		account.setUpdatedDate(rs.getDate("UPDATED_DATE"));
		
		return account;
	}

}
