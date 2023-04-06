import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Login {
    private Map<String, String> users;
    private String filePath;

    public Login(String filePath) {
        this.filePath = filePath;
        users = new HashMap<>();
        loadUsernamesAndPasswords();
    }

    private void loadUsernamesAndPasswords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String username = line.trim();
                String password = reader.readLine().trim();
                users.put(username, password);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public void loginUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your username:");
        String username = scanner.nextLine();

        if (!users.containsKey(username)) {
            throw new IllegalArgumentException("Username not found.");
        }

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        if (!password.equals(users.get(username))) {
            throw new IllegalArgumentException("Incorrect password.");
        }

        System.out.println("Login successful!");
    }

    public void registerUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a new username:");
        String newUsername = scanner.nextLine();

        if (users.containsKey(newUsername)) {
            System.out.println("Username already exists. Please choose a different one.");
            return;
        }

        System.out.println("Enter a new password:");
        String newPassword = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("\n" + newUsername + "\n" + newPassword); // Add a newline character before writing the new username
            users.put(newUsername, newPassword);
            System.out.println("Registration successful!");
        } catch (IOException e) {
            System.err.println("Error writing to a file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Login login = new Login("ENTERFILESOURCENAMEHERE"); // ENTER FILENAME
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1 to login or 2 to register:");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        try {
            if (choice == 1) {
                login.loginUser();
            } else if (choice == 2) {
                login.registerUser();
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
