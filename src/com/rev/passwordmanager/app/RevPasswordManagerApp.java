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
                System.out.println("Invalid choice");
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
                                name, email, password, question, answer);

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
                                        loginEmail, loginPassword);

                        System.out.println("Login successful");
                        System.out.println("User ID: " + userId);
                        break;

                    // ================= FORGOT PASSWORD =================
                    case 3:
                        System.out.print("Enter registered Email: ");
                        String fpEmail = sc.nextLine();

                        String secQ =
                                userService.getSecurityQuestion(fpEmail);

                        if (secQ == null) {
                            System.out.println("Email not found");
                            break;
                        }

                        System.out.println(
                                "Security Question: " + secQ);

                        System.out.print("Enter Answer: ");
                        String secAns = sc.nextLine();

                        System.out.print("Enter New Password: ");
                        String newPass = sc.nextLine();

                        if (userService.forgotPassword(
                                fpEmail, secAns, newPass)) {
                            System.out.println(
                                    "Password reset successful");
                        } else {
                            System.out.println(
                                    "Incorrect security answer");
                        }
                        break;

                    case 4:
                        System.out.println("Thank you!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice");
                }

            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
