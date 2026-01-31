package com.rev.passwordmanager.app;

import java.util.Scanner;

import com.rev.passwordmanager.exception.ValidationException;
import com.rev.passwordmanager.service.UserService;
import com.rev.passwordmanager.util.ValidationUtil;

public class RevPasswordManagerApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();

        while (true) {

            System.out.println("\n===== Rev Password Manager =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            try {
                switch (choice) {

                    // ================= REGISTER =================
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        if (!ValidationUtil.isValidEmail(email)) {
                            System.out.println("Invalid email format");
                            break;
                        }

                        System.out.print("Enter Password: ");
                        String password = sc.nextLine();

                        System.out.print("Enter Security Question: ");
                        String question = sc.nextLine();

                        System.out.print("Enter Security Answer: ");
                        String answer = sc.nextLine();

                        userService.register(
                                name,
                                email,
                                password,
                                question,
                                answer
                        );

                        System.out.println("Registration successful");
                        break;

                    // ================= LOGIN =================
                    case 2:
                        System.out.print("Enter Email: ");
                        String loginEmail = sc.nextLine();

                        System.out.print("Enter Password: ");
                        String loginPassword = sc.nextLine();

                        int userId =
                                userService.login(
                                        loginEmail,
                                        loginPassword
                                );

                        if (userId == -1) {
                            System.out.println("Invalid email or password");
                            break;
                        }

                        System.out.println("Login successful");
                        System.out.println("User ID: " + userId);

                        // ===== Password Vault Menu =====
                        boolean loggedIn = true;
                        while (loggedIn) {

                            System.out.println("\n===== Password Vault Menu =====");
                            System.out.println("1. Add Password");
                            System.out.println("2. List Passwords");
                            System.out.println("3. Search Password");
                            System.out.println("4. View Password");
                            System.out.println("5. Logout");
                            System.out.print("Enter choice: ");

                            int vaultChoice;
                            try {
                                vaultChoice =
                                        Integer.parseInt(sc.nextLine());
                            } catch (Exception e) {
                                System.out.println("Invalid choice");
                                continue;
                            }

                            switch (vaultChoice) {

                                case 1:
                                    System.out.print("Account Name: ");
                                    String accName = sc.nextLine();

                                    System.out.print("Username: ");
                                    String accUser = sc.nextLine();

                                    System.out.print("Password: ");
                                    String accPass = sc.nextLine();

                                    if (userService.addPassword(
                                            userId,
                                            accName,
                                            accUser,
                                            accPass)) {
                                        System.out.println(
                                                "Password added successfully");
                                    } else {
                                        System.out.println(
                                                "Failed to add password");
                                    }
                                    break;

                                case 2:
                                    userService.listPasswords(userId);
                                    break;

                                case 3:
                                    System.out.print("Enter Account Name: ");
                                    String searchAcc = sc.nextLine();
                                    userService.searchPassword(
                                            userId, searchAcc);
                                    break;

                                case 4:
                                    System.out.print("Enter ENTRY_ID: ");
                                    int entryId =
                                            Integer.parseInt(sc.nextLine());

                                    System.out.print(
                                            "Re-enter Master Password: ");
                                    String masterPass = sc.nextLine();

                                    userService.viewPassword(
                                            entryId,
                                            userId,
                                            masterPass
                                    );
                                    break;

                                case 5:
                                    loggedIn = false;
                                    System.out.println("Logged out");
                                    break;

                                default:
                                    System.out.println("Invalid choice");
                            }
                        }
                        break;

                    // ================= FORGOT PASSWORD =================
                    case 3:
                        System.out.print("Enter registered Email: ");
                        String fpEmail = sc.nextLine();

                        if (!ValidationUtil.isValidEmail(fpEmail)) {
                            System.out.println("Invalid email format");
                            break;
                        }

                        String secQuestion =
                                userService.getSecurityQuestion(fpEmail);

                        if (secQuestion == null) {
                            System.out.println("Email not found");
                            break;
                        }

                        System.out.println(
                                "Security Question: " + secQuestion);

                        System.out.print("Enter Answer: ");
                        String secAnswer = sc.nextLine();

                        System.out.print("Enter New Password: ");
                        String newPassword = sc.nextLine();

                        if (userService.forgotPassword(
                                fpEmail,
                                secAnswer,
                                newPassword)) {
                            System.out.println(
                                    "Password reset successful");
                        } else {
                            System.out.println(
                                    "Incorrect security answer");
                        }
                        break;

                    case 4:
                        System.out.println("Thank you for using RevPassword Manager!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice");
                }

            } catch (ValidationException ve) {
                System.out.println(ve.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error occurred");
            }
        }
    }
}
