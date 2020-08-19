package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Admin;
import com.revature.utils.ConnectionUtility;

public class AdminDAO implements IAdminDAO {
	
	@Override
	public boolean addAdmin(Admin a) {
		try (Connection conn = ConnectionUtility.getConnection()) {

			String sql = "INSERT INTO admins (first_name, last_name, username, user_password, status)"
					+ "VALUES (?, ?, ?, ?, ?);";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setString(++index, a.getFirstName());
			statement.setString(++index, a.getLastName());
			statement.setString(++index, a.getUsername());
			statement.setString(++index, a.getPassword());
			statement.setString(++index, a.getStatus());

			statement.execute();
			return true;

		} catch (SQLException s) {
			s.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean validateUsername(String adminLogin) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM admins WHERE username = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, adminLogin);

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
	public Admin checkPassword(String adminLogin, String adminPassword) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM admins WHERE username = ? AND user_password = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, adminLogin);
			statement.setString(2, adminPassword);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Admin a = new Admin();
				a.setFirstName(result.getString("first_name"));
				a.setLastName(result.getString("last_name"));
				a.setUsername(result.getString("username"));
				a.setPassword(result.getString("user_password"));
				a.setStatus(result.getString("status"));
				return a;
			} else {
				return null;
			}

		} catch (SQLException s) {
			s.printStackTrace();
		}
		return null;
	}

	@Override
	public String checkUsername(String username) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM admins WHERE username = ?;";

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
	public List<Admin> viewPending() {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM admins WHERE status = 'Pending';";

			Statement statement = conn.createStatement();

			List<Admin> list = new ArrayList<>();

			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Admin a = new Admin(result.getString("first_name"), result.getString("last_name"),
						result.getString("username"), result.getString("user_password"),
						result.getString("status"));
				list.add(a);
			}
			return list;

		} catch (SQLException s) {
			s.printStackTrace();
		}
		return null;
		
	}

	
	@Override
	public List<Admin> viewByUser(String user) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM admins WHERE username = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, user);

			List<Admin> list = new ArrayList<>();

			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Admin a = new Admin(result.getString("first_name"), result.getString("last_name"), 
						result.getString("username"), result.getString("user_password"),
						result.getString("status"));
				list.add(a);
			}
			return list;

		} catch (SQLException s) {
			s.printStackTrace();
		}
		return null;
		
	}
	
	@Override
	public Admin checkAccount(String user) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM admins WHERE username = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, user);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Admin a = new Admin();
				a.setFirstName(result.getString("first_name"));
				a.setLastName(result.getString("last_name"));
				a.setUsername(result.getString("username"));
				a.setPassword(result.getString("user_password"));
				a.setStatus(result.getString("status"));
				return a;
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateEntry(Admin a) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "UPDATE admins SET first_name = ?, last_name = ?, user_password = ?, status = ? "
					+ "WHERE username = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setString(++index, a.getFirstName());
			statement.setString(++index, a.getLastName());
			statement.setString(++index, a.getPassword());
			statement.setString(++index, a.getStatus());
			statement.setString(++index, a.getUsername());
			
			statement.execute();
			
		}catch (SQLException s) {
			s.printStackTrace();
		}
		

	}

}
