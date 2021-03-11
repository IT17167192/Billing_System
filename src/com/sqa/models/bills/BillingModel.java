package com.sqa.models.bills;

import java.sql.*;
import java.util.ArrayList;

import com.sqa.dao.bills.BillImpl;
import com.sqa.dao.items.ItemImpl;
import com.sqa.dao.users.UserAccountImpl;
import com.sqa.models.connection.DatabaseConnection;

public class BillingModel {
private Connection connection;
	
	public BillingModel() {
		this.connection = DatabaseConnection.getConnection();
	}
	
	public ArrayList<BillImpl> getAllBills(){
		ArrayList<BillImpl> bills = new ArrayList<BillImpl>();
		String sql1 = "select * from bills b inner join users u on b.userId = u.id";
		String sql2 = "select * from item_bills ib inner join items it on ib.itemId = it.itemCode where ib.billId = ?";
		
		Statement st1;
		PreparedStatement st2;
		ResultSet resultSet1, resultSet2;
		
		try {
			st1 = connection.createStatement();
			resultSet1 = st1.executeQuery(sql1);
			while(resultSet1.next()) {
				BillImpl bill = new BillImpl();
				//add user details to the user object
				UserAccountImpl user = new UserAccountImpl();
				user.setUserId(resultSet1.getInt("u.id"));
				user.setUserName(resultSet1.getString("u.username"));
				user.setPassword(resultSet1.getString("u.password"));
				user.setName(resultSet1.getString("u.name"));
				user.setRole(resultSet1.getString("u.role"));
				bill.setUser(user);
				
				//add bill details to the bill object
				bill.setBillId(resultSet1.getInt("b.billId"));
				bill.setTotalPrice(resultSet1.getDouble("b.totalPrice"));
				
				ArrayList<ItemImpl> items = new ArrayList<ItemImpl>();;
				st2 = connection.prepareStatement(sql2);
				st2.setInt(1, resultSet1.getInt("b.billId"));
				
				resultSet2 = st2.executeQuery();
				
				while(resultSet2.next()) {
					ItemImpl item = new ItemImpl();
					item.setItemCode(resultSet2.getInt("it.itemCode"));
					item.setQuantity(resultSet2.getInt("it.quantity"));
					item.setItemName(resultSet2.getString("it.itemName"));
					item.setPrice(resultSet2.getDouble("it.price"));
					
					items.add(item);
				}
				
				bill.setItems(items);
				
				bills.add(bill);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bills;
	}
	
	public boolean addBill(BillImpl bill) {
		String sql1 = "insert into bills (billId, totalPrice, issuedDate, userId) values (?, ?, ?, ?)";
		String sql2 = "insert into item_bills (billId, itemId, quantity) values (?, ?, ?)";
		
		PreparedStatement st1, st2;
		ResultSet resultSet1, resultSet2;
		boolean iSuccess = true;
		
		try {
			st1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			st1.setInt(1, bill.getBillId());
			st1.setDouble(2, bill.getTotalPrice());
			st1.setString(3, null);
			st1.setInt(4, bill.getUser().getUserId());
			st1.executeUpdate();
			resultSet1 = st1.getGeneratedKeys();
			
			if(resultSet1.next()){
				st2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
				for(ItemImpl itemImpl : bill.getItems()) {
					st2.setInt(1, (int)resultSet1.getLong(1));
					st2.setInt(2, itemImpl.getItemCode());
					st2.setInt(3, itemImpl.getQuantity());
					
					st2.executeUpdate();
					resultSet2 = st2.getGeneratedKeys();
					if(resultSet2.next()){
						iSuccess = false;
					}			
				}
				
				if(iSuccess) {
					return true;
				}else {
					return false;
				}
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}
