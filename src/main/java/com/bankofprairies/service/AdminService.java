package com.bankofprairies.service;

import java.util.List;

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

}
