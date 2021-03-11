package com.sqa.models.items;
import java.sql.*;
import java.util.ArrayList;

import com.sqa.dao.items.ItemImpl;
import com.sqa.models.connection.DatabaseConnection;

public class ItemModal {
	private Connection connection;
	
	public ItemModal() {
		this.connection = DatabaseConnection.getConnection();
	}
	
	public boolean addItem(ItemImpl itemImpl) {
		String sql = "insert into items (itemCode, itemName, price, quantity) values (?, ?, ?, ?)";
		
		PreparedStatement st;
		ResultSet resultSet;
		
		try {
			st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, null);
			st.setString(2, itemImpl.getItemName());
			st.setDouble(3, itemImpl.getPrice());
			st.setInt(4, itemImpl.getQuantity());
			
			st.executeUpdate();
			resultSet = st.getGeneratedKeys();
			
			if(resultSet.next()){
			   return true;
			}else {
				return false;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public ItemImpl getItemByItemCode(int itemCode) {
		String sql = "select * from items where itemCode = ?";
		
		PreparedStatement st;
		ResultSet resultSet;
		
		try {
			st = connection.prepareStatement(sql);
			st.setInt(1, itemCode);
			
			resultSet = st.executeQuery();
			
			if(resultSet.next()) {
				ItemImpl item = new ItemImpl();
				item.setItemCode(resultSet.getInt("itemCode"));
				item.setQuantity(resultSet.getInt("quantity"));
				item.setItemName(resultSet.getString("itemName"));
				item.setPrice(resultSet.getDouble("price"));
				
				return item;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getQuantityByItemCode(int itemCode) {
		String sql = "select quantity from items where itemCode = ?";
		
		PreparedStatement st;
		ResultSet resultSet;
		
		try {
			st = connection.prepareStatement(sql);
			st.setInt(1, itemCode);
			
			resultSet = st.executeQuery();
			
			if(resultSet.next()) {
				return resultSet.getInt("quantity");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public boolean rollbackItemQuantity(int itemCode, int rollBackQuantity) {
		String sql = "update items set quantity=? where itemCode=?";
		int balance = getQuantityByItemCode(itemCode) + rollBackQuantity;
		
		if(balance < 0) {
			return false;
		}
		
		PreparedStatement st;
		ResultSet resultSet;
		
		try {
			st = connection.prepareStatement(sql, Statement. RETURN_GENERATED_KEYS);
			st.setInt(1, balance);
			st.setInt(2, itemCode);
			
			st.executeUpdate();
			resultSet = st.getGeneratedKeys();
			if(!resultSet.next()){
			   return true;
			}else {
				return false;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateQuantityByItemCode(int itemCode, int purchasedQuantity) {
		String sql = "update items set quantity=? where itemCode=?";
		int balance = getQuantityByItemCode(itemCode) - purchasedQuantity;
		
		if(balance < 0) {
			return false;
		}
		
		PreparedStatement st;
		ResultSet resultSet;
		
		try {
			st = connection.prepareStatement(sql, Statement. RETURN_GENERATED_KEYS);
			st.setInt(1, balance);
			st.setInt(2, itemCode);
			
			st.executeUpdate();
			resultSet = st.getGeneratedKeys();
			if(!resultSet.next()){
			   return true;
			}else {
				return false;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
