package com.bankofprairies.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TransactionBean {
	
	int idTransaction;
	BigDecimal transcAmount;
	String transacDesc;
	String transacType;
	Date transacDate;
	
	BigDecimal totalDebit;
	BigDecimal totalCredit;
	
	AccountBean account;
	
	public TransactionBean() {
		// TODO Auto-generated constructor stub
	}

	
	
	
	public TransactionBean(int idTransaction, BigDecimal transcAmount, String transacDesc, String transacType,
			Date transacDate, BigDecimal totalDebit, BigDecimal totalCredit, AccountBean account) {
		super();
		this.idTransaction = idTransaction;
		this.transcAmount = transcAmount;
		this.transacDesc = transacDesc;
		this.transacType = transacType;
		this.transacDate = transacDate;
		this.totalDebit = totalDebit;
		this.totalCredit = totalCredit;
	}




	public int getIdTransaction() {
		return idTransaction;
	}
	public void setIdTransaction(int idTransaction) {
		this.idTransaction = idTransaction;
	}
	public BigDecimal getTranscAmount() {
		return transcAmount;
	}
	public void setTranscAmount(BigDecimal transcAmount) {
		this.transcAmount = transcAmount;
	}
	public String getTransacDesc() {
		return transacDesc;
	}
	public void setTransacDesc(String transacDesc) {
		this.transacDesc = transacDesc;
	}
	public String getTransacType() {
		return transacType;
	}
	public void setTransacType(String transacType) {
		this.transacType = transacType;
	}
	public Date getTransacDate() {
		return transacDate;
	}
	public void setTransacDate(Date transacDate) {
		this.transacDate = transacDate;
	}

	public BigDecimal getTotalDebit() {
		return totalDebit;
	}

	public void setTotalDebit(BigDecimal totalDebit) {
		this.totalDebit = totalDebit;
	}

	public BigDecimal getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(BigDecimal totalCredit) {
		this.totalCredit = totalCredit;
	}

		
	public AccountBean getAccount() {
		return account;
	}


	public void setAccount(AccountBean account) {
		this.account = account;
	}




	@Override
	public String toString() {
		return "TransactionBean [idTransaction=" + idTransaction + ", transcAmount=" + transcAmount + ", transacDesc="
				+ transacDesc + ", transacType=" + transacType + ", transacDate=" + transacDate + ", totalDebit="
				+ totalDebit + ", totalCredit=" + totalCredit + ", account=" + account + "]";
	}




	
	

	

}
