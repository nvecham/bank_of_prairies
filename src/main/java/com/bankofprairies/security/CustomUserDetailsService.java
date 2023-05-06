package com.bankofprairies.security;



import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankofprairies.bean.CustomerBean;
import com.bankofprairies.dao.CustomerDao;



@Service("UserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		/*
		 * CustomerBean customer = this.customerDao.findByUsername(username);
		 * 
		 * if(customer==null) {
		 * 
		 * throw new UsernameNotFoundException("User not found: " +username); }
		 * 
		 * return new CustomUserDetails(customer);
		 */
		 
		
		
		
		  String sql = "SELECT * FROM CUSTOMER WHERE USERNAME = ?";
		  
		  List<CustomerBean> customers = jdbcTemplate.query(sql, new
		  BeanPropertyRowMapper<>(CustomerBean.class), username);
		  
		  CustomerBean customer = customers.get(0); Set<GrantedAuthority> authorities =
		  new HashSet<>(); authorities.add(new
		  SimpleGrantedAuthority(customer.getRole()));
		  
		  logger.debug("Hi from CUDS");
		 
		  
		  //return new CustomUserDetails(customer);
		  
		  return new User(customer.getUsername(), customer.getPassword(), authorities);
		 
		
		
    
	}
}
