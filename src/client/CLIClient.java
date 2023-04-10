package client;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

import users.User;

public class CLIClient {
    private static final String WELCOME_STRING = "Welcome to IM System of Sharing Economy";
    private static final String SELECT = "Please Select from following Options:\n";
    private static final String LOGIN_MENU = SELECT + "1. Login\n2. Register a New Account\n3. Quit";
    private static final String BYEBYE_STRING = "Bye-Bye!";
    private static final String OPERATION_LIST = SELECT + "1. Account Management\n2. Message\n3. Logout";
    private static final String ACCOUNT_MENU = SELECT + "1. Edit Account\n2. Delete Account\n3. View Account Statistic\n4. Block Users";

    private static final String ACCOUNT_INFO_PATH = "file" + File.separator + "account_list.txt";

    public static void run() {
        System.out.println(WELCOME_STRING);
        Scanner scanner = new Scanner(System.in);
        boolean close = false;
        Login login = new Login(ACCOUNT_INFO_PATH);
        Map<String, User> users = login.getUsers();
        String uid = null;

        while (!close) {
            // login and registration
            while (uid == null)
                uid = login(scanner, login);
            if (uid.equals("|"))
                return;
            boolean sessionAlive = true;
            while (sessionAlive) {
                sessionAlive = operations(scanner, users.get(uid));
            }
                
        } 

        scanner.close();
    }

    private static boolean operations(Scanner scanner, User user) {
        System.out.println(OPERATION_LIST);
        int input = inToOpt(scanner, 1, 3);
        if (input == 1) {
            return userOperations(scanner, user);
        }

        return false;
    }

    private static boolean userOperations(Scanner scanner, User user) {
        System.out.println(ACCOUNT_MENU);
            int input = inToOpt(scanner, 1, 4);
            if (input == 1)
                new Login(ACCOUNT_INFO_PATH).editAccount(scanner, user.getEmail());
            if (input == 2) {
                new Login(ACCOUNT_INFO_PATH).deleteAccount(user.getEmail());
                return false;
            }
            if (input == 3)
                user.getDashboard().toString();
            if (input == 4)
                user.blockUsers(user);
            return true;
    }

    private static String login(Scanner scanner, Login login) {
        String uid = null;
        System.out.println(LOGIN_MENU);
        int option = inToOpt(scanner, 1, 3);
        if (option == 3) {
            System.out.println(BYEBYE_STRING);
            return "|";
        }
        if (option == 1)
            uid = login.loginUser(scanner);
        if (option == 2)
            login.registerUser(scanner);
        return uid;
    }

    private static int inToOpt(Scanner scanner, int start, int end) {
        int rtn = -1;
        for (;;) {
            try {
                rtn = Integer.parseInt(scanner.nextLine());
                if (rtn < start && rtn > end && rtn != -1)
                    throw new NumberFormatException();
                return rtn;
            } catch (NumberFormatException e) {
                System.out.printf("Please input a valid integer between %d and %d\n, or -1 to quit the application",
                        start, end);
            }
        }

    }
}
