package com.sqa.dao.users;

public class UserAccountImpl implements IUserAccount{
	String userName;
	String password;
	int userId;
	String role;
	String name;
	
	public UserAccountImpl() {
		
	}
	
	public UserAccountImpl(String userName, String password, int userId, String name, String role) {
		super();
		this.userName = userName;
		this.password = password;
		this.userId = userId;
		this.name = name;
		this.role = role;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Override
	public String getUserName() {
		return userName;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public int getUserId() {
		return userId;
	}

	@Override
	public String getRole() {
		return role;
	}

	@Override
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void validateUserNameEmpty() {
		System.out.println(this.userName.isBlank());
		if(this.userName.isBlank())
			throw new RuntimeException("User Name cannot be Empty!");
	}

	@Override
	public void validatePasswordEmpty() {
		if(this.password.isBlank())
			throw new RuntimeException("Password cannot be Empty!");
	}
	
	
	
}
