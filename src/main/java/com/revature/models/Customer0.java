package com.revature.models;

import java.io.Serializable;

public class Customer0 implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int accountID;
	private String firstName;
	private String lastName;
	private String accountType;
	private String username;
	private String password;
	private int balance;
	private String accountStatus;
	
	public Customer0() {
		super();
	}

	public Customer0(int accountID, String firstName, String lastName, String accountType, String username,
			String password, int balance, String accountStatus) {
		super();
		this.accountID = accountID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountType = accountType;
		this.username = username;
		this.password = password;
		this.balance = balance;
		this.accountStatus = accountStatus;
	}

	public Customer0(String firstName, String lastName, String accountType, String username, String password,
			int balance, String accountStatus) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountType = accountType;
		this.username = username;
		this.password = password;
		this.balance = balance;
		this.accountStatus = accountStatus;
	}

	public Customer0(String username) {
		super();
		this.username = username;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	

}
