package com.bankofprairies.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		if (roles.contains("ADMIN")) {
			response.sendRedirect("/adminDashboard");
			return;
		} else if (roles.contains("CUSTOMER")) {
			response.sendRedirect("/customerDashboard");
			return;
		} else {
			throw new IllegalStateException("User has no role assigned.");
		}

	}

	/*
	 * @Override public void onAuthenticationSuccess(HttpServletRequest request,
	 * HttpServletResponse response, Authentication authentication) throws
	 * IOException, ServletException {
	 * 
	 * Set<String> roles =
	 * AuthorityUtils.authorityListToSet(authentication.getAuthorities());
	 * 
	 * if (roles.contains("ADMIN")) { // Redirect to the admin dashboard if user has
	 * ROLE_ADMIN getRedirectStrategy().sendRedirect(request, response,
	 * "/adminDashboard"); } else if (roles.contains("CUSTOMER")) { // Redirect to
	 * the user dashboard if user has ROLE_USER
	 * getRedirectStrategy().sendRedirect(request, response, "/customerDashboard");
	 * } else { super.onAuthenticationSuccess(request, response, authentication); }
	 * }
	 */

}
