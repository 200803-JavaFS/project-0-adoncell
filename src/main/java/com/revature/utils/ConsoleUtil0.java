package com.revature.utils;

import java.util.List;
import java.util.Scanner;

import com.revature.daos.AccountDAO;
import com.revature.daos.AdminDAO;
import com.revature.daos.EmployeeDAO;
import com.revature.models.Admin;
import com.revature.models.Customer0;
import com.revature.models.Employee;
import com.revature.services.AdminService;
import com.revature.services.CustomerService;
import com.revature.services.EmployeeService;

public class ConsoleUtil0 {

	private static final Scanner scan = new Scanner(System.in);
	private CustomerService cs = new CustomerService();
	private AccountDAO ad = new AccountDAO();
	private EmployeeService es = new EmployeeService();
	private EmployeeDAO ed = new EmployeeDAO();
	private AdminService as = new AdminService();
	private AdminDAO admind = new AdminDAO();

	public void beginApp() {

		System.out.println("Hello and welcome to the National Bank of Java Beginners. \n"
				+ "How can we help you today? \n" + "[1] I am a new customer. \n" + "[2] I am an existing customer. \n"
				+ "[3] I am an Employee. \n" + "[4] I am a Bank Admin. \n" + "[5] That's all for today. ");

		String answer = scan.nextLine();
		openingSwitch(answer);
	}

	private void openingSwitch(String answer) {
		switch (answer) {
		case "1":
			newCustomerOptions();
			break;
		case "2":
			accountLogin();
			break;
		case "3":
			emloyeeOptions();
			break;
		case "4":
			initialAdmin();
			break;
		case "5":
			System.out.println("Thank you for banking with us today. Come again!");
			break;
		default:
			System.out.println("Invalid input, please enter a number 1-4.");
			beginApp();
			break;
		}
	}

	private void initialAdmin() {
		System.out.println("Are you a new admin? \n" + "[Y] Yes \n" + "[N] No \n" + "[Q] Main Menu");
		String input = scan.nextLine().toLowerCase();

		switch (input) {
		case "y":
			System.out.println("Alright, let's get your Admin Account set up then.");
			newAdmin();
			break;
		case "n":
			adminLogin();
			break;
		case "q":
			beginApp();
			break;
		default:
			System.out.println("Invalid input. Please enter 'Y', 'N', or 'Q'.");
			initialAdmin();
			break;
		}

	}

	private void newAdmin() {
		System.out.println("What is your first name?");
		String firstName = scan.nextLine().toUpperCase();

		System.out.println("What is your last name?");
		String lastName = scan.nextLine().toUpperCase();

		System.out.println("What username would you like?");
		String username = scan.nextLine();
		username = checkAdminUsername(username).toLowerCase();

		System.out.println("What password would you like?");
		String password = scan.nextLine();

		Admin a = new Admin(firstName, lastName, username, password, "Pending");

		if (as.insertAdmin(a)) {
			System.out.println("Your Admin Account has been set up and is now pending approval by another Admin. \n"
					+ "You can check its status by logging in as an Existing Admin. \n"
					+ "For now, I'll take you back to the Main Menu.");
			beginApp();
		} else {
			System.out.println("Something went wrong. Please enter your information again.");
			newEmployee();
		}

	}

	private String checkAdminUsername(String username) {
		String ad = null;
		ad = admind.checkUsername(username);

		if (ad != null) {
			return username;
		} else {
			System.out.println("That username is already taken. Please enter a new one.");
			String newUsername = scan.nextLine();
			checkUsernameMethod(newUsername);
		}
		return null;
	}

	private void adminLogin() {
		System.out.println("Please enter your username or type 'Q' to go back to the Main Menu.");
		String adminLogin = scan.nextLine().toLowerCase();

		if (adminLogin.equals("q")) {
			System.out.println("Back to the Main Menu then!");
			beginApp();
		}

		boolean lookup = false;
		lookup = admind.validateUsername(adminLogin);

		if (lookup) {
			admimPassLogin(adminLogin);
		} else {
			System.out.println("Sorry, we don't have any registered admins with that username.");
			adminLogin();
		}

	}

	private void admimPassLogin(String adminLogin) {
		System.out.println("Please enter your password or type 'Q' to go back to the Main Menu.");
		String adminPassword = scan.nextLine().toLowerCase();

		if (adminPassword.equals("q")) {
			System.out.println("Back to the Main Menu then!");
			beginApp();
		}

		Admin lookup = new Admin();
		lookup = admind.checkPassword(adminLogin, adminPassword);

		if (lookup != null) {
			validAdminOptions(lookup);
		} else {
			System.out.println("Sorry, that password is incorrect.");
			admimPassLogin(adminLogin);
		}
	}

	private void validAdminOptions(Admin a) {
		String name = a.getFirstName();
		System.out.println("Welcome back, " + name + "!");
		String status = a.getStatus();
		if (status.equals("Approved")) {
			System.out.println("What would you like to do today? \n" + "[1] View/Edit Customer Account(s) \n"
					+ "[2] Approve/Deny Open Applications \n" + "[3] Main Menu");

			String option = scan.nextLine();
			switch (option) {
			case "1":
				viewOrEditAccounts(a);
				break;
			case "2":
				approveOrDeny(a);
				break;
			case "3":
				beginApp();
				break;
			default:
				System.out.println("Invalid input. Please enter a number 1-3.");
				validAdminOptions(a);
				;
				break;
			}

		} else if (status.equals("Pending")) {
			System.out.println("Your account is still awaiting approval from an Admin. Please check back in later.");
			beginApp();
		} else {
			System.out.println(
					"Your account has been closed. Please contact our Customer Service if you think this is a mistake.");
			beginApp();
		}
	}

