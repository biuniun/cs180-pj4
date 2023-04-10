package client;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import users.Customer;
import users.Message;
import users.NotSCException;
import users.Roles;
import users.Seller;
import users.Store;
import users.User;

public class CLIClient {
    private static final String WELCOME_STRING = "Welcome to IM System of Sharing Economy";
    private static final String SELECT = "Please Select from following Options:\n";
    private static final String LOGIN_MENU = SELECT + "1. Login\n2. Register a New Account\n3. Quit";
    private static final String BYEBYE_STRING = "Bye-Bye!";
    private static final String OPERATION_LIST = SELECT + "1. Account Management\n2. Message\n3. Logout";
    private static final String ACCOUNT_MENU = SELECT
            + "1. Edit Account\n2. Delete Account\n3. View Account Statistic\n4. Block Users";

    private static final String ACCOUNT_INFO_PATH = "file" + File.separator + "account_list.txt";

    private static Login login = new Login(ACCOUNT_INFO_PATH);
    private static Map<String, User> usermap = login.getUsers();

    public static void run() {
        System.out.println(WELCOME_STRING);
        Scanner scanner = new Scanner(System.in);
        boolean close = false;

        String uid = null;
        User sessionUser;

        while (!close) {
            // login and registration
            while (uid == null)
                uid = login(scanner, login);
            if (uid.equals("|"))
                return;
            boolean sessionAlive = true;
            while (sessionAlive) {
                if (usermap.get(uid).getUserType().equals(Roles.Customer))
                    sessionUser = new Customer(uid);
                else
                    sessionUser = new Seller(uid);
                sessionAlive = operations(scanner, sessionUser);
            }
            uid = null;
        }
        login.saveUserAccountsToFile();
        scanner.close();
    }

    private static boolean operations(Scanner scanner, User user) {
        String option = "";
        int sell = 0;
        if (user.getUserType().equals(Roles.Seller)) {
            option = "\n4. Add a store";
            sell = 1;
        }
        System.out.println(OPERATION_LIST + option);
        int input = inToOpt(scanner, 1, 3 + sell);
        if (input == 1) {
            return userOperations(scanner, user);
        }
        if (input == 2) {
            System.out.println("Loading Message\nPlease Check Following List of Conversation");
            user.loadMessage();
            int i = 0;
            User[] receivers = user.getReceiver().toArray(User[]::new);
            for (User auser : receivers)
                System.out.println(++i + ". " + auser);
            System.out.println(++i + ". Create a new Conversation");
            String dest = "";
            int process = inToOpt(scanner, 1, i);
            if (process == i) {
                if (sell == 0) {
                    System.out.println("Loading Stores");
                    Map<String, String> map = Store.getStores();
                    int m = 0;
                    System.out.println(SELECT);
                    String[] sellers = map.keySet().toArray(String[]::new);
                    for (String k : sellers) {
                        System.out.println((m + 1) + ". Store: " + k + "Seller: " + map.get(k));
                    }

                    dest = map.get(sellers[inToOpt(scanner, 1, m) - 1]);
                }
                if (sell == 1) {
                    System.out.println("Printing Custumers:");
                    usermap.values().stream().filter(s -> s.getUserType().equals(Roles.Customer))
                            .forEach(System.out::println);

                    System.out.println("Please provide the destination email.");
                    while (dest.isBlank()) {
                        dest = scanner.nextLine();
                        if (!usermap.values().contains(new Customer(dest))) {
                            System.err.println("Email not found");
                            dest = null;
                        }
                    }
                }

                if (checkAccount(dest))
                    try {
                        return sendMassage(scanner, user, usermap.get(dest));
                    } catch (NotSCException e) {
                        System.out.println(e);
                        return true;
                    }
                else {
                    System.err.println("User not found");
                    return true;
                }
            } else {
                i = process - 1;
                System.out.println("Loading Conversation with " + receivers[i]);
                ArrayList<Message> con = user.getCon(receivers[i]);
                for (int j = 1; j < con.size() + 1; j++) {
                    System.out.println(j + ". " + con.get(j - 1));
                }
                System.out
                        .println("Select the record to modify or delete the message or press 0 to send a new message.");
                int messageOp = inToOpt(scanner, 0, con.size());
                if (messageOp == 0)
                    try {
                        return sendMassage(scanner, user, receivers[i]);
                    } catch (NotSCException e) {
                        System.out.println(e);
                        return true;
                    }
                else {
                    messageOp--;
                    System.out.println(SELECT + "1. Edit\n2. Delete");
                    int edOp = inToOpt(scanner, 1, 2);
                    if (edOp == 2) {
                        if (user.getUserType().equals(Roles.Seller))
                            con.get(messageOp).setSellerVis(false);
                        else
                            con.get(messageOp).setCustomerVis(false);

                        con.get(messageOp).writeToRecord();
                    }
                    if (edOp == 1) {
                        System.out.println("Please input your message");
                        con.get(messageOp).setMessage(scanner.nextLine());
                        con.get(messageOp).writeToRecord();
                    }
                }
            }
            return Message.tidy();
        }
        if (input == 4 && sell == 1) {
            System.out.println("Please input your store name:");
            new Store(new Seller(user.getEmail()), scanner.nextLine());
            return true;
        }

        return false;
    }

    private static boolean checkAccount(String address) {
        return usermap.containsKey(address);
    }

    private static boolean sendMassage(Scanner scanner, User sender, User dest) throws NotSCException {
        Seller seller = null;
        Customer customer = null;
        System.out.println("Please input the message.");
        if (sender.getUserType().equals(dest.getUserType())) {
            throw new NotSCException(
                    "Error: Do not send to " + sender.getUserType().name() + " as a " + sender.getUserType().name());
        }
        if (dest.getBlockedUsers().contains(sender)) {
            System.out.println("You been blocked by the user.");
            return true;
        }
        if (sender.getUserType().equals(Roles.Seller)) {
            seller = new Seller(sender.getEmail());
            customer = new Customer(dest.getEmail());
        } else {
            seller = new Seller(dest.getEmail());
            customer = new Customer(sender.getEmail());
        }

        new Message(seller, customer, scanner.nextLine(), true, true, sender.getUserType().equals(Roles.Seller))
                .writeToRecord();

        Message.tidy();

        return true;
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
            ;
        if (input == 4) {
            System.out.println("Provide the email address of the user you would like to block.");
            String dest = scanner.nextLine();
            if (!usermap.values().contains(new Customer(dest))) {
                System.err.println("Email not found");
                dest = null;
            }

            user.blockUsers(new User(dest, null));
        }
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
                if (rtn < start && rtn > end)
                    throw new NumberFormatException();
                return rtn;
            } catch (NumberFormatException e) {
                System.out.printf("Please input a valid integer between %d and %d\n",
                        start, end);
            }
        }

    }
}
