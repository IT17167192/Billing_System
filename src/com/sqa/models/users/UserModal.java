package com.sqa.models.users;
import java.sql.*;
import java.util.ArrayList;

import com.sqa.dao.users.UserAccountImpl;
import com.sqa.models.connection.DatabaseConnection;

public class UserModal {
	private Connection connection;
	
	public UserModal() {
		this.connection = DatabaseConnection.getConnection();
	}
	
	public ArrayList<UserAccountImpl> getAllUsers(){
		ArrayList<UserAccountImpl> users = new ArrayList<UserAccountImpl>();
		
		Statement st;
		ResultSet resultSet;
		
		try {
			st = connection.createStatement();
			resultSet = st.executeQuery("SELECT * FROM users");
			
			while (resultSet.next()) {
				UserAccountImpl userAccountImpl = new UserAccountImpl();
				userAccountImpl.setUserId(resultSet.getInt("id"));
				userAccountImpl.setUserName(resultSet.getString("username"));
				userAccountImpl.setPassword(resultSet.getString("password"));
				userAccountImpl.setName(resultSet.getString("name"));
				userAccountImpl.setRole(resultSet.getString("role"));
				
				users.add(userAccountImpl);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return users;
	} 

	public boolean authenticate(String userName, String password) {
		
		String sql = "select * from users where username = ? and password = md5(?)";
		
		PreparedStatement st;
		ResultSet resultSet;
		
		try {
			st = connection.prepareStatement(sql);
			
			st.setString(1, userName);
			st.setString(2, password);
			
			resultSet = st.executeQuery();
			
			if(resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public UserAccountImpl getAccountByUsername(String userName) {
		String sql = "select * from users where username = ?";
		
		PreparedStatement st;
		ResultSet resultSet;
		
		try {
			st = connection.prepareStatement(sql);
			st.setString(1, userName);
			
			resultSet = st.executeQuery();
			
			if(resultSet.next()) {
				
				UserAccountImpl userAccountImpl = new UserAccountImpl();
				userAccountImpl.setUserId(resultSet.getInt("id"));
				userAccountImpl.setUserName(resultSet.getString("username"));
				userAccountImpl.setPassword(resultSet.getString("password"));
				userAccountImpl.setName(resultSet.getString("name"));
				userAccountImpl.setRole(resultSet.getString("role"));
				
				return userAccountImpl;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//should handle the NullPointerException in the user-end using try catch
		return null;
	}
	
	public boolean addUser(UserAccountImpl userAccountImpl) {
		//userName must be unique
		if(getAccountByUsername(userAccountImpl.getUserName()) != null) {
			return false;
		}
		
		String sql = "insert into users (id, username, name, password, role) values (?, ?, ?, md5(?), ?)";
		
		PreparedStatement st;
		ResultSet resultSet;
		
		try {
			st = connection.prepareStatement(sql, Statement. RETURN_GENERATED_KEYS);
			st.setString(1, null);
			st.setString(2, userAccountImpl.getUserName());
			st.setString(3, userAccountImpl.getName());
			st.setString(4, userAccountImpl.getPassword());
			st.setString(5, userAccountImpl.getRole());
			
			st.executeUpdate();
			resultSet = st.getGeneratedKeys();
			
			if(resultSet.next()){
			   return true;
			}else {
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