	private void viewOrEditAccounts(Admin a) {
		System.out.println("Which accounts would you like to view? \n" + "[1] View all accounts \n"
				+ "[2] View specific account \n" + "[3] View accounts by status \n" + "[4] Go to previous page");
		String input = scan.nextLine();

		switch (input) {
		case "1":
			List<Customer0> list = ad.viewAll();
			System.out.println("Here are all the accounts in the database: ");
			for (Customer0 c : list) {
				System.out.println("Name: " + c.getFirstName() + " " + c.getLastName() + ", Username: "
						+ c.getUsername() + ", Account Type: " + c.getAccountType() + ", Balance: " + c.getBalance()
						+ ", Status: " + c.getAccountStatus());
			}
			System.out.println("Press 'Enter' to go to previous page.");
			scan.nextLine();
			viewOrEditAccounts(a);
			break;

		case "2":
			specificAccount(a);
			break;

		case "3":
			System.out.println("Which status would you like to view by? \n" + "[O] Open \n" + "[C] Closed \n"
					+ "[P] Pending \n" + "[Q] Go to previous page.");
			String view = scan.nextLine().toLowerCase();

			switch (view) {
			case "o":
				List<Customer0> open = ad.viewOpen();
				System.out.println("Here are all the open accounts in the database: ");
				for (Customer0 c : open) {
					System.out.println("Name: " + c.getFirstName() + " " + c.getLastName() + ", Username: "
							+ c.getUsername() + ", Account Type: " + c.getAccountType() + ", Balance: " + c.getBalance()
							+ ", Status: " + c.getAccountStatus());
				}
				System.out.println("Press 'Enter' to go to previous page.");
				scan.nextLine();
				viewOrEditAccounts(a);
				break;
			case "c":
				List<Customer0> closed = ad.viewClosed();
				System.out.println("Here are all the closed accounts in the database: ");
				for (Customer0 c : closed) {
					System.out.println("Name: " + c.getFirstName() + " " + c.getLastName() + ", Username: "
							+ c.getUsername() + ", Account Type: " + c.getAccountType() + ", Balance: " + c.getBalance()
							+ ", Status: " + c.getAccountStatus());
				}
				System.out.println("Press 'Enter' to go to previous page.");
				scan.nextLine();
				viewOrEditAccounts(a);
				break;
			case "p":
				List<Customer0> pend = ad.viewPending();
				System.out.println("Here are all the pending accounts in the database: ");
				for (Customer0 c : pend) {
					System.out.println("Name: " + c.getFirstName() + " " + c.getLastName() + ", Username: "
							+ c.getUsername() + ", Account Type: " + c.getAccountType() + ", Balance: " + c.getBalance()
							+ ", Status: " + c.getAccountStatus());
				}
				System.out.println("Press 'Enter' to go to previous page.");
				scan.nextLine();
				viewOrEditAccounts(a);
				break;
			case "q":
				viewOrEditAccounts(a);
				break;
			}
		case "4":
			validAdminOptions(a);
			break;
		default:
			System.out.println("Invalid input. Please enter a number 1-4.");
			viewOrEditAccounts(a);
			break;
		}

	}

	private void specificAccount(Admin a) {
		System.out.println("What is the username of the person whose account you'd like to view? \n"
				+ "You can also enter 'Q' to go to the previous page.");
		String user = scan.nextLine().toLowerCase();

		if (user.equals("q")) {
			viewOrEditAccounts(a);
		}

		boolean lookup = false;
		lookup = ad.validateUsername(user);

		if (lookup) {
			Customer0 spec = ad.checkTransferAccount(user);
			System.out.println("Name: " + spec.getFirstName() + " " + spec.getLastName() + ", Username: "
					+ spec.getUsername() + ", Account Type: " + spec.getAccountType() + ", Balance: "
					+ spec.getBalance() + ", Status: " + spec.getAccountStatus());
			editAccount(a, user);
		} else {
			System.out.println("Sorry, we don't have any registered accounts with that username.");
			specificAccount(a);
		}

	}

	private void editAccount(Admin a, String user) {
		Customer0 c = ad.checkTransferAccount(user);
		if (!c.getAccountStatus().equals("Closed")) {
			System.out.println("Would you like to edit this account? \n" + "[Y] Yes \n" + "[N] No");
			String input = scan.nextLine().toLowerCase();
			switch (input) {
			case "y":
				editOptions(a, user);
				break;
			case "n":
				System.out.println("Press 'Enter' to go to the previous page.");
				scan.nextLine();
				specificAccount(a);
			default:
				System.out.println("Invalid input. Please enter either 'Y' or 'N'.");
				editAccount(a, user);
			}
		} else {
			System.out.println(
					"This account has been closed and therefore cannot be edited. You can press 'Enter' to go back.");
			scan.nextLine();
			specificAccount(a);
		}

	}

	private void editOptions(Admin a, String user) {
		Customer0 c = ad.checkTransferAccount(user);
		System.out.println("What would you like to change? \n" + "[1] First Name \n" + "[2] Last Name \n"
				+ "[3] Balance \n" + "[4] Account Status \n" + "[5] Return to previous page.");
		String choice = scan.nextLine();
		switch (choice) {
		case "1":
			String first = c.getFirstName();
			System.out.println("The user's current registered first name is " + first + ". \n"
					+ "What would you like to change it to?");
			String newFirst = scan.nextLine().toUpperCase();
			c.setFirstName(newFirst);
			ad.updateEntry(c);
			System.out.println("The user's new registered first name is " + c.getFirstName() + ". \n"
					+ "Press 'Enter' to go to the previous page.");
			scan.nextLine();
			editOptions(a, user);
			break;
		case "2":
			String last = c.getLastName();
			System.out.println("The user's current registered last name is " + last + ". \n"
					+ "What would you like to change it to?");
			String newLast = scan.nextLine().toUpperCase();
			c.setLastName(newLast);
			ad.updateEntry(c);
			System.out.println("The user's new registered last name is " + c.getLastName() + ". \n"
					+ "Press 'Enter' to go to the previous page.");
			scan.nextLine();
			editOptions(a, user);
			break;
		case "3":
			if (c.getAccountStatus().equals("Open")) {
				int balance = c.getBalance();
				System.out.println("The user's current balance is $" + balance + ". What would you like to do?\n"
						+ "[W] Withdraw money \n" + "[D] Deposit money \n" + "[T] Transfer money \n"
						+ "[Q] Go to previous page. ");
				String action = scan.nextLine().toLowerCase();
				switch (action) {
				case "w":
					makeWithdrawal(a, c);
					break;
				case "d":
					depositMoney(a, c);
					break;
				case "t":
					checkTransfer(a, c);
					editOptions(a, c.getUsername());
					break;
				case "q":
					editAccount(a, user);
					break;
				default:
					System.out.println("Invalid input. Please try again.");
					editOptions(a, user);
					break;
				}
			} else {
				System.out.println("This account has not been approved yet, so the balance cannot be changed. ");
				editOptions(a, user);
				break;
			}
			break;
		case "4":
			Customer0 customer = ad.checkTransferAccount(user);
			approvalDenial(a, customer);
			break;
		case "5":
			editAccount(a, user);
			break;
		default:
			System.out.println("Invalid input. Please try again.");
			editOptions(a, user);
			break;
		}

	}

	private void checkTransfer(Admin a, Customer0 c) {
		System.out.println("What is the username of the person you would like to transfer money to? \n"
				+ "You can also enter 'Q' to go to the previous page.");
		String transferUser = scan.nextLine().toLowerCase();

		if (transferUser.equals("q")) {
			viewAccount(c);
		}

		Customer0 lookup = new Customer0();
		lookup = ad.checkTransferAccount(transferUser);

		if (lookup != null) {
			String transferFullName = getFullName(lookup);
			System.out.println("Is " + transferFullName + " the user you mean to transfer money to? \n" + "[Y] Yes \n"
					+ "[N] No ");
			String input = scan.nextLine().toLowerCase();
			switch (input) {
			case "y":
				checkStatusForTransfer(c, lookup);
				transferMoney(a, c, lookup);
				break;
			case "n":
				checkTransfer(c);
				break;
			default:
				System.out.println("Invalid input. Please enter either 'y' or 'n'.");
				break;
			}
		} else {
			System.out.println(
					"Sorry, that user does not exist. Please try again, or enter 'Q' to go to the previous page.");
			checkTransfer(c);
		}

	}

	private void makeWithdrawal(Admin a, Customer0 c) {
		System.out.println("How much would you like to withdraw?");
		int withdraw = scan.nextInt();
		int balance = c.getBalance();
		if (withdraw >= 0) {
			if (withdraw <= balance) {
				int amount = balance - withdraw;
				c.setBalance(amount);
				ad.updateEntry(c);
				System.out.println("The user's new balance is $" + c.getBalance() + ".");
				editOptions(a, c.getUsername());
			} else if (withdraw > balance) {
				System.out.println("I'm sorry, your account balance is not high enough to withdraw this amount. \n"
						+ "Please try again, or enter the number '0' to go to the previous page.");
				makeWithdrawal(a, c);
			}
		} else {
			System.out.println(
					"Invalid input. Please enter a positive amount, or enter the number '0' to go to the previous page.");
			makeWithdrawal(a, c);
		}

	}

	private void depositMoney(Admin a, Customer0 c) {
		System.out.println("How much would you like to deposit?");
		int deposit = scan.nextInt();
		int balance = c.getBalance();
		if (deposit >= 0) {
			int amount = balance + deposit;
			c.setBalance(amount);
			ad.updateEntry(c);
			System.out.println("The new balance is $" + c.getBalance() + ".");
			editOptions(a, c.getUsername());
		} else {
			System.out.println(
					"Invalid input. Please enter a positive number, or enter the number '0' to go to the previous page.");
			depositMoney(a, c);
		}

	}

	private void approvalDenial(Admin a, Customer0 c) {
		System.out.println("This account currently has a status of " + c.getAccountStatus() + ".");
		if (c.getAccountStatus().equals("Pending")) {
			System.out.println(
					"How would you like to proceed? \n" + "[A] Approve \n" + "[D] Deny \n" + "[Q] Go to previous page");
			String answer = scan.nextLine().toLowerCase();
			switch (answer) {
			case "a":
				approveStatus(a, c.getUsername());
				System.out.println("The application has been approved. Press 'Enter' to go to the previous page.");
				scan.nextLine();
				editOptions(a, c.getUsername());
				break;
			case "d":
				denyStatus(a, c.getUsername());
				System.out.println("The account has been closed. Press 'Enter' to go to the previous menu.");
				scan.nextLine();
				editOptions(a, c.getUsername());
				break;
			case "q":
				editOptions(a, c.getUsername());
			default:
				System.out.println("Invalid input. Please try again.");
				approvalDenial(a, c);
				break;
			}
		} else {
			closeMethod(a, c);

		}

	}

	private void closeMethod(Admin a, Customer0 c) {
		System.out.println("Would you like to close this account? \n" + "[Y] Yes \n" + "[N] No ");
		String answer = scan.nextLine().toLowerCase();
		switch (answer) {
		case "y":
			denyStatus(a, c.getUsername());
			break;
		case "n":
			editOptions(a, c.getUsername());
		default:
			System.out.println("Invalid input. Please try again.");
			closeMethod(a, c);
			break;
		}

	}

	private void approveOrDeny(Admin a) {
		System.out.println("Which pending account(s) would you like to see? \n" + "[1] Customers \n"
				+ "[2] Employees \n" + "[3] Other Admins \n" + "[4] Previous page.");
		String input = scan.nextLine();
		switch (input) {
		case "1":
			approveOrDenyCustomers(a);
			break;
		case "2":
			approveOrDenyEmployees(a);
			break;
		case "3":
			approveOrDenyAdmins(a);
			break;
		case "4":
			validAdminOptions(a);
			break;
		default:
			System.out.println("Invalid input. Please enter a number 1-4.");
			approveOrDeny(a);
			break;
		}

	}

