package com.sqa.controllers.users;

import java.util.Scanner;

import com.sqa.dao.users.UserAccountImpl;
import com.sqa.models.users.UserModal;

public class UserController {
	private UserModal userModel;
	
	public UserController() {
		userModel = new UserModal();
	}
	
	public UserAccountImpl login() {
		//return true or false respectively
		Scanner sc = new Scanner(System.in);

		String userName = "", password;
		
		System.out.print("Enter User Name << ");
		userName = sc.nextLine().trim();
		
		System.out.print("Enter Password << ");
		password = sc.nextLine().trim();
		
		while(!userModel.authenticate(userName, password)) {
			System.out.println("Login Failed. Please try again!");
			
			System.out.print("Enter User Name << ");
			userName = sc.nextLine().trim();
			
			System.out.print("Enter Password << ");
			password = sc.nextLine().trim();
		}
		return getUserByUserName(userName);
	}
	
	public void addUser(UserAccountImpl user) {
		if(user.getRole().equals("admin")) {
			Scanner sc = new Scanner(System.in);
			
			String userName = "", password, roleInput, role, name;
			boolean accountCreated = false;
			
			while(!accountCreated) {
				
				System.out.print("Enter Display Name << ");
				name = sc.nextLine().trim();
				
				System.out.print("Enter User Name << ");
				userName = sc.nextLine().trim();
				
				System.out.print("Enter Password << ");
				password = sc.nextLine().trim();
				
				System.out.print("Select Role -> [Admin - A || User - U (Defualt will be User!)] : ");
				
				roleInput = sc.nextLine().trim();
				if(roleInput.equalsIgnoreCase("A")) {
					System.out.println("Creating admin user .....");
					role = "admin";
				}else {
					System.out.println("Creating user .....");
					role = "user";
				}
				
				UserAccountImpl userAccountImpl = new UserAccountImpl(userName, password, 0, name, role);
				
				if(!userModel.addUser(userAccountImpl)) {
					System.out.println("User name already exist!");
				}else {
					accountCreated = true;
					System.out.println("Account is created Successfullly");
				}
			
			}
		}else {
			System.out.println("Access Denied!");
		}
			
		
	}
	
	public UserAccountImpl getUserByUserName(String userNamme) {
		UserAccountImpl userAccountImpl = userModel.getAccountByUsername(userNamme);
		
		if(userAccountImpl == null)
			throw new NullPointerException();
		
		return userAccountImpl;
		
	}
}
