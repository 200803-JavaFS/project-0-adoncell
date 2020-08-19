package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Employee;
import com.revature.utils.ConnectionUtility;

public class EmployeeDAO implements IEmployeeDAO {

	@Override
	public boolean addEmployee(Employee e) {
		try (Connection conn = ConnectionUtility.getConnection()) {

			String sql = "INSERT INTO employees (first_name, last_name, username, user_password, status)"
					+ "VALUES (?, ?, ?, ?, ?);";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setString(++index, e.getFirstName());
			statement.setString(++index, e.getLastName());
			statement.setString(++index, e.getUsername());
			statement.setString(++index, e.getPassword());
			statement.setString(++index, e.getStatus());

			statement.execute();
			return true;

		} catch (SQLException s) {
			s.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean validateUsername(String empLogin) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM employees WHERE username = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, empLogin);

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
	public String checkUsername(String username) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM employees WHERE username = ?;";

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
	public Employee checkPassword(String empLogin, String empPassword) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM employees WHERE username = ? AND user_password = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, empLogin);
			statement.setString(2, empPassword);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Employee e = new Employee();
				e.setFirstName(result.getString("first_name"));
				e.setLastName(result.getString("last_name"));
				e.setUsername(result.getString("username"));
				e.setPassword(result.getString("user_password"));
				e.setStatus(result.getString("status"));
				return e;
			} else {
				return null;
			}

		} catch (SQLException s) {
			s.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> viewPending() {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM employees WHERE status = 'Pending';";

			Statement statement = conn.createStatement();

			List<Employee> list = new ArrayList<>();

			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Employee e = new Employee(result.getString("first_name"), result.getString("last_name"),
						result.getString("username"), result.getString("user_password"), result.getString("status"));
				list.add(e);
			}
			return list;

		} catch (SQLException s) {
			s.printStackTrace();
		}
		return null;
		
	}
	
	@Override
	public List<Employee> viewByUser(String user) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM employees WHERE username = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, user);

			List<Employee> list = new ArrayList<>();

			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Employee e = new Employee(result.getString("first_name"), result.getString("last_name"), 
						result.getString("username"), result.getString("user_password"),
						result.getString("status"));
				list.add(e);
			}
			return list;

		} catch (SQLException s) {
			s.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Employee checkAccount(String user) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM employees WHERE username = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, user);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Employee e = new Employee();
				e.setFirstName(result.getString("first_name"));
				e.setLastName(result.getString("last_name"));
				e.setUsername(result.getString("username"));
				e.setPassword(result.getString("user_password"));
				e.setStatus(result.getString("status"));
				return e;
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateEntry(Employee e) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "UPDATE employees SET first_name = ?, last_name = ?, user_password = ?, status = ? "
					+ "WHERE username = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setString(++index, e.getFirstName());
			statement.setString(++index, e.getLastName());
			statement.setString(++index, e.getPassword());
			statement.setString(++index, e.getStatus());
			statement.setString(++index, e.getUsername());
			
			statement.execute();
			
		}catch (SQLException s) {
			s.printStackTrace();
		}
		
	}


}