	private void approveOrDenyAdmins(Admin a) {
		List<Admin> pend = admind.viewPending();
		System.out.println("Here are all the pending admin accounts in the database: ");
		for (Admin e : pend) {
			System.out.println("Name: " + e.getFirstName() + " " + e.getLastName() + ", Username: " + e.getUsername()
					+ ", Status: " + e.getStatus());
		}

		System.out.println("What is the username for the admin application whose status you'd like to change? \n"
				+ "You can also enter 'Q' to go to the previous page.");
		String user = scan.nextLine().toLowerCase();

		if (user.equals("q")) {
			approveOrDeny(a);
		}
		
		boolean lookup = false;
		lookup = admind.validateUsername(user);
		Admin check = admind.checkAccount(user);
		if (check.getStatus().equals("Pending")) {

			if (lookup) {
				System.out.println("Would you like to approve or deny this application? \n" + "[A] Approve \n"
						+ "[D] Deny \n" + "[Q] Previous Page");
				String status = scan.nextLine().toLowerCase();
				switch (status) {
				case "a":
					approveStatusAdmin(a, user);
					System.out.println("The application has been approved. Press 'Enter' to go to the previous page.");
					scan.nextLine();
					approveOrDenyAdmins(a);
					break;
				case "d":
					denyStatusAdmin(a, user);
					System.out.println("The account has been closed. Press 'Enter' to go to the previous menu.");
					scan.nextLine();
					approveOrDenyAdmins(a);
					break;
				case "q":
					approveOrDeny(a);
					break;
				default:
					System.out.println("Invalid input. Please try again.");
					approveOrDenyAdmins(a);
					break;
				}
			} else {
				System.out.println("Sorry, we don't have any registered accounts with that username.");
				approveOrDenyAdmins(a);
			}
		} else {
			System.out.println("This portal is only for pending applications.");
			approveOrDenyAdmins(a);
		}

	}

	private void denyStatusAdmin(Admin a, String user) {
		Admin ad = admind.checkAccount(user);
		ad.setStatus("Closed");
		admind.updateEntry(ad);

	}

	private void approveStatusAdmin(Admin a, String user) {
		Admin ad = admind.checkAccount(user);
		ad.setStatus("Open");
		admind.updateEntry(a);


	}

	private void approveOrDenyEmployees(Admin a) {
		List<Employee> pend = ed.viewPending();
		System.out.println("Here are all the pending customer accounts in the database: ");
		for (Employee e : pend) {
			System.out.println("Name: " + e.getFirstName() + " " + e.getLastName() + ", Username: " + e.getUsername()
					+ ", Status: " + e.getStatus());
		}

		System.out.println("What is the username for the employee application whose status you'd like to change? \n"
				+ "You can also enter 'Q' to go to the previous page.");
		String user = scan.nextLine().toLowerCase();

		if (user.equals("q")) {
			approveOrDeny(a);
		}

		boolean lookup = false;
		lookup = ed.validateUsername(user);
		Employee check = ed.checkAccount(user);
		if (check.getStatus().equals("Pending")) {

			if (lookup) {
				System.out.println("Would you like to approve or deny this application? \n" + "[A] Approve \n"
						+ "[D] Deny \n" + "[Q] Previous Page");
				String status = scan.nextLine().toLowerCase();
				switch (status) {
				case "a":
					approveStatusEmployee(a, user);
					System.out.println("The application has been approved. Press 'Enter' to go to the previous page.");
					scan.nextLine();
					approveOrDenyEmployees(a);
					break;
				case "d":
					denyStatusEmployee(a, user);
					System.out.println("The account has been closed. Press 'Enter' to go to the previous menu.");
					scan.nextLine();
					approveOrDenyEmployees(a);
					break;
				case "q":
					approveOrDeny(a);
					break;
				default:
					System.out.println("Invalid input. Please try again.");
					approveOrDenyEmployees(a);
					break;
				}
			} else {
				System.out.println("Sorry, we don't have any registered accounts with that username.");
				approveOrDenyEmployees(a);
			}
		} else {
			System.out.println("This portal is only for pending applications.");
			approveOrDenyEmployees(a);
		}

	}

	private void denyStatusEmployee(Admin a, String user) {
		Employee e = ed.checkAccount(user);
		e.setStatus("Closed");
		ed.updateEntry(e);

	}

	private void approveStatusEmployee(Admin a, String user) {
		Employee e = ed.checkAccount(user);
		e.setStatus("Open");
		ed.updateEntry(e);

	}

	private void approveOrDenyCustomers(Admin a) {
		List<Customer0> pend = ad.viewPending();
		System.out.println("Here are all the pending customer accounts in the database: ");
		for (Customer0 c : pend) {
			System.out.println("Name: " + c.getFirstName() + " " + c.getLastName() + ", Username: " + c.getUsername()
					+ ", Account Type: " + c.getAccountType() + ", Balance: " + c.getBalance() + ", Status: "
					+ c.getAccountStatus());
		}

		System.out.println("What is the username for the application whose status you'd like to change? \n"
				+ "You can also enter 'Q' to go to the previous page.");
		String user = scan.nextLine().toLowerCase();

		if (user.equals("q")) {
			approveOrDeny(a);
		}

		boolean lookup = false;
		lookup = ad.validateUsername(user);
		Customer0 check = ad.checkTransferAccount(user);
		if (check.getAccountStatus().equals("Pending")) {

			if (lookup) {
				System.out.println("Would you like to approve or deny this application? \n" + "[A] Approve \n"
						+ "[D] Deny \n" + "[Q] Previous Page");
				String status = scan.nextLine().toLowerCase();
				switch (status) {
				case "a":
					approveStatus(a, user);
					System.out.println("The application has been approved. Press 'Enter' to go to the previous page.");
					scan.nextLine();
					approveOrDenyCustomers(a);
					break;
				case "d":
					denyStatus(a, user);
					System.out.println("The account has been closed. Press 'Enter' to go to the previous menu.");
					scan.nextLine();
					approveOrDenyCustomers(a);
					break;
				case "q":
					approveOrDeny(a);
					break;
				default:
					System.out.println("Invalid input. Please try again.");
					approveOrDenyCustomers(a);
					break;
				}
			} else {
				System.out.println("Sorry, we don't have any registered accounts with that username.");
				approveOrDenyCustomers(a);
			}
		} else {
			System.out.println("This portal is only for pending applications.");
			approveOrDenyCustomers(a);
		}

	}

