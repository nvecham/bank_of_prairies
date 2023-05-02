package com.bankofprairies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.bankofprairies.security.CustomPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/js/**","/img/**","/CSS/**")
				                                .permitAll().anyRequest().authenticated())
		                          .formLogin(form -> form.loginPage("/login").permitAll())
		                          .logout(logout-> logout.permitAll())
		                          .csrf().disable();
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
	    //return new BCryptPasswordEncoder();
		return new CustomPasswordEncoder();
	}
	

	
	/*
	 * @Bean UserDetailsService userDetailsService() {
	 * 
	 * UserDetails user = User.builder() .username("admin")
	 * .password(this.passwordEncoder().encode("admin123")) .roles("USER").build();
	 * 
	 * return new InMemoryUserDetailsManager(user); }
	 */
	
	
	
}
