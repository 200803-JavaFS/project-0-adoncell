package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String url = "jdbc:postgresql://javafs200803.caivdeg4po3d.us-east-1.rds.amazonaws.com:5432/projectdb";
		String username = "postgres";
		String password = "RevatureEngineer153";

		return DriverManager.getConnection(url, username, password);
	}

//	public static void main(String[] args) {
//		try (Connection conn = ConnectionUtility.getConnection()) {
//			System.out.println("connection successful");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

}
