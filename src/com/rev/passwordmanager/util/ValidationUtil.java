package com.rev.passwordmanager.util;

public class ValidationUtil {

    // Validate email-- it ensures that the email cannot be empty and it must contain @ to validate the domain
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.contains("@");
    }

    // To Validate password length specifying that the password must be greater than or qual to 6 charachters
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= 6;
    }
}
