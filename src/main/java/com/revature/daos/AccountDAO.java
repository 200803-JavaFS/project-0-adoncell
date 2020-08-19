package com.revature.daos;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Customer0;
import com.revature.utils.ConnectionUtility;

public class AccountDAO implements IAccountDAO {

	@Override
	public String checkUsername(String username) {

		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE username = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, username);

			ResultSet result = statement.executeQuery();

			if (!result.next()) {
				return username;
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addCustomer(Customer0 c) {
		try (Connection conn = ConnectionUtility.getConnection()) {

			String sql = "INSERT INTO accounts (first_name, last_name, account_type, username, user_password, balance, status)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?);";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setString(++index, c.getFirstName());
			statement.setString(++index, c.getLastName());
			statement.setString(++index, c.getAccountType());
			statement.setString(++index, c.getUsername());
			statement.setString(++index, c.getPassword());
			statement.setInt(++index, c.getBalance());
			statement.setString(++index, c.getAccountStatus());

			statement.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Customer0 checkPassword(String userLogin, String userPassword) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE username = ? AND user_password = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, userLogin);
			statement.setString(2, userPassword);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Customer0 c = new Customer0();
				c.setFirstName(result.getString("first_name"));
				c.setLastName(result.getString("last_name"));
				c.setAccountType(result.getString("account_type"));
				c.setUsername(result.getString("username"));
				c.setPassword(result.getString("user_password"));
				c.setBalance(result.getInt("balance"));
				c.setAccountStatus(result.getString("status"));
				return c;
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean validateUsername(String userLogin) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE username = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, userLogin);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Customer0 checkTransferAccount(String transferUser) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE username = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, transferUser);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Customer0 c = new Customer0();
				c.setFirstName(result.getString("first_name"));
				c.setLastName(result.getString("last_name"));
				c.setAccountType(result.getString("account_type"));
				c.setUsername(result.getString("username"));
				c.setPassword(result.getString("user_password"));
				c.setBalance(result.getInt("balance"));
				c.setAccountStatus(result.getString("status"));
				return c;
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Customer0> viewAll() {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts;";

			Statement statement = conn.createStatement();

			List<Customer0> list = new ArrayList<>();

			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Customer0 c = new Customer0(result.getString("first_name"), result.getString("last_name"),
						result.getString("account_type"), result.getString("username"),
						result.getString("user_password"), result.getInt("balance"), result.getString("status"));
				list.add(c);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Customer0> viewOpen() {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE status = 'Open';";

			Statement statement = conn.createStatement();

			List<Customer0> list = new ArrayList<>();

			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Customer0 c = new Customer0(result.getString("first_name"), result.getString("last_name"),
						result.getString("account_type"), result.getString("username"),
						result.getString("user_password"), result.getInt("balance"), result.getString("status"));
				list.add(c);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Customer0> viewClosed() {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE status = 'Closed';";

			Statement statement = conn.createStatement();

			List<Customer0> list = new ArrayList<>();

			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Customer0 c = new Customer0(result.getString("first_name"), result.getString("last_name"),
						result.getString("account_type"), result.getString("username"),
						result.getString("user_password"), result.getInt("balance"), result.getString("status"));
				list.add(c);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Customer0> viewPending() {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE status = 'Pending';";

			Statement statement = conn.createStatement();

			List<Customer0> list = new ArrayList<>();

			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Customer0 c = new Customer0(result.getString("first_name"), result.getString("last_name"),
						result.getString("account_type"), result.getString("username"),
						result.getString("user_password"), result.getInt("balance"), result.getString("status"));
				list.add(c);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void updateEntry(Customer0 c) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "UPDATE accounts SET first_name = ?, last_name = ?, user_password = ?, balance = ?, status = ? "
					+ "WHERE username = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setString(++index, c.getFirstName());
			statement.setString(++index, c.getLastName());
			statement.setString(++index, c.getPassword());
			statement.setInt(++index, c.getBalance());
			statement.setString(++index, c.getAccountStatus());
			statement.setString(++index, c.getUsername());
			
			statement.execute();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

}