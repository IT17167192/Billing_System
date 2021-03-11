package com.sqa.dao.bills;

import java.util.ArrayList;
import java.util.Date;

import com.sqa.dao.items.ItemImpl;
import com.sqa.dao.users.UserAccountImpl;

public interface IBill {
	public int getBillId();
	public void setBillId(int billId);
	public ArrayList<ItemImpl> getItems();
	public void setItems(ArrayList<ItemImpl> items);
	public double getTotalPrice();
	public void setTotalPrice(double totalPrice);
	public Date getIssuedDate();
	public void setIssuedDate(Date issuedDate);
	public UserAccountImpl getUser();
	public void setUser(UserAccountImpl user);
}
