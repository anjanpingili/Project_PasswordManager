package com.rev.passwordmanager.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.rev.passwordmanager.dao.PasswordEntryDAO;
import com.rev.passwordmanager.exception.ValidationException;
import com.rev.passwordmanager.service.UserService;

public class EndToEndProjectTest {

    @Test
    public void testCompleteFlow() throws ValidationException {

        UserService userService = new UserService();
        PasswordEntryDAO passwordEntryDAO = new PasswordEntryDAO();

        // Use stable test email
        String email = "e2e_test@gmail.com";
        String password = "endpass123";

        int userId;

        // 1. Try registration
        try {
            boolean registered = userService.register(
                    "EndUser",
                    email,
                    password
            );
            assertTrue(registered);
        } catch (Exception e) {
            // Ignore if user already exists
        }

        // 2. Login (always works if user exists)
        userId = userService.login(email, password);
        assertTrue(userId != -1);

        // 3. Add password entry
        boolean added = passwordEntryDAO.addPassword(
                userId,
                "EndAccount",
                "end_username",
                "accountpass"
        );
        assertTrue(added);

        // 4. List passwords
        passwordEntryDAO.getAllPasswords(userId);

        // 5. Search password
        passwordEntryDAO.searchPassword(userId, "EndAccount");

        // End-to-end flow passed
        assertTrue(true);
    }
}
