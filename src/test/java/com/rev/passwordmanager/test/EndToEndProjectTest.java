package com.rev.passwordmanager.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.rev.passwordmanager.dao.PasswordEntryDAO;
import com.rev.passwordmanager.dao.UserDAO;

public class EndToEndProjectTest {

    @Test
    public void testCompleteFlow() {

        UserDAO userDAO = new UserDAO();
        PasswordEntryDAO pwdDAO = new PasswordEntryDAO();

        // Register
        boolean registered = userDAO.registerUser(
                "e2e",
                "e2e@gmail.com",
                "e2e123",
                "Fav color?",
                "blue");

        assertTrue(registered);

        // Login
        int userId = userDAO.loginUser(
                "e2e@gmail.com", "e2e123");

        assertTrue(userId > 0);

        // Add password
        boolean added = pwdDAO.addPassword(
                userId,
                "Facebook",
                "fb@gmail.com",
                "fbpass");

        assertTrue(added);
    }
}
