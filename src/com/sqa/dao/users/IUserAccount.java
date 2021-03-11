package com.sqa.dao.users;

public interface IUserAccount {
	public void setName(String name);
	public String getName();
	public void setRole(String role);
	public String getRole();
	public void setUserName(String userName);
	public void setPassword(String password);
	public void setUserId(int userId);
	public String getUserName();
	public String getPassword();
	public int getUserId();
	public void validateUserNameEmpty();
	public void validatePasswordEmpty();
}
