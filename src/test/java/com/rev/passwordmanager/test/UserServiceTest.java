package com.rev.passwordmanager.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rev.passwordmanager.dao.PasswordEntryDAO;
import com.rev.passwordmanager.dao.UserDAO;
import com.rev.passwordmanager.exception.ValidationException;
import com.rev.passwordmanager.service.UserService;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private PasswordEntryDAO passwordDAO;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // ================= REGISTER =================
    @Test
    public void testRegisterSuccess() throws ValidationException {

        when(userDAO.registerUser(
                "abc",
                "abc@gmail.com",
                "abc123",
                "Pet name?",
                "tom"))
            .thenReturn(true);

        boolean result =
            userService.register(
                "abc",
                "abc@gmail.com",
                "abc123",
                "Pet name?",
                "tom");

        assertTrue(result);
    }

    @Test(expected = ValidationException.class)
    public void testRegisterInvalidEmail()
            throws ValidationException {

        userService.register(
            "abc",
            "abc",
            "abc123",
            "Pet name?",
            "tom");
    }

    // ================= LOGIN =================
    @Test
    public void testLoginSuccess()
            throws ValidationException {

        when(userDAO.loginUser(
                "abc@gmail.com",
                "abc123"))
            .thenReturn(1);

        int userId =
            userService.login(
                "abc@gmail.com",
                "abc123");

        assertEquals(1, userId);
    }

    // ================= VIEW PASSWORD =================
    @Test
    public void testViewPasswordSuccess()
            throws ValidationException {

        when(userDAO.loginUserById(1, "abc123"))
            .thenReturn(1);

        userService.viewPassword(10, 1, "abc123");

        verify(passwordDAO)
            .viewPassword(10, 1);
    }

    // ================= FORGOT PASSWORD =================
    @Test
    public void testForgotPasswordSuccess()
            throws ValidationException {

        when(userDAO.verifySecurityAnswer(
                "abc@gmail.com", "tom"))
            .thenReturn(true);

        when(userDAO.resetPassword(
                "abc@gmail.com", "newpass123"))
            .thenReturn(true);

        boolean result =
            userService.forgotPassword(
                "abc@gmail.com",
                "tom",
                "newpass123");

        assertTrue(result);
    }
}
