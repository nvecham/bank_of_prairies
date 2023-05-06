package com.bankofprairies.bean;

public class RoleBean {
	
	int idRole;
	String nameRole;
	
	
	
	public RoleBean() {
		// TODO Auto-generated constructor stub
	}

	public RoleBean(int idRole, String nameRole) {
		super();
		this.idRole = idRole;
		this.nameRole = nameRole;
	}

	public int getIdRole() {
		return idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public String getNameRole() {
		return nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	@Override
	public String toString() {
		return "RoleBean [idRole=" + idRole + ", nameRole=" + nameRole + "]";
	}
	
	

}
