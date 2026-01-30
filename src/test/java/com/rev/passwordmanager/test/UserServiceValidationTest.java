package com.rev.passwordmanager.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.rev.passwordmanager.exception.ValidationException;
import com.rev.passwordmanager.service.UserService;

public class UserServiceValidationTest {

    private UserService userService = new UserService();

    //Invalid email test
    @Test
    public void testRegisterInvalidEmail() {
        try {
            userService.register("TestUser", "invalidemail", "password123");
            fail("ValidationException expected for invalid email");
        } catch (ValidationException e) {
            // Expected
        }
    }

    //  Invalid password test
    @Test
    public void testRegisterInvalidPassword() {
        try {
            userService.register("TestUser", "test@gmail.com", "123");
            fail("ValidationException expected for invalid password");
        } catch (ValidationException e) {
            // Expected
        }
    }

    //Invalid login email
    @Test
    public void testLoginInvalidEmail() {
        try {
            userService.login("wrongemail", "password123");
            fail("ValidationException expected for invalid login email");
        } catch (ValidationException e) {
            // Expected
        }
    }

    // Invalid login password
    @Test
    public void testLoginInvalidPassword() {
        try {
            userService.login("test@gmail.com", "123");
            fail("ValidationException expected for invalid login password");
        } catch (ValidationException e) {
            
        }
    }
}
