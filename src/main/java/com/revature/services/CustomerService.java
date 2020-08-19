package com.revature.services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.AccountDAO;
import com.revature.daos.IAccountDAO;
import com.revature.models.Customer0;

public class CustomerService {
	
	private static IAccountDAO dao = new AccountDAO();
	private static final Logger log = LogManager.getLogger(CustomerService.class);

	public boolean insertCustomer(Customer0 c) {
		log.info("Inserting New Account Application" + c);
		if (dao.addCustomer(c)) {
			return true;
		}
		return false;
	}

}
