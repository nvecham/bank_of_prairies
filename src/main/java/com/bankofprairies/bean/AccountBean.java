package com.bankofprairies.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class AccountBean {
	
	/* ACCOUNT_ID int AI PK 
ACC_NUMBER bigint 
ACC_BALANCE double 
DB_CARD_NUMBER bigint 
DBCARD_VALID_TILL bigint 
DB_CVV int 
CREATED_DATE datetime 
CREATED_BY varchar(45) 
UPDATED_DATE datetime 
UPDATED_BY varchar(45) 
CUSTOMER_CUSTOMER_ID */
	
	int idAccount;
	private long accountNumber;
	private BigDecimal accountBalance;
	private long debitCardNumber;
	private Date debitCardValidTill;
	int debitCvv;
	String createdBy;
	Date createdDate;
	String updatedBy;
	Date updatedDate;
	
	public AccountBean() {
		// TODO Auto-generated constructor stub
	}

	public AccountBean(int idAccount, long accountNumber, BigDecimal accountBalance, long debitCardNumber,
			Date debitCardValidTill, int debitCvv, String createdBy, Date createdDate, String updatedBy,
			Date updatedDate) {
		super();
		this.idAccount = idAccount;
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
		this.debitCardNumber = debitCardNumber;
		this.debitCardValidTill = debitCardValidTill;
		this.debitCvv = debitCvv;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	public int getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal d) {
		this.accountBalance = d;
	}

	public long getDebitCardNumber() {
		return debitCardNumber;
	}

	public void setDebitCardNumber(long debitCardNumber) {
		this.debitCardNumber = debitCardNumber;
	}

	public Date getDebitCardValidTill() {
		return debitCardValidTill;
	}

	public void setDebitCardValidTill(Date debitCardValidTill) {
		this.debitCardValidTill = debitCardValidTill;
	}

	public int getDebitCvv() {
		return debitCvv;
	}

	public void setDebitCvv(int debitCvv) {
		this.debitCvv = debitCvv;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "AccountBean [idAccount=" + idAccount + ", accountNumber=" + accountNumber + ", accountBalance="
				+ accountBalance + ", debitCardNumber=" + debitCardNumber + ", debitCardValidTill=" + debitCardValidTill
				+ ", debitCvv=" + debitCvv + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + "]";
	}
	
	
	
	

}
