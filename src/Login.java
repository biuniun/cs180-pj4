/*
TXT FILE FORMAT MUST BE:
username245 // username
password245 // respective password
seller      // indication of customer or seller
username123
password123
customer
username768
password768
customer
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

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

    private void loadUsernamesAndPasswords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String email = line.trim();

                if (!EMAIL_PATTERN.matcher(email).matches()) {
                    System.err.println("Warning: Skipping invalid email format: " + email);
                    continue;
                }

                String password = reader.readLine();
                if (password == null) {
                    System.err.println("Error: Email " + email + " has no corresponding password!");
                    break;
                }

                String userType = reader.readLine();
                if (userType == null) {
                    System.err.println("Error: Email " + email + " has no corresponding user type!");
                    break;
                }

                users.put(email, new User(email, password.trim(), userType.trim()));
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static class User {
        String email;
        String password;
        String userType;

        public User(String email, String password, String userType) {
            this.email = email;
            this.password = password;
            this.userType = userType;
        }
    }

    public void loginUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your email:");
        String email = scanner.nextLine();

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            System.out.println("Invalid email format!");
            return;
        }

        if (!users.containsKey(email)) {
            throw new IllegalArgumentException("Email not found!");
        }

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        if (!password.equals(users.get(email))) {
            throw new IllegalArgumentException("Incorrect password!");
        }

        System.out.println("Login successful!");
    }

    public void registerUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a new email:");
        String newEmail = scanner.nextLine();

        if (!EMAIL_PATTERN.matcher(newEmail).matches()) {
            System.out.println("Invalid email format!");
            return;
        }

        if (users.containsKey(newEmail)) {
            System.out.println("Email already exists! Please choose a different one.");
            return;
        }

        System.out.println("Enter a new password:");
        String newPassword = scanner.nextLine();

        System.out.println("Are you a customer or seller? (enter 'customer' or 'seller'):");
        String userType = scanner.nextLine().toLowerCase();
        while (!userType.equals("customer") && !userType.equals("seller")) {
            System.out.println("Invalid user type. Please enter either 'customer' or 'seller':");
            userType = scanner.nextLine().toLowerCase();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("\n" + newEmail + "\n" + newPassword + "\n" + userType);
            users.put(newEmail, new User(newEmail, newPassword, userType));
            System.out.println("Registration successful!");
        } catch (IOException e) {
            System.err.println("Error writing to a file: " + e.getMessage());
        }
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

    public void editAccount(String email) {
        Scanner scanner = new Scanner(System.in);

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
            currentUser.email = newEmail;
            users.put(newEmail, currentUser);
            email = newEmail;
        }

        System.out.println("Enter a new password (leave empty to keep the current password):");
        String newPassword = scanner.nextLine();

        if (!newPassword.isEmpty()) {
            currentUser.password = newPassword;
        }

        saveUserAccountsToFile();
        System.out.println("Account updated successfully!");
    }
    
    private void saveUserAccountsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users.values()) {
                writer.write(user.email + "\n" + user.password + "\n" + user.userType + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to a file: " + e.getMessage());
        }
    }

        public static void main(String[] args) {
        Login login = new Login("ENTERFILENAME.txt"); // ENTER TXT FILE NAME HERE
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1 to login, 2 to register, 3 to delete an account, or 4 to edit an account:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        try {
            if (choice == 1) {
                login.loginUser();
            } else if (choice == 2) {
                login.registerUser();
            } else if (choice == 3) {
                System.out.println("Enter the email of the account you wish to delete:");
                String email = scanner.nextLine();
                login.deleteAccount(email);
            } else if (choice == 4) {
                System.out.println("Enter the email of the account you wish to edit:");
                String email = scanner.nextLine();
                login.editAccount(email);
            } else {
                System.out.println("Invalid choice!");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
