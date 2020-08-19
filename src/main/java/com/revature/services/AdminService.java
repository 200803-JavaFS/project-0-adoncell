package com.revature.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.AdminDAO;
import com.revature.daos.IAdminDAO;
import com.revature.models.Admin;
import com.revature.models.Employee;

public class AdminService {
	
	private static IAdminDAO dao = new AdminDAO();
	private static final Logger log = LogManager.getLogger(EmployeeService.class);

	public boolean insertAdmin(Admin a) {
		log.info("Inserting New Admin Application" + a);
		if (dao.addAdmin(a)) {
			return true;
		}
		return false;
	}

}
