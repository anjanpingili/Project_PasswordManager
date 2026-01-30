package com.rev.passwordmanager.app;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.rev.passwordmanager.exception.ValidationException;
import com.rev.passwordmanager.service.UserService;

public class RevPasswordManagerApp {

    private static final Logger logger =
            Logger.getLogger(RevPasswordManagerApp.class);

    public static void main(String[] args) {

        logger.info("Rev Password Manager Application Started");

        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();

        int loggedInUserId = -1;

        while (true) {

            System.out.println("\n ----> Rev Password Manager <----");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Add Password");
            System.out.println("4. List Passwords");
            System.out.println("5. Search Password");
            System.out.println("6. View Password");
            System.out.println("7. Update Password");
            System.out.println("8. Delete Password");
            System.out.println("9. Logout");
            System.out.println("10. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {

                switch (choice) {

                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Password: ");
                        String password = sc.nextLine();

                        if (userService.register(name, email, password)) {
                            logger.info("User registered successfully");
                            System.out.println("Registration successful");
                        }
                        break;

                    case 2:
                        System.out.print("Enter Email: ");
                        String loginEmail = sc.nextLine();

                        System.out.print("Enter Password: ");
                        String loginPassword = sc.nextLine();

                        loggedInUserId =
                                userService.login(loginEmail, loginPassword);

                        if (loggedInUserId != -1) {
                            logger.info("User logged in successfully");
                            System.out.println("Login successful");
                        } else {
                            System.out.println("Invalid credentials");
                        }
                        break;

                    case 3:
                        if (loggedInUserId == -1) {
                            System.out.println("Please login first");
                            break;
                        }

                        System.out.print("Account Name: ");
                        String accountName = sc.nextLine();

                        System.out.print("Username: ");
                        String accUsername = sc.nextLine();

                        System.out.print("Password: ");
                        String accPassword = sc.nextLine();

                        if (userService.addPassword(
                                loggedInUserId,
                                accountName,
                                accUsername,
                                accPassword)) {

                            logger.info("Password added");
                            System.out.println("Password added successfully");
                        } else {
                            System.out.println("Failed to add password");
                        }
                        break;

                    case 4:
                        if (loggedInUserId == -1) {
                            System.out.println("Please login first");
                            break;
                        }
                        userService.listPasswords(loggedInUserId);
                        break;

                    case 5:
                        if (loggedInUserId == -1) {
                            System.out.println("Please login first");
                            break;
                        }

                        System.out.print("Enter Account Name: ");
                        String searchAccount = sc.nextLine();
                        userService.searchPassword(loggedInUserId, searchAccount);
                        break;

                    case 6:
                        if (loggedInUserId == -1) {
                            System.out.println("Please login first");
                            break;
                        }

                        System.out.print("Enter ENTRY_ID: ");
                        int viewId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Re-enter Master Password: ");
                        String masterPassword = sc.nextLine();

                        userService.viewPassword(
                                viewId,
                                loggedInUserId,
                                masterPassword);
                        break;

                    case 7:
                        if (loggedInUserId == -1) {
                            System.out.println("Please login first");
                            break;
                        }

                        System.out.print("Enter ENTRY_ID: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("New Username: ");
                        String newUsername = sc.nextLine();

                        System.out.print("New Password: ");
                        String newPassword = sc.nextLine();

                        if (userService.updatePassword(
                                updateId,
                                loggedInUserId,
                                newUsername,
                                newPassword)) {

                            logger.info("Password updated");
                            System.out.println("Password updated successfully");
                        } else {
                            System.out.println("Update failed");
                        }
                        break;

                    case 8:
                        if (loggedInUserId == -1) {
                            System.out.println("Please login first");
                            break;
                        }

                        System.out.print("Enter ENTRY_ID: ");
                        int deleteId = sc.nextInt();
                        sc.nextLine();

                        if (userService.deletePassword(
                                deleteId,
                                loggedInUserId)) {

                            logger.info("Password deleted");
                            System.out.println("Password deleted successfully");
                        } else {
                            System.out.println("Delete failed");
                        }
                        break;

                    case 9:
                        loggedInUserId = -1;
                        logger.info("User logged out");
                        System.out.println("Logout successful");
                        break;

                    case 10:
                        logger.info("Application exited");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice");
                }

            } catch (ValidationException e) {
                logger.warn("Validation error: " + e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }
}
