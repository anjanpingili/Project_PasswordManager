package com.rev.passwordmanager.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.rev.passwordmanager.dao.PasswordEntryDAO;

public class PasswordEntryDAOTest {

    @Test
    public void testAddPassword() {

        PasswordEntryDAO dao = new PasswordEntryDAO();

        // Use a valid USER_ID from USERS table
        boolean result = dao.addPassword(
                1,
                "Gmail",
                "test@gmail.com",
                "pass123");

        assertTrue(result);
    }
}
