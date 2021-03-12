package com.sqa.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sqa.controllers.users.UserController;
import com.sqa.models.users.UserModal;

class UserTesting {
    static UserModal userModal;
    static UserController userController;
    private String userName, password;
	
	@BeforeAll
    static void initAll() {
	   userModal = new UserModal();	
	   userController = new UserController();
    }
	
	@BeforeEach
    void init() {
		System.out.println("Init Called");
		userName = "";
		password = "";
    }
	
	@Test
	@DisplayName("User should be authenticated!")
	void shouldAuthentucateUser() {
		userName = "AmashaD";
		password = "Amasha123";
		Assertions.assertTrue(userModal.authenticate(userName, password));
	}
	
	@Test
	@DisplayName("User not an admin!")
	void notAnAdminUser() {
		userName = "YugmaF";
		password = "Yugma123";
		Assertions.assertFalse(userModal.getAccountByUsername(userName).getRole().equals("admin"));
	}
	
	@Test
	@DisplayName("User Role Should be equal to admin!")
	void shouldBeAdminUser() {
		userName = "AmashaD";
		Assertions.assertTrue(userModal.getAccountByUsername(userName).getRole().equals("admin"));
	}

	@Test
	@DisplayName("Should throw NullPointer Exception!")
	void shouldThrowNullPointerExceptionWhenUserNameNotFound() {
		Assertions.assertThrows(NullPointerException.class, () -> userController.getUserByUserName(userName));
	}
}
