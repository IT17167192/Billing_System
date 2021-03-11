package com.sqa.controllers.bills;

import java.util.ArrayList;
import java.util.Scanner;

import com.sqa.controllers.items.ItemController;
import com.sqa.dao.bills.BillImpl;
import com.sqa.dao.items.ItemImpl;
import com.sqa.dao.users.UserAccountImpl;
import com.sqa.models.bills.BillingModel;

public class BillingController {
	BillingModel billingModel;
	
	public BillingController() {
		billingModel = new BillingModel();
	}
	
	public void purchaseItem(UserAccountImpl user) {
		//return true or false respectively
		Scanner sc = new Scanner(System.in);
		ItemController itemController = new ItemController();
		
		double totalPrice = 0.00;
		
		String cmd;
		
		try {
			ArrayList<ItemImpl> items = new ArrayList<ItemImpl>();
			
			ItemImpl item = itemController.searchItemByItemCode();	
			int quantity = itemController.reduceQuantity(item.getItemCode());
			
			item.setQuantity(quantity);
			items.add(item);

			double priceEach = item.getPrice() * quantity;
			totalPrice += priceEach;
			
			System.out.print("Do you want to add more items? (Y/N) : ");
			cmd = sc.nextLine().trim();
			
			while(!cmd.equalsIgnoreCase("N")) {
				item = itemController.searchItemByItemCode();
				ItemImpl itemToCheck = checkItemExist(items, item);
				
				String itemUpdateCmd = "";
				
				if(itemToCheck != null) {
					System.out.print("Item already added! Do you want to update added quanity (Y/N) ? ");
					itemUpdateCmd = sc.nextLine().trim();
				}
				
				if(itemUpdateCmd.equalsIgnoreCase("Y")) {
					if(itemController.rollbackItemQuantity(itemToCheck.getItemCode(), itemToCheck.getQuantity()));
						System.out.println("Provide Update Quantity!");
				}else if(itemUpdateCmd.equalsIgnoreCase("N")){
					itemToCheck = null;
					continue;
				}
				
				quantity = itemController.reduceQuantity(item.getItemCode());
				
				priceEach = item.getPrice() * quantity;
				totalPrice += priceEach;
				
				item.setQuantity(quantity);
				items.add(item);
				System.out.print("Do you want to add more items? (Y/N) : ");
				cmd = sc.nextLine().trim();
			}
			
			BillImpl billImpl = new BillImpl();
			billImpl.setItems(items);
			billImpl.setTotalPrice(totalPrice);
			billImpl.setUser(user);
			System.out.println("Total Price : " + totalPrice);
			
			System.out.print("Enter Pay amount : ");
			double payAmount = Double.parseDouble(sc.nextLine().trim());
			
			while(payAmount < totalPrice) {
				System.out.println("Sorry! Pay amount is invalid!");
				System.out.print("Enter Pay amount : ");
				payAmount = Double.parseDouble(sc.nextLine().trim());
			}
			
			System.out.println("Balance : " + (payAmount - totalPrice));
			
			boolean success = billingModel.addBill(billImpl);
			System.out.println("Bill Successfully Added!");
		} catch (NullPointerException e) {
			System.out.println("Sorry! Item not found!");
			e.printStackTrace();
		}
		
	}
	
	public ItemImpl checkItemExist(ArrayList<ItemImpl> items, ItemImpl item) {
		
		for(ItemImpl i : items) {
			if(i.getItemCode() == item.getItemCode())
				return i;
		}
		return null;
	}
}
