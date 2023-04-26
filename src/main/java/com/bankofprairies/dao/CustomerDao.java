package com.bankofprairies.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.util.Util;

@Repository
public class CustomerDao {
	
	List<CustomerBean> customers;
	
	public CustomerDao() {
		this.customers = new ArrayList<CustomerBean>();
		/*(int idCustomer, 
		String firstName, 
		String lastName, 
		Date birth, 
		String gender, 
		String email,
		String mobile, 
		String sin, 
		String street, 
		String city, String zip, 
		String country, 
		String status) */
	
		//customers.add(customer);
		customers.add(new CustomerBean(2, "Nikhila" ,"Vecham" ,Util.parseDate("1990-02-02") ,"Female", "abc@gmail.com", "123-456-7890", "12345678", "123 Google street", "Regina","Saskatchewan", "S4W0E5", "Canada", "A"));
	}
	
	public List<CustomerBean> listCustomers(){
		
		return this.customers;
		
	}
	
	

}
