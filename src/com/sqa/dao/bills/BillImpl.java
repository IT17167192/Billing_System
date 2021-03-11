package com.sqa.dao.bills;

import java.util.ArrayList;
import java.util.Date;

import com.sqa.dao.items.ItemImpl;
import com.sqa.dao.users.UserAccountImpl;

public class BillImpl implements IBill{
	private int billId = 0;
	private ArrayList<ItemImpl> items;
	private double totalPrice;
	private Date issuedDate;
	private UserAccountImpl user;
	
	public BillImpl() {}
	
	public BillImpl(int billId, double totalPrice, Date issuedDate, UserAccountImpl user) {
		super();
		this.billId = billId;
		this.items = items;
		this.totalPrice = totalPrice;
		this.issuedDate = issuedDate;
		this.user = user;
	}
	
	public BillImpl(int billId, ArrayList<ItemImpl> items, double totalPrice, Date issuedDate, UserAccountImpl user) {
		super();
		this.billId = billId;
		this.items = items;
		this.totalPrice = totalPrice;
		this.issuedDate = issuedDate;
		this.user = user;
	}

	@Override
	public int getBillId() {
		return billId;
	}
	
	@Override
	public void setBillId(int billId) {
		this.billId = billId;
	}
	
	@Override
	public ArrayList<ItemImpl> getItems() {
		return items;
	}
	
	@Override
	public void setItems(ArrayList<ItemImpl> items) {
		this.items = items;
	}
	
	@Override
	public double getTotalPrice() {
		return totalPrice;
	}
	
	@Override
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Override
	public Date getIssuedDate() {
		return issuedDate;
	}
	
	@Override
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}
	
	@Override
	public UserAccountImpl getUser() {
		return user;
	}
	
	@Override
	public void setUser(UserAccountImpl user) {
		this.user = user;
	}
}