	private void denyStatus(Admin a, String user) {
		Customer0 c = ad.checkTransferAccount(user);
		c.setAccountStatus("Closed");
		ad.updateEntry(c);
//		System.out.println("The account has been closed. Press 'Enter' to go to the previous menu.");
//		scan.nextLine();
//		viewOrEditAccounts(a);

	}

	private void approveStatus(Admin a, String user) {
		Customer0 c = ad.checkTransferAccount(user);
		c.setAccountStatus("Open");
		ad.updateEntry(c);
//		System.out.println("The application has been approved. Press 'Enter' to go to the previous page.");
//		scan.nextLine();
//		editOptions(a, user);

	}

	private void emloyeeOptions() {
		System.out.println("Are you a new employee? \n" + "[Y] Yes \n" + "[N] No \n" + "[Q] Main Menu");
		String input = scan.nextLine().toLowerCase();

		switch (input) {
		case "y":
			System.out.println("Alright, let's get your Employee Account set up then.");
			newEmployee();
			break;
		case "n":
			employeeLogin();
			break;
		case "q":
			beginApp();
			break;
		default:
			System.out.println("Invalid input. Please enter 'Y', 'N', or 'Q'.");
			emloyeeOptions();
			break;
		}

	}

	private void employeeLogin() {
		System.out.println("Please enter your username or type 'Q' to go back to the Main Menu.");
		String empLogin = scan.nextLine().toLowerCase();

		if (empLogin.equals("q")) {
			System.out.println("Back to the Main Menu then!");
			beginApp();
		}

		boolean lookup = false;
		lookup = ed.validateUsername(empLogin);

		if (lookup) {
			empPassLogin(empLogin);
		} else {
			System.out.println("Sorry, we don't have any registered accounts with that username.");
			employeeLogin();
		}

	}

	private void empPassLogin(String empLogin) {
		System.out.println("Please enter your password or type 'Q' to go back to the Main Menu.");
		String empPassword = scan.nextLine().toLowerCase();

		if (empPassword.equals("q")) {
			System.out.println("Back to the Main Menu then!");
			beginApp();
		}

		Employee lookup = new Employee();
		lookup = ed.checkPassword(empLogin, empPassword);

		if (lookup != null) {
			validEmployeeOptions(lookup);
		} else {
			System.out.println("Sorry, that password is incorrect.");
			passwordLogin(empLogin);
		}

	}

	private void validEmployeeOptions(Employee e) {
		String name = e.getFirstName();
		System.out.println("Welcome back, " + name + "!");
		String status = e.getStatus();
		if (status.equals("Open")) {
			System.out.println("What would you like to do today? \n" + "[1] View Customer Account(s) \n"
					+ "[2] Approve/Deny Open Applications \n" + "[3] Main Menu");

			String option = scan.nextLine();
			switch (option) {
			case "1":
				viewCustomerAccounts(e);
				break;
			case "2":
				approveOrDeny(e);
				break;
			case "3":
				beginApp();
				break;
			default:
				System.out.println("Invalid input. Please enter a number 1-3.");
				validEmployeeOptions(e);
				break;
			}

		} else if (status.equals("Pending")) {
			System.out.println("Your account is still awaiting approval from an Admin. Please check back in later. \n"
					+ "You can press 'Enter' to go back to the Main Menu.");
			scan.nextLine();
			beginApp();
		} else {
			System.out.println(
					"Your account has been closed. Please contact our Customer Service if you think this is a mistake.");
			beginApp();
		}

	}

	private void approveOrDeny(Employee e) {
		List<Customer0> pend = ad.viewPending();
		System.out.println("Here are all the pending customer accounts in the database: ");
		for (Customer0 c : pend) {
			System.out.println("Name: " + c.getFirstName() + " " + c.getLastName() + ", Username: " + c.getUsername()
					+ ", Account Type: " + c.getAccountType() + ", Balance: " + c.getBalance() + ", Status: "
					+ c.getAccountStatus());
		}

		System.out.println("What is the username for the application whose status you'd like to change? \n"
				+ "You can also enter 'Q' to go to the previous page.");
		String user = scan.nextLine().toLowerCase();

		if (user.equals("q")) {
			validEmployeeOptions(e);
		}

		boolean lookup = false;
		lookup = ad.validateUsername(user);
		Customer0 check = ad.checkTransferAccount(user);
		if (check.getAccountStatus().equals("Pending")) {

			if (lookup) {
				System.out.println("Would you like to approve or deny this application? \n" + "[A] Approve \n"
						+ "[D] Deny \n" + "[Q] Previous Page");
				String status = scan.nextLine().toLowerCase();
				switch (status) {
				case "a":
					approveStatus(e, user);
					System.out.println("The application has been approved. Press 'Enter' to go to the previous page.");
					scan.nextLine();
					approveOrDeny(e);
					break;
				case "d":
					denyStatus(e, user);
					System.out.println("The account has been closed. Press 'Enter' to go to the previous menu.");
					scan.nextLine();
					approveOrDeny(e);
					break;
				case "q":
					validEmployeeOptions(e);
					break;
				default:
					System.out.println("Invalid input. Please try again.");
					approveOrDeny(e);
					break;
				}
			} else {
				System.out.println("Sorry, we don't have any registered accounts with that username.");
				approveOrDeny(e);
			}
		} else {
			System.out.println("This portal is only for pending applications.");
			approveOrDeny(e);
		}

	}

