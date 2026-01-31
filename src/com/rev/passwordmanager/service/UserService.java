package com.rev.passwordmanager.service;

import com.rev.passwordmanager.dao.PasswordEntryDAO;
import com.rev.passwordmanager.dao.UserDAO;
import com.rev.passwordmanager.exception.ValidationException;
import com.rev.passwordmanager.util.ValidationUtil;

public class UserService {

    private UserDAO userDAO = new UserDAO();
    private PasswordEntryDAO passwordDAO = new PasswordEntryDAO();

    // ================= REGISTER =================
    public boolean register(String name,
                            String email,
                            String password,
                            String question,
                            String answer)
            throws ValidationException {

        if (!ValidationUtil.isValidEmail(email))
            throw new ValidationException("Invalid email format");

        if (!ValidationUtil.isValidPassword(password))
            throw new ValidationException(
                    "Password must be at least 6 characters");

        if (question == null || question.trim().isEmpty())
            throw new ValidationException(
                    "Security question is mandatory");

        if (answer == null || answer.trim().isEmpty())
            throw new ValidationException(
                    "Security answer is mandatory");

        return userDAO.registerUser(
                name, email, password, question, answer);
    }

    // ================= LOGIN =================
    public int login(String email, String password)
            throws ValidationException {

        if (!ValidationUtil.isValidEmail(email))
            throw new ValidationException("Invalid email format");

        if (!ValidationUtil.isValidPassword(password))
            throw new ValidationException("Invalid password");

        return userDAO.loginUser(email, password);
    }

    // ================= PASSWORD VAULT =================
    public boolean addPassword(int userId,
                               String accountName,
                               String username,
                               String password) {

        return passwordDAO.addPassword(
                userId, accountName, username, password);
    }

    public void listPasswords(int userId) {
        passwordDAO.getAllPasswords(userId);
    }

    public void searchPassword(int userId, String accountName) {
        passwordDAO.searchPassword(userId, accountName);
    }

    public void viewPassword(int entryId,
                             int userId,
                             String masterPassword)
            throws ValidationException {

        int verified =
                userDAO.loginUserById(userId, masterPassword);

        if (verified == -1)
            throw new ValidationException(
                    "Invalid master password");

        passwordDAO.viewPassword(entryId, userId);
    }

    // ================= FORGOT PASSWORD =================
    public String getSecurityQuestion(String email)
            throws ValidationException {

        if (!ValidationUtil.isValidEmail(email))
            throw new ValidationException("Invalid email format");

        return userDAO.getSecurityQuestion(email);
    }

    public boolean forgotPassword(String email,
                                  String answer,
                                  String newPassword)
            throws ValidationException {

        if (!ValidationUtil.isValidPassword(newPassword))
            throw new ValidationException(
                    "Password must be at least 6 characters");

        if (userDAO.verifySecurityAnswer(email, answer)) {
            return userDAO.resetPassword(email, newPassword);
        }
        return false;
    }
}
