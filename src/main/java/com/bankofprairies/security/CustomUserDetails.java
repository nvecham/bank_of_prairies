package com.bankofprairies.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bankofprairies.bean.CustomerBean;
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	CustomerBean customer;
	
	public CustomUserDetails(CustomerBean customer) {
		super();
		this.customer = customer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		
		  List<GrantedAuthority> authorities = new ArrayList<>(); 
		  authorities.add(new SimpleGrantedAuthority("CUSTOMER")); 
		  authorities.add(new SimpleGrantedAuthority("ADMIN"));
		  return authorities;
		 
	    
	    
	  		
	}

	@Override
	public String getPassword() {
		return this.customer.getPassword();
	}

	@Override
	public String getUsername() {
		return this.customer.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return "Active".equalsIgnoreCase(this.customer.getStatus());
	}

}