	private void denyStatus(Employee e, String user) {
		Customer0 c = ad.checkTransferAccount(user);
		c.setAccountStatus("Closed");
		ad.updateEntry(c);
		System.out.println("The application has been denied. Press 'Enter' to go to the previous page.");
		scan.nextLine();
		approveOrDeny(e);

	}

	private void approveStatus(Employee e, String user) {
		Customer0 c = ad.checkTransferAccount(user);
		c.setAccountStatus("Open");
		ad.updateEntry(c);
		System.out.println("The application has been approved. Press 'Enter' to go to the previous page.");
		scan.nextLine();
		approveOrDeny(e);
	}

	private void viewCustomerAccounts(Employee e) {
		System.out.println("Which accounts would you like to view? \n" + "[1] View all accounts \n"
				+ "[2] View specific account \n" + "[3] View accounts by status \n" + "[4] Go to previous page");
		String input = scan.nextLine();

		switch (input) {
		case "1":
			List<Customer0> list = ad.viewAll();
			System.out.println("Here are all the accounts in the database: ");
			for (Customer0 c : list) {
				System.out.println("Name: " + c.getFirstName() + " " + c.getLastName() + ", Username: "
						+ c.getUsername() + ", Account Type: " + c.getAccountType() + ", Balance: " + c.getBalance()
						+ ", Status: " + c.getAccountStatus());
			}
			System.out.println("Press 'Enter' to go to previous page.");
			scan.nextLine();
			viewCustomerAccounts(e);
			break;

		case "2":
			specificAccount(e);
			break;

		case "3":
			System.out.println("Which status would you like to view by? \n" + "[O] Open \n" + "[C] Closed \n"
					+ "[P] Pending \n" + "[Q] Go to previous page.");
			String view = scan.nextLine().toLowerCase();

			switch (view) {
			case "o":
				List<Customer0> open = ad.viewOpen();
				System.out.println("Here are all the open accounts in the database: ");
				for (Customer0 c : open) {
					System.out.println("Name: " + c.getFirstName() + " " + c.getLastName() + ", Username: "
							+ c.getUsername() + ", Account Type: " + c.getAccountType() + ", Balance: " + c.getBalance()
							+ ", Status: " + c.getAccountStatus());
				}
				System.out.println("Press 'Enter' to go to previous page.");
				scan.nextLine();
				viewCustomerAccounts(e);
				break;
			case "c":
				List<Customer0> closed = ad.viewClosed();
				System.out.println("Here are all the closed accounts in the database: ");
				for (Customer0 c : closed) {
					System.out.println("Name: " + c.getFirstName() + " " + c.getLastName() + ", Username: "
							+ c.getUsername() + ", Account Type: " + c.getAccountType() + ", Balance: " + c.getBalance()
							+ ", Status: " + c.getAccountStatus());
				}
				System.out.println("Press 'Enter' to go to previous page.");
				scan.nextLine();
				viewCustomerAccounts(e);
				break;
			case "p":
				List<Customer0> pend = ad.viewPending();
				System.out.println("Here are all the pending accounts in the database: ");
				for (Customer0 c : pend) {
					System.out.println("Name: " + c.getFirstName() + " " + c.getLastName() + ", Username: "
							+ c.getUsername() + ", Account Type: " + c.getAccountType() + ", Balance: " + c.getBalance()
							+ ", Status: " + c.getAccountStatus());
				}
				System.out.println("Press 'Enter' to go to previous page.");
				scan.nextLine();
				viewCustomerAccounts(e);
				break;
			case "q":
				validEmployeeOptions(e);
				break;
			}
		case "4":
			validEmployeeOptions(e);
			break;
		default:
			System.out.println("Invalid input. Please enter a number 1-4.");
			viewCustomerAccounts(e);
			break;
		}


	}

	private void specificAccount(Employee e) {
		System.out.println("What is the username of the person whose account you'd like to view? \n"
				+ "You can also enter 'Q' to go to the previous page.");
		String user = scan.nextLine().toLowerCase();

		if (user.equals("q")) {
			viewCustomerAccounts(e);
		}

		boolean lookup = false;
		lookup = ad.validateUsername(user);

		if (lookup) {
			Customer0 spec = ad.checkTransferAccount(user);
			System.out.println("Name: " + spec.getFirstName() + " " + spec.getLastName() + ", Username: "
					+ spec.getUsername() + ", Account Type: " + spec.getAccountType() + ", Balance: "
					+ spec.getBalance() + ", Status: " + spec.getAccountStatus());
			System.out.println("Press 'Enter' to go to previous page.");
			scan.nextLine();
			viewCustomerAccounts(e);
		} else {
			System.out.println("Sorry, we don't have any registered accounts with that username.");
			specificAccount(e);
		}

	}

	private void newEmployee() {
		System.out.println("What is your first name?");
		String firstName = scan.nextLine().toUpperCase();

		System.out.println("What is your last name?");
		String lastName = scan.nextLine().toUpperCase();

		System.out.println("What username would you like?");
		String username = scan.nextLine();
		username = checkEmployeeUsername(username).toLowerCase();

		System.out.println("What password would you like?");
		String password = scan.nextLine();

		Employee e = new Employee(firstName, lastName, username, password, "Pending");

		if (es.insertEmployee(e)) {
			System.out.println("Your Employee Account has been set up and is now pending approval by an Admin. \n"
					+ "You can check its status by logging in as an Existing Employee. \n"
					+ "For now, I'll take you back to the Main Menu.");
			beginApp();
		} else {
			System.out.println("Something went wrong. Please enter your information again.");
			newEmployee();
		}

	}

