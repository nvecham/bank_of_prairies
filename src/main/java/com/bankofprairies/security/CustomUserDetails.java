package com.bankofprairies.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.customer.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
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
