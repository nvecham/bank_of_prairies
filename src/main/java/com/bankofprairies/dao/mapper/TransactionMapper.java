package com.bankofprairies.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bankofprairies.bean.TransactionBean;

public class TransactionMapper implements RowMapper<TransactionBean>{

	@Override
	public TransactionBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TransactionBean transaction = new TransactionBean();
		
		/* 
		 * int idTransaction;
	BigDecimal transcAmount;
	String transacDesc;
	String transacType;
	Date transacDate;
		 * */
		transaction.setIdTransaction(rs.getInt("TRANSACTION_ID"));
		transaction.setTranscAmount(rs.getBigDecimal("TR_AMOUNT"));
		transaction.setTransacDesc(rs.getString("TR_DESCRIPTION"));
		transaction.setTransacType(rs.getString("TR_TYPE"));
		transaction.setTransacDate(rs.getDate("TR_DATE"));
		
		//transaction.setTotalDebit(rs.getBigDecimal("TOTALDEBIT"));
		
		return transaction;
	}

}
