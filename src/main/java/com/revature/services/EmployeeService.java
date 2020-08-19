package com.revature.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.EmployeeDAO;
import com.revature.daos.IEmployeeDAO;
import com.revature.models.Employee;

public class EmployeeService {
	
	private static IEmployeeDAO dao = new EmployeeDAO();
	private static final Logger log = LogManager.getLogger(EmployeeService.class);

	public boolean insertEmployee(Employee e) {
		log.info("Inserting New Account Application" + e);
		if (dao.addEmployee(e)) {
			return true;
		}
		return false;
	}
	
	

}
