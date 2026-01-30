package com.rev.passwordmanager.service;

import org.apache.log4j.Logger;

import com.rev.passwordmanager.dao.PasswordEntryDAO;
import com.rev.passwordmanager.dao.UserDAO;
import com.rev.passwordmanager.exception.ValidationException;
import com.rev.passwordmanager.util.ValidationUtil;

public class UserService {

    private static final Logger logger =
            Logger.getLogger(UserService.class);

    private UserDAO userDAO = new UserDAO();
    private PasswordEntryDAO passwordEntryDAO = new PasswordEntryDAO();

    // Register
    public boolean register(String name, String email, String password)
            throws ValidationException {

        logger.info("Register request received");

        if (!ValidationUtil.isValidEmail(email)) {
            logger.warn("Invalid email entered");
            throw new ValidationException("Invalid email. Email must contain '@'");
        }

        if (!ValidationUtil.isValidPassword(password)) {
            logger.warn("Invalid password entered");
            throw new ValidationException(
                    "Invalid password. Password must be at least 6 characters");
        }

        return userDAO.registerUser(name, email, password);
    }

    // Login
    public int login(String email, String password)
            throws ValidationException {

        logger.info("Login request received");

        if (!ValidationUtil.isValidEmail(email)) {
            logger.warn("Invalid login email");
            throw new ValidationException("Invalid email format");
        }

        if (!ValidationUtil.isValidPassword(password)) {
            logger.warn("Invalid login password");
            throw new ValidationException(
                    "Invalid password. Minimum 6 characters required");
        }

        return userDAO.loginUser(email, password);
    }

    // Password operations
    public boolean addPassword(int userId, String accountName,
                               String username, String password) {
        return passwordEntryDAO.addPassword(userId, accountName, username, password);
    }

    public void listPasswords(int userId) {
        passwordEntryDAO.getAllPasswords(userId);
    }

    public void searchPassword(int userId, String accountName) {
        passwordEntryDAO.searchPassword(userId, accountName);
    }

    public void viewPassword(int entryId, int userId, String masterPassword) {
        passwordEntryDAO.viewPassword(entryId, userId, masterPassword);
    }

    public boolean updatePassword(int entryId, int userId,
                                  String newUsername, String newPassword) {
        return passwordEntryDAO.updatePassword(
                entryId, userId, newUsername, newPassword);
    }

    public boolean deletePassword(int entryId, int userId) {
        return passwordEntryDAO.deletePassword(entryId, userId);
    }
}
