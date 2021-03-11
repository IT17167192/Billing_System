package com.sqa.dao.items;

import java.util.ArrayList;

import com.sqa.dao.bills.BillImpl;

public class ItemImpl implements IItem{
	private int itemCode;
	private double price;
	private String itemName;
	private int quantity;
	private ArrayList<BillImpl> bills;
	
	public ItemImpl() {
		
	}
	
	public ItemImpl(int itemCode, double price, String itemName, int quantity) {
		super();
		this.itemCode = itemCode;
		this.price = price;
		this.itemName = itemName;
		this.quantity = quantity;
	}
	
	public ItemImpl(int itemCode, double price, String itemName, int quantity, ArrayList<BillImpl> bills) {
		super();
		this.itemCode = itemCode;
		this.price = price;
		this.itemName = itemName;
		this.quantity = quantity;
		this.bills = bills;
	}

	@Override
	public int getItemCode() {
		return itemCode;
	}

	@Override
	public void setItemCode(int itemCode) {
		this.itemCode = itemCode;
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String getItemName() {
		return itemName;
	}

	@Override
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public ArrayList<BillImpl> getBills() {
		return bills;
	}

	
	@Override
	public void setBills(ArrayList<BillImpl> bills) {
		this.bills = bills;
	}
	
	
	
}
