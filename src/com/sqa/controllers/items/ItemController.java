package com.sqa.controllers.items;

import java.util.Scanner;

import com.sqa.dao.items.ItemImpl;
import com.sqa.dao.users.UserAccountImpl;
import com.sqa.models.items.ItemModal;

public class ItemController {
	ItemModal itemModal;
	
	public ItemController() {
		itemModal = new ItemModal();
	}
	
	public void addItem(UserAccountImpl user) {
		if(user.getRole().equals("admin")) {
			//return true or false respectively
			Scanner sc = new Scanner(System.in);
			
			String itemName = "";
			double price = 0.00;
			int quantity = 0;
			
			boolean itemCreated = false;
			
			while(!itemCreated) {
				System.out.print("Enter Item Name << ");
				itemName = sc.nextLine().trim();
				
				System.out.print("Enter Item Price << ");
				price = Double.parseDouble(sc.nextLine().trim());
				
				System.out.print("Enter Item Quantity << ");
				quantity = Integer.parseInt(sc.nextLine().trim());
				
				ItemImpl itemImpl = new ItemImpl(0, price, itemName, quantity);
				
				if(itemModal.addItem(itemImpl)) {
					itemCreated = true;
					System.out.println("Item is created Successfullly");
				}
			}
		}else {
			System.out.println("Access Denied!");
		}
	}
	
	public ItemImpl searchItemByItemCode() {
		Scanner sc = new Scanner(System.in);
		String itemCode = "";
		
		System.out.print("Enter item Code << ");
		itemCode = sc.nextLine().trim();
		
		ItemImpl itemImpl = itemModal.getItemByItemCode(Integer.parseInt(itemCode));
		
		if(itemImpl == null) {
			throw new NullPointerException();
		}
		
		return itemImpl;
	}
	
	public int reduceQuantity(int itemCode) {
		Scanner sc = new Scanner(System.in);
		int quantity = 0;
		
		int currentQuantity = itemModal.getQuantityByItemCode(itemCode);
		
		boolean success = false;
		
		System.out.print("Enter quantity << ");
		quantity = Integer.parseInt(sc.nextLine().trim());
		
		//check required quantity is available
		if(currentQuantity < quantity) {
			System.out.println("Sorry! Not enough quantity!");
		}else {
			success = itemModal.updateQuantityByItemCode(itemCode, quantity);
			return success ? quantity : 0;
		}
		
		while(!success) {
			System.out.print("Enter quantity << ");
			quantity = Integer.parseInt(sc.nextLine().trim());
			
			if(currentQuantity < quantity) {
				System.out.println("Sorry! Not enough quantity!");
			}else {
				success = itemModal.updateQuantityByItemCode(itemCode, quantity);
				return success ? quantity : 0;
			}			
		}
		
		return success ? quantity : 0;
		
	}

	public boolean rollbackItemQuantity(int itemCode, int rollbackQuantity) {
		return itemModal.rollbackItemQuantity(itemCode, rollbackQuantity);
	}
}
