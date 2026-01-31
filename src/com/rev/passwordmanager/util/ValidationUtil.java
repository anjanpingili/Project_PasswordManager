package com.rev.passwordmanager.util;

public class ValidationUtil {

    public static boolean isValidEmail(String email) {
    	return email != null
    	        && email.contains("@")
    	        && (email.endsWith(".com") || email.endsWith(".in"));
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
