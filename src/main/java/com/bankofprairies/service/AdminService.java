package com.bankofprairies.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.dao.CustomerDao;


@Service
public class AdminService {
	
	@Autowired
	CustomerDao customerDao;
	
	public List<CustomerBean> listCustomers() {
		
		List<CustomerBean> customers = this.customerDao.listCustomers();
		return customers;
	}
	
	public void addCustomer(CustomerBean customer) {
		
		this.validateCustomer(customer);
		this.customerDao.addCustomer(customer);
		
	}
	
	public CustomerBean findCustomer(int idCustomer) {
		
		return this.customerDao.findCustomer(idCustomer);
	}
	
	public void updateCustomer(CustomerBean customer) {
		
		this.validateCustomer(customer);
		
		this.customerDao.updateCustomer(customer);
	}
	
	
	  public void deleteCustomer(int idCustomer) {
	  
		  this.customerDao.deleteCustomer(idCustomer); 
	  
	  }
	 

	private void validateCustomer(CustomerBean customer) {
		
		if(customer.getFirstName().isEmpty() ||
		   customer.getLastName().isEmpty() ||
		   customer.getEmail().isEmpty()) {
			
			throw new RuntimeException("Invalid Customer data");
		}
	}
	
	
	
}
