package com.sqa.controllers.bills;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.sqa.controllers.items.ItemController;
import com.sqa.dao.bills.BillImpl;
import com.sqa.dao.items.ItemImpl;
import com.sqa.dao.users.UserAccountImpl;
import com.sqa.models.bills.BillingModel;

public class BillingController {
	BillingModel billingModel;
	ArrayList<ItemImpl> items;
	public BillingController() {
		billingModel = new BillingModel();
		items = new ArrayList<ItemImpl>();
	}
	
	public void purchaseItem(UserAccountImpl user) {
		//return true or false respectively
		Scanner sc = new Scanner(System.in);
		ItemController itemController = new ItemController();
		
		double totalPrice = 0.00;
		
		String cmd;
		
		try {
			
			ItemImpl item = itemController.searchItemByItemCode();	
			int quantity = itemController.reduceQuantity(item.getItemCode());
			item.setQuantity(quantity);
			addItemToList(item);

			double priceEach = item.getPrice() * quantity;
			totalPrice += priceEach;
			
			System.out.print("Do you want to add more items? (Y/N) : ");
			cmd = sc.nextLine().trim();
			
			while(!cmd.equalsIgnoreCase("N")) {
				item = itemController.searchItemByItemCode();
				ItemImpl itemToCheck = checkItemExist(getItems(), item);
				
				String itemUpdateCmd = "";
				
				if(itemToCheck != null) {
					System.out.print("Item already added! Do you want to update added quanity (Y/N) ? ");
					itemUpdateCmd = sc.nextLine().trim();
				}
				
				if(itemUpdateCmd.equalsIgnoreCase("Y")) {
					//set item as updated item
					item = itemToCheck;
					
					//reduce price from total price
					totalPrice -= (item.getPrice() * item.getQuantity());
					
					//remove from list
					removeItemByItem(item);
					
					//update quantity
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
				addItemToList(item);
				System.out.print("Do you want to add more items? (Y/N) : ");
				cmd = sc.nextLine().trim();
			}
			
			BillImpl billImpl = new BillImpl();
			billImpl.setItems(getItems());
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
			//clear arrayList
			clearItemsFromArrayList();
			
			System.out.println("Bill Successfully Added!");
		} catch (NullPointerException e) {
			System.out.println("Sorry! Item not found!");
			e.printStackTrace();
		}
		
	}
	
	public ItemImpl checkItemExist(ArrayList<ItemImpl> items, ItemImpl item) {
		
		for(ItemImpl i : getItems()) {
			if(i.getItemCode() == item.getItemCode())
				return i;
		}
		return null;
	}
	
	public void addItemToList(ItemImpl item) {
		items.add(item);
	}
	
	public ArrayList<ItemImpl> getItems() {
		return items;
	}
	
	public void clearItemsFromArrayList() {
		items.clear();
	}
	
	public void removeItemByItem(ItemImpl item) {
		int pos = 0;
		for(ItemImpl i : getItems()) {
			if(i.getItemCode() == item.getItemCode()) {
				break;
			}
			pos++;
		}
		
		items.remove(pos);
	}
}
