package com.sqa.models.connection;

import java.sql.*;

public class DatabaseConnection {
	private volatile static Connection connection;
	
	private DatabaseConnection() {}
	
	public static Connection getConnection(){
		if(connection == null) {
			synchronized (DatabaseConnection.class) {
				if(connection == null) {
					try {
						Class.forName("com.mysql.jdbc.Driver");
						connection = DriverManager
								.getConnection("jdbc:mysql://localhost:3306/billing_system?createDatabaseIfNotExist=true&&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
						return connection;
					
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		}
		
		return connection;
	}
}
