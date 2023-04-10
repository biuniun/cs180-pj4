package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import users.Roles;
import users.User;

public class Login {
    private Map<String, User> users;
    private String filePath;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public Login(String filePath) {
        this.filePath = filePath;
        users = new HashMap<>();
        loadUsernamesAndPasswords();
    }

    private boolean loadUsernamesAndPasswords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.lines().forEach(l -> {
                String[] sigs = l.split(";");
                users.put(sigs[0], new User(sigs[0], sigs[1], Roles.valueOf(sigs[2])));
            });

            return true;
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return false;
        }
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public String loginUser(Scanner scanner) {
        System.out.println("Enter your email:");
        String emailAdd = scanner.nextLine();
        

        if (!users.containsKey(emailAdd)) {
            System.err.println("Username not found.");
            return null;
        }

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        if (!password.equals(users.get(emailAdd).getPassword())) {
            System.err.println("Incorrect password.");
            return null;
        }

        System.out.println("Login successful!");

        return users.get(emailAdd).getEmail();
    }

    public boolean registerUser(Scanner scanner) {

        System.out.println("Enter a new email:");
        String newUsername = scanner.nextLine();

        if (users.containsKey(newUsername)) {
            System.out.println("Username already exists. Please choose a different one.");
            return false;
        }

        if (!EMAIL_PATTERN.matcher(newUsername).matches()) {
            System.out.println("Invalid email format!");
            return false;
        }

        System.out.println("Enter a new password:");
        String newPassword = scanner.nextLine();

        System.out.println("Please select your role from Seller and Customer");
        String role = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            users.put(newUsername, new User(newUsername, newPassword, Roles.valueOf(role)));
            saveUserAccountsToFile();
            System.out.println("Registration successful!");
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to a file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Role can only be Seller and Customer!");
        }

        return false;
    }

    public void deleteAccount(String email) {
        if (!users.containsKey(email)) {
            System.out.println("Email not found!");
            return;
        }

        users.remove(email);
        saveUserAccountsToFile();
        System.out.println("Account deleted successfully!");
    }

    public void editAccount(Scanner scanner, String email) {
        if (!users.containsKey(email)) {
            System.out.println("Email not found!");
            return;
        }

        User currentUser = users.get(email);

        System.out.println("Enter a new email (leave empty to keep the current email):");
        String newEmail = scanner.nextLine();

        if (!newEmail.isEmpty()) {
            if (!EMAIL_PATTERN.matcher(newEmail).matches()) {
                System.out.println("Invalid email format!");
                return;
            }

            if (users.containsKey(newEmail)) {
                System.out.println("Email already exists! Please choose a different one.");
                return;
            }
            users.remove(email);
            currentUser.setEmail(newEmail);
            users.put(newEmail, currentUser);
            email = newEmail;
        }

        System.out.println("Enter a new password (leave empty to keep the current password):");
        String newPassword = scanner.nextLine();

        if (!newPassword.isEmpty()) {
            currentUser.setPassword(newPassword);
        }

        saveUserAccountsToFile();
        System.out.println("Account updated successfully!");
    }

    private void saveUserAccountsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users.values()) {
                writer.write(user.getEmail() + ";" + user.getPassword() + ";" + user.getUserType().name() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to a file: " + e.getMessage());
        }
    }
}
