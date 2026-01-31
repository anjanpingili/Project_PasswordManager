package com.rev.passwordmanager.util;

import java.util.Random;

public class PasswordGeneratorUtil {

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL = "@#$%&*!";

    public static String generatePassword(int length,
                                          boolean useChars,
                                          boolean useNumbers,
                                          boolean useSpecial) {

        String allowedChars = "";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        if (useChars) {
            allowedChars += CHARACTERS;
        }
        if (useNumbers) {
            allowedChars += NUMBERS;
        }
        if (useSpecial) {
            allowedChars += SPECIAL;
        }

        if (allowedChars.length() == 0) {
            return null;
        }

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(index));
        }

        return password.toString();
    }
}
