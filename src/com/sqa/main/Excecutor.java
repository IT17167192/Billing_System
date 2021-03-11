package com.sqa.main;

import java.util.Scanner;

import com.sqa.controllers.bills.BillingController;
import com.sqa.controllers.items.ItemController;
import com.sqa.controllers.users.UserController;
import com.sqa.dao.users.UserAccountImpl;

public class Excecutor {

	public static void main(String[] args) {
		UserController userController = new UserController();
		ItemController itemController = new ItemController();
		BillingController billingController = new BillingController();
		
		UserAccountImpl userAccount = null;

		System.out.println("\n     == Welcome to Amasha's Billing System ==");
		
		String cmd = "";
		
		try {
			Scanner sc = new Scanner(System.in);
			
			if(userAccount == null){
				System.out.println("Type 'L' to Login || Type 'E' to exit");				
			}else{
				if(userAccount.getRole().equals("admin")) {
					System.out.println("Type 'A' to Add an Item || Type 'R' to Register User || Type 'B' to Add a Bill || Type 'O' to Logout || Type 'E' to exit");										
				}else {
					System.out.println("Type 'B' to Add a Bill || Type 'O' to Logout || Type 'E' to exit");										
				}
			}
			
			System.out.print("cmd << ");
			cmd = sc.nextLine().trim();
			
			while(!cmd.equalsIgnoreCase("E")) {
				if(cmd.equalsIgnoreCase("R")) {
					//only admins in the store can add users 
					if(userAccount.getRole().equals("admin")) {
						try {
							//user registration
						    userController.addUser(userAccount);			
						} catch (NullPointerException e) {
							System.out.println("User not Found!");
						} catch (Exception e) {
							System.out.println("Internal Error!");
						}
					}else {
						System.out.println("Access Denied!");
					}
				}else if(cmd.equalsIgnoreCase("A")) {
					//only admins in the store can add users 
					if(userAccount.getRole().equals("admin")) {
						//user registration
						itemController.addItem(userAccount);				
					}else {
						System.out.println("Access Denied!");
					}
				}else if(cmd.equalsIgnoreCase("L")) {
					//user login
					userAccount = userController.login();
					System.out.println("\n     *** Welcome : " 
						    + userAccount.getName() + " ***");
				}else if(cmd.equalsIgnoreCase("B")) {
					billingController.purchaseItem(userAccount);
				}else if(cmd.equalsIgnoreCase("O")) {
					userAccount = null;
					System.out.println("Logged Off!");
				}
				
				if(userAccount == null) {
					System.out.println("Type 'L' to Login || Type 'E' to exit");				
				}else {
					if(userAccount.getRole().equals("admin")) {
						System.out.println("Type 'A' to Add an Item || Type 'R' to Register User || Type 'B' to Add a Bill || Type 'O' to Logout || Type 'E' to exit");										
					}else {
						System.out.println("Type 'B' to Add a Bill || Type 'O' to Logout || Type 'E' to exit");										
					}
				}
				
				cmd = "";
				System.out.print("cmd << ");
				cmd = sc.nextLine().trim();
			}
			
			//if exists, user account should be assign to null
			userAccount = null;
			
		} catch(Exception ex) {
			System.out.println("Object not found! cccl");
			main(args);
		}
	}

}