	private String checkEmployeeUsername(String username) {
		String emp = null;
		emp = ed.checkUsername(username);

		if (emp != null) {
			return username;
		} else {
			System.out.println("That username is already taken. Please enter a new one.");
			String newUsername = scan.nextLine();
			checkUsernameMethod(newUsername);
		}
		return null;
	}

	private void accountLogin() {
		System.out.println("Please enter your username or type 'Q' to go back to the Main Menu.");
		String userLogin = scan.nextLine().toLowerCase();

		if (userLogin.equals("q")) {
			System.out.println("Back to the Main Menu then!");
			beginApp();
		}

		boolean lookup = false;
		lookup = ad.validateUsername(userLogin);

		if (lookup) {
			passwordLogin(userLogin);
		} else {
			System.out.println("Sorry, we don't have any registered accounts with that username.");
			accountLogin();
		}

	}

	private void passwordLogin(String userLogin) {
		System.out.println("Please enter your password or type 'Q' to go back to the Main Menu.");
		String userPassword = scan.nextLine().toLowerCase();

		if (userPassword.equals("q")) {
			System.out.println("Back to the Main Menu then!");
			beginApp();
		}

		Customer0 lookup = new Customer0();
		lookup = ad.checkPassword(userLogin, userPassword);

		if (lookup != null) {
			validCustomerOptions(lookup);
		} else {
			System.out.println("Sorry, that password is incorrect.");
			passwordLogin(userLogin);
		}

	}

	private void validCustomerOptions(Customer0 c) {
		String name = c.getFirstName();
		System.out.println("Welcome back, " + name + "! How can we help you today? \n" + "[1] View Account \n"
				+ "[2] Make Changes \n" + "[3] Main Menu");

		String option = scan.nextLine();
		switch (option) {
		case "1":
			viewAccount(c);
			break;
		case "2":
			editAccount(c);
			break;
		case "3":
			beginApp();
			break;
		default:
			System.out.println("Invalid input. Please enter a number 1-4.");
			validCustomerOptions(c);
			break;
		}
	}

	private void editAccount(Customer0 c) {
		System.out.println("What would you like to change? \n" + "[1] My First Name \n" + "[2] My Last Name \n"
				+ "[3] My Password \n" + "[4] Go to previous page.");
		String input = scan.nextLine();
		switch (input) {
		case "1":
			String first = c.getFirstName();
			System.out.println("Your current listed first name is " + first + ". What would you like to change it to?");
			String newFirst = scan.nextLine().toUpperCase();
			c.setFirstName(newFirst);
			ad.updateEntry(c);
			System.out.println("Your listed first name is now " + c.getFirstName() + ".");
			editAccount(c);
			break;
		case "2":
			String last = c.getLastName();
			System.out.println("Your current listed last name is " + last + ". What would you like to change it to?");
			String newLast = scan.nextLine().toUpperCase();
			c.setLastName(newLast);
			ad.updateEntry(c);
			System.out.println("Your listed last name is now " + c.getLastName() + ".");
			editAccount(c);
			break;
		case "3":
			String pass = c.getPassword();
			System.out.println("Your current password is " + pass + ". What would you like to change it to?");
			String newPass = scan.nextLine();
			c.setPassword(newPass);
			ad.updateEntry(c);
			System.out.println("Your password is now " + c.getPassword() + ".");
			editAccount(c);
			break;
		case "4":
			validCustomerOptions(c);
			break;
		default:
			System.out.println("Invalid input. Please enter a value 1-4.");
			editAccount(c);
			break;
		}

	}

	private void viewAccount(Customer0 c) {
		String type = c.getAccountType();
		String status = c.getAccountStatus();
		int balance = c.getBalance();

		if (status.equals("Pending")) {
			System.out.println("Your " + type + " account is still Pending. Once it is approved, you may view it. \n"
					+ "Please hit the 'Enter' key to go back.");
			scan.nextLine();
			validCustomerOptions(c);
		} else if (status.equals("Closed")) {
			System.out.println("Your " + type + " account is currently Closed. \n"
					+ "If you think this is an error, please contact our Customer Service at +1 (800) 628-8737. \n"
					+ "Press 'Enter' to go to the previous menu.");
			scan.nextLine();
			validCustomerOptions(c);
		} else {
			System.out.println("Your " + type + " account has a balance of $" + balance + ". \n"
					+ "What would you like to do now? \n" + "[W] Withdraw money \n" + "[D] Deposit money \n"
					+ "[T] Transfer money \n" + "[Q] Go to previous page. ");
			String option = scan.nextLine().toLowerCase();
			switch (option) {
			case "w":
				makeWithdrawal(c);
				break;
			case "d":
				System.out.println("How much would you like to deposit?");
				int deposit = scan.nextInt();
				depositMoney(c, deposit);
				break;
			case "t":
				checkTransfer(c);
				viewAccount(c);
				break;
			case "q":
				validCustomerOptions(c);
				break;
			default:
				System.out.println("Invalid input. Please try again.");
				viewAccount(c);
			}
		}
	}


	private void checkTransfer(Customer0 c) {
		System.out.println("What is the username of the person you would like to transfer money to? \n"
				+ "You can also enter 'Q' to go to the previous page.");
		String transferUser = scan.nextLine().toLowerCase();

		if (transferUser.equals("q")) {
			viewAccount(c);
		}

		Customer0 lookup = new Customer0();
		lookup = ad.checkTransferAccount(transferUser);

		if (lookup != null) {
			String transferFullName = getFullName(lookup);
			System.out.println("Is " + transferFullName + " the user you mean to transfer money to? \n" + "[Y] Yes \n"
					+ "[N] No ");
			String input = scan.nextLine().toLowerCase();
			switch (input) {
			case "y":
				checkStatusForTransfer(c, lookup);
				transferMoney(null, c, lookup);
				break;
			case "n":
				checkTransfer(c);
				break;
			default:
				System.out.println("Invalid input. Please enter either 'y' or 'n'.");
				break;
			}
		} else {
			System.out.println(
					"Sorry, that user does not exist. Please try again, or enter 'Q' to go to the previous page.");
			checkTransfer(c);
		}

	}

