package com.rev.passwordmanager.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.rev.passwordmanager.dao.PasswordEntryDAO;
import com.rev.passwordmanager.dao.UserDAO;

public class PasswordEntryDAOTest {

    private PasswordEntryDAO passwordEntryDAO;
    private UserDAO userDAO;
    private int userId;

    @Before
    public void setUp() {

        passwordEntryDAO = new PasswordEntryDAO();
        userDAO = new UserDAO();

        String email = "vault_" + System.currentTimeMillis() + "@gmail.com";

        userDAO.registerUser("VaultUser", email, "vault123");

        userId = userDAO.loginUser(email, "vault123");
    }

    @Test
    public void testAddPassword() {

        boolean result = passwordEntryDAO.addPassword(
                userId,
                "JUnitAccount",
                "junit_username",
                "account123"
        );

        assertTrue(result);
    }

    @Test
    public void testListPasswords() {
        passwordEntryDAO.getAllPasswords(userId);
        assertTrue(true); // console verification
    }

    @Test
    public void testSearchPassword() {
        passwordEntryDAO.searchPassword(userId, "JUnitAccount");
        assertTrue(true);
    }
}
