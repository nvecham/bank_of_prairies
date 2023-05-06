package com.bankofprairies.bean;

import java.util.Date;

public class CustomerBean {
	
	
	AccountBean account;

	int idCustomer;
	String firstName;
	String lastName;
	Date birth;
	String gender;
	String username;
	String password;
	String email;
	String mobile;
	String sin;
	String street;
	String city;
	String province;
	String zip;
	String country;
	String status;
	String role;
	
	public CustomerBean() {
		// TODO Auto-generated constructor stub
	}

	public CustomerBean(int idCustomer, String firstName, String lastName, Date birth, String gender, String username,
			String password, String email, String mobile, String sin, String street, String city, String province,
			String zip, String country, String status,String role) {
		super();
		this.idCustomer = idCustomer;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birth = birth;
		this.gender = gender;
		this.username = username;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
		this.sin = sin;
		this.street = street;
		this.city = city;
		this.province = province;
		this.zip = zip;
		this.country = country;
		this.status = status;
		this.role = role;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSin() {
		return sin;
	}

	public void setSin(String sin) {
		this.sin = sin;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "CustomerBean [account=" + account + ", idCustomer=" + idCustomer + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", birth=" + birth + ", gender=" + gender + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", mobile=" + mobile + ", sin=" + sin + ", street="
				+ street + ", city=" + city + ", province=" + province + ", zip=" + zip + ", country=" + country
				+ ", status=" + status + ", role=" + role + "]";
	}



	
	
	
	
	
	
	
	
	
	
	
}
