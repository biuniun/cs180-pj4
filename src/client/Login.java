package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

public class Login {
    private Map<String, String> users;
    private String filePath;

    public Login(String filePath) {
        this.filePath = filePath;
        users = new HashMap<>();
        loadUsernamesAndPasswords();
    }

    private boolean loadUsernamesAndPasswords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.lines().forEach(l -> {

                users.put(l.split(";")[0], l.split(";")[1]);
            });

            return true;
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return false;
        }
    }

    public String loginUser(Scanner scanner) {
        System.out.println("Enter your email:");
        String emailAdd = scanner.nextLine();
        users.get(emailAdd);

        if (!users.containsKey(emailAdd)) {
            System.err.println("Username not found.");
            return null;
        }

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        if (!password.equals(users.get(emailAdd))) {
            System.err.println("Incorrect password.");
            return null;
        }

        System.out.println("Login successful!");

        return emailAdd;
    }

    public void registerUser(Scanner scanner) {

        System.out.println("Enter a new username:");
        String newUsername = scanner.nextLine();

        if (users.containsKey(newUsername)) {
            System.out.println("Username already exists. Please choose a different one.");
            return;
        }

        System.out.println("Enter a new password:");
        String newPassword = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("\n" + newUsername + "\n" + newPassword);
            users.put(newUsername, newPassword);
            System.out.println("Registration successful!");
        } catch (IOException e) {
            System.err.println("Error writing to a file: " + e.getMessage());
        }
    }

    @Test
    public void test() {
        Login login = new Login("ENTERFILESOURCENAMEHERE"); // ENTER FILENAME
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1 to login or 2 to register:");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        try {
            if (choice == 1) {
                login.loginUser(scanner);
            } else if (choice == 2) {
                login.registerUser(scanner);
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
