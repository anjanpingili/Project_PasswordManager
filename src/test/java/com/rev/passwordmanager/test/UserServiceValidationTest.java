package com.rev.passwordmanager.test;

import org.junit.Test;

import com.rev.passwordmanager.exception.ValidationException;
import com.rev.passwordmanager.service.UserService;

public class UserServiceValidationTest {

    private UserService userService = new UserService();

    // ================= REGISTER VALIDATIONS =================

    @Test(expected = ValidationException.class)
    public void testRegisterInvalidEmail() throws ValidationException {

        userService.register(
                "abc",
                "abc",              // invalid email
                "abc123",
                "Pet name?",
                "tom"
        );
    }

    @Test(expected = ValidationException.class)
    public void testRegisterShortPassword() throws ValidationException {

        userService.register(
                "abc",
                "abc@gmail.com",
                "123",              // invalid password
                "Pet name?",
                "tom"
        );
    }

    @Test(expected = ValidationException.class)
    public void testRegisterMissingSecurityQuestion()
            throws ValidationException {

        userService.register(
                "abc",
                "abc@gmail.com",
                "abc123",
                "",                 // missing question
                "tom"
        );
    }

    @Test(expected = ValidationException.class)
    public void testRegisterMissingSecurityAnswer()
            throws ValidationException {

        userService.register(
                "abc",
                "abc@gmail.com",
                "abc123",
                "Pet name?",
                ""                  // missing answer
        );
    }

    // ================= LOGIN VALIDATIONS =================

    @Test(expected = ValidationException.class)
    public void testLoginInvalidEmail()
            throws ValidationException {

        userService.login(
                "abc",              // invalid email
                "abc123"
        );
    }

    @Test(expected = ValidationException.class)
    public void testLoginInvalidPassword()
            throws ValidationException {

        userService.login(
                "abc@gmail.com",
                "123"               // invalid password
        );
    }

    // ================= FORGOT PASSWORD VALIDATIONS =================

    @Test(expected = ValidationException.class)
    public void testForgotPasswordShortNewPassword()
            throws ValidationException {

        userService.forgotPassword(
                "abc@gmail.com",
                "tom",
                "123"               // invalid new password
        );
    }
}
