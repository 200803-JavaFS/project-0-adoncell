package com.revature.daos;

import java.util.List;

import com.revature.models.Customer0;

public interface IAccountDAO {
	
	public String checkUsername(String username);

	public boolean addCustomer(Customer0 c);
	
	public Customer0 checkPassword(String userLogin, String userPassword);
	
	public boolean validateUsername(String userLogin);
	
	public Customer0 checkTransferAccount(String transferUser);
	
	public List<Customer0> viewAll();
	
	public List<Customer0> viewOpen();
	
	public List<Customer0> viewClosed();
	
	public List<Customer0> viewPending();

	public void updateEntry(Customer0 c);

}
