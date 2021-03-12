package com.sqa.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import com.sqa.controllers.bills.BillingController;
import com.sqa.controllers.items.ItemController;
import com.sqa.dao.bills.BillImpl;
import com.sqa.dao.items.ItemImpl;
import com.sqa.models.bills.BillingModel;
import com.sqa.models.items.ItemModal;
import com.sqa.models.users.UserModal;

@DisplayName("Billing testing")
class BillingTesting {
	static BillingModel billingModel;
	static ItemModal itemModal;
	static UserModal userModal;
	
	static ItemController itemController;
	static BillingController billingController;
	
	static ItemImpl item;
	
	static int itemCode;
	
	static double totalPrice;
	static int quantity = 0;
	
	@BeforeAll
    static void initAll() {
	   System.out.println("Initiating All variables");
	  
	   billingModel = new BillingModel();
	   itemModal = new ItemModal();
	   userModal = new UserModal();	
	   
	   itemController = new ItemController();
	   billingController = new BillingController();
	   
	   totalPrice = 0;
    }
	
	@Test
    @Order(1)
    void initItemCode() {
        itemCode = 1;
        System.out.println("ItemCode initialized!");
    }
	
	@Test
	@Order(2)
	@DisplayName("Item name should equal to 'Car Toy'")
    void assertWithHamcrestMatcher() {
		System.out.println("Getting item name");
        assertThat(itemModal.getItemByItemCode(itemCode).getItemName(), is(equalTo("Car Toy")));
    }

	//Nested testing class for test bill
	@Nested
	@DisplayName("Bill testing batch")
	class TestBill {
		
		@BeforeEach
		void init() {
			System.out.println("Item count in the list : " + billingController.getItems().size());
			itemCode = 0;
		}
		
		@Test
	    @Order(1)
		@DisplayName("Get item 1")
	    void getItem_1() {
	        itemCode = 1;
	        item = itemModal.getItemByItemCode(itemCode);
	        item.setQuantity(2);
			totalPrice += (item.getPrice() * item.getQuantity());
			
			//add to arraylist
			billingController.addItemToList(item);
			
			//reduce quantity and test success
			assertTrue(itemModal.updateQuantityByItemCode(item.getItemCode(), item.getQuantity()));
	    }
		
		@Test
	    @Order(2)
		@DisplayName("Get item 2")
	    void getItem_2() {
	        itemCode = 2;
	        item = itemModal.getItemByItemCode(itemCode);
	        item.setQuantity(1);
	        totalPrice += (item.getPrice() * item.getQuantity());
	        
	        //add to arraylist
	        billingController.addItemToList(item);
	        
	        //reduce quantity and test success
	        assertTrue(itemModal.updateQuantityByItemCode(item.getItemCode(), item.getQuantity()));
	    }
		
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("After all test cases are executed,  add the bill to db!");
		//check total price
		assertThat(totalPrice, is(equalTo(16500.0)));
		//create Bill object
		BillImpl billImpl = new BillImpl();
		billImpl.setItems(billingController.getItems());
		billImpl.setTotalPrice(totalPrice);
		billImpl.setUser(userModal.getAccountByUsername("AmashaD"));
		assertTrue(billingModel.addBill(billImpl));
		billingController.clearItemsFromArrayList();
		
	}
	
	@Test
	@Disabled("Disabled test case")
	@DisplayName("Should throw NullPointer Exception when input is given as 0!")
	void shouldThrowNullPointerExceptionWhenUserNameNotFound() {
		Assertions.assertThrows(NullPointerException.class, () -> itemController.searchItemByItemCode());
	}
	
}
