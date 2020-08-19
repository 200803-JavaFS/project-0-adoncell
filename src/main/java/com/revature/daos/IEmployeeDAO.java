package com.revature.daos;

import java.util.List;

import com.revature.models.Employee;

public interface IEmployeeDAO {

	boolean addEmployee(Employee e);
	
	public boolean validateUsername(String empLogin);
	
	public String checkUsername(String username);
	
	public Employee checkPassword(String empLogin, String empPassword);
	
	public List<Employee> viewPending();
	
	public List<Employee> viewByUser(String user);
	
	public Employee checkAccount(String user);

	public void updateEntry(Employee e);

}
