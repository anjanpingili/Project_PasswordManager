package com.rev.passwordmanager.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.rev.passwordmanager.dao.UserDAO;

public class UserDAOTest {

    @Test
    public void testRegisterUser() {

        UserDAO dao = new UserDAO();

        boolean result = dao.registerUser(
                "daoUser",
                "dao@gmail.com",
                "dao123",
                "Birth city?",
                "hyd");

        assertTrue(result);
    }

    @Test
    public void testLoginUser() {

        UserDAO dao = new UserDAO();
        int userId = dao.loginUser(
                "dao@gmail.com", "dao123");

        assertTrue(userId > 0);
    }
}
