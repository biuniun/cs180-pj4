package client;

import java.io.File;
import java.util.Scanner;

public class CLIClient {
    private static final String WELCOME_STRING = "Welcome to IM System of Sharing Economy";
    private static final String LOGIN_MENU = "Please Select from following Options:\n1. Login\n2. Register a New Account\n3. Quit";
    private static final String BYEBYE_STRING = "Bye-Bye!";

    private static final String ACCOUNT_INFO_PATH = "file" + File.separator + "account_list.txt";

    public static void run() {
        System.out.println(WELCOME_STRING);
        Scanner scanner = new Scanner(System.in);

        // login and registrtaion
        String uid = null;
        while (uid == null) 
            uid = login(scanner);
        if (uid.equals("|"))
            return;

        // Operations: Users and Conversational.

        // Receieve Messages

        // Send Messages


        


        scanner.close();
    }

    private static String login(Scanner scanner) {
        String uid = null;
        System.out.println(LOGIN_MENU);
        int option = inToOpt(scanner, 1, 3);
        if (option == 3) {
            System.out.println(BYEBYE_STRING);
            return "|";
        }
        Login login = new Login(ACCOUNT_INFO_PATH);
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
