package com.sqa.dao.items;

import java.util.ArrayList;

import com.sqa.dao.bills.BillImpl;

public interface IItem {
	public int getItemCode();
	public void setItemCode(int itemCode);
	public double getPrice();
	public void setPrice(double price);
	public String getItemName();
	public void setItemName(String itemName);
	public int getQuantity();
	public void setQuantity(int quantity);
	ArrayList<BillImpl> getBills();
	void setBills(ArrayList<BillImpl> bills);
}