	private void checkStatusForTransfer(Customer0 c, Customer0 lookup) {
		String status = lookup.getAccountStatus();
		if (!status.equals("Open")) {
			System.out.println("This user's account is not open, so you cannot transfer money to it.");
			checkTransfer(c);
		}

	}

	private String getFullName(Customer0 c) {
		String first = c.getFirstName();
		String last = c.getLastName();
		return first + " " + last;
	}

	private void transferMoney(Admin a, Customer0 currentUser, Customer0 transferUser) {
		String otherName = transferUser.getFirstName();
		System.out.println("How much money would you like to transfer to " + otherName + "?");
		int amount = scan.nextInt();

		int balance = currentUser.getBalance();
		if (amount >= 0) {
			if (amount <= balance) {
				int newBal = balance - amount;
				currentUser.setBalance(newBal);
				ad.updateEntry(currentUser);
			} else if (amount > balance) {
				System.out.println("I'm sorry, your account balance is not high enough to withdraw this amount. \n"
						+ "Please try again, or enter the number '0' to go to the previous page.");
				transferMoney(a, currentUser, transferUser);
			}
		} else {
			System.out.println(
					"Invalid input. Please enter a positive amount, or enter the number '0' to go to the previous page.");
			transferMoney(a, currentUser, transferUser);
		}

		int otherbal = transferUser.getBalance();
		int total = otherbal + amount;
		transferUser.setBalance(total);
		ad.updateEntry(transferUser);
		System.out.println("The account of " + currentUser.getUsername() + " now has a balance of $"
				+ currentUser.getBalance() + ", \n" + "and the account of " + transferUser.getUsername()
				+ " now has a balance of $" + transferUser.getBalance() + ".");
		//editOptions(a, currentUser.getUsername());

	}

	private void depositMoney(Customer0 c, int deposit) {
		int balance = c.getBalance();
		if (deposit >= 0) {
			int amount = balance + deposit;
			c.setBalance(amount);
			ad.updateEntry(c);
			viewAccount(c);
		} else {
			System.out.println(
					"Invalid input. Please enter a positive number, or enter the number '0' to go to the previous page.");
			depositMoney(c, deposit);
		}

	}

	private void makeWithdrawal(Customer0 c) {
		System.out.println("How much would you like to withdraw?");
		int withdraw = scan.nextInt();
		int balance = c.getBalance();
		if (withdraw >= 0) {
			if (withdraw <= balance) {
				int amount = balance - withdraw;
				c.setBalance(amount);
				ad.updateEntry(c);
				viewAccount(c);
			} else if (withdraw > balance) {
				System.out.println("I'm sorry, your account balance is not high enough to withdraw this amount. \n"
						+ "Please try again, or enter the number '0' to go to the previous page.");
				makeWithdrawal(c);
			}
		} else {
			System.out.println(
					"Invalid input. Please enter a positive amount, or enter the number '0' to go to the previous page.");
			makeWithdrawal(c);
		}
	}

	private void newCustomerOptions() {

		System.out.println("Would you like to apply to open a new account? \n" + "[Y] Yes \n" + "[N] No ");

		String newAnswer = scan.nextLine();
		newAnswer = newAnswer.toLowerCase();

		switch (newAnswer) {
		case "y":
			System.out.println("Great! Let's get started then.");
			accountApplication();
			break;
		case "n":
			System.out.println("That's alright. Maybe next time.");
			beginApp();
			break;
		default:
			System.out.println("Invalid input, please enter either 'y' or 'n'.");
			newCustomerOptions();
			break;
		}

	}

	private void accountApplication() {

		System.out.println("What is your first name?");
		String firstName = scan.nextLine().toUpperCase();

		System.out.println("What is your last name?");
		String lastName = scan.nextLine().toUpperCase();

		String acc = accountType();

		System.out.println("Great. We will get started on that application. \n"
				+ "In the meantime, let's set up a username and password for you to use to log in. \n"
				+ "What username would you like?");
		String username = scan.nextLine();
		String valid = checkUsernameMethod(username.toLowerCase());

		System.out.println("What password would you like?");
		String password = scan.nextLine();

		Customer0 c = new Customer0(firstName, lastName, acc, valid, password, 0, "Pending");

		if (cs.insertCustomer(c)) {
			System.out.println("Your application is now pending. \n"
					+ "You can check its status by logging in as an Existing Customer in the Main Menu. \n"
					+ "I'll take you there right now.");
			beginApp();
		} else {
			System.out.println("Something went wrong. Please enter your information again.");
			accountApplication();
		}

	}

	private String accountType() {

		System.out.println("What kind of account would you like to apply for? \n" + "[C] Checking Account \n"
				+ "[S] Savings Account");

		String acc = scan.nextLine();

		acc = acc.toLowerCase();

		if (acc.equals("c")) {
			return "checking";
		} else if (acc.equals("s")) {
			return "savings";
		} else {
			System.out.println("I'm sorry. Please enter either 'c' for Checking or 's' for Savings.");
			return accountType();
		}
	}

	private String checkUsernameMethod(String username) {
		String cust = null;
		cust = ad.checkUsername(username);

		if (cust != null) {
			if (cust.equals("")) {
				System.out.println("Invalid input. Please try again.");
				String newUsername = scan.nextLine();
				return checkUsernameMethod(newUsername);
			} else {
				return cust;
			}
		} else {
			System.out.println("That username is already taken. Please enter a new one.");
			String newUsername = scan.nextLine();
			return checkUsernameMethod(newUsername);
		}
	}
}
