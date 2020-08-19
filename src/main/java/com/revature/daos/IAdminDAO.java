package com.revature.daos;

import java.util.List;

import com.revature.models.Admin;

public interface IAdminDAO {

	boolean addAdmin(Admin a);
	
	public boolean validateUsername(String adminLogin);
	
	public Admin checkPassword(String adminLogin, String adminPassword);
	
	public String checkUsername(String username);
	
	public List<Admin> viewPending();
	
	public List<Admin> viewByUser(String user);
	
	public Admin checkAccount(String user);

	public void updateEntry(Admin a);

}
