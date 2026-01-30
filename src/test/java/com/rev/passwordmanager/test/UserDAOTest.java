package com.rev.passwordmanager.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.rev.passwordmanager.dao.UserDAO;

public class UserDAOTest {

    private UserDAO userDAO;

    @Before
    public void setUp() {
        userDAO = new UserDAO();
    }

    @Test
    public void testUserRegistration() {

        String email = "junit_" + System.currentTimeMillis() + "@gmail.com";

        boolean result = userDAO.registerUser(
                "JUnitUser",
                email,
                "junit123"
        );

        assertTrue(result);
    }

    @Test
    public void testUserLogin() {

        String email = "login_" + System.currentTimeMillis() + "@gmail.com";

        userDAO.registerUser("LoginUser", email, "login123");

        int userId = userDAO.loginUser(email, "login123");

        assertTrue(userId != -1);
    }
}
