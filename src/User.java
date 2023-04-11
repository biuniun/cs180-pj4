import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Seller
 * 
 * @author Matthew Rops mjrops
 * @version 04-10-2023
 */

public class User {
    private static final String MESS_PATH = "file" + File.separator + "message.txt";
    private static final String ACCOUNT_INFO_PATH = "file" + File.separator + "account_list.txt";
    private String email;
    private String password;
    private Roles userType;
    private boolean blockListLoaded;
    private boolean notSeeLoaded;
    private ArrayList<String> blockedUsers;
    private ArrayList<User> receivers;
    private ArrayList<Message> history;
    private ArrayList<String> noSee;

    public User(String email, Roles userType) {
        this.email = email;
        this.userType = userType;
        this.receivers = new ArrayList<>();
        this.history = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
        this.noSee = new ArrayList<>();
        this.blockListLoaded = false;
        this.notSeeLoaded = false;
    }

    public User(String email, String password, Roles userType) {
        this(email, userType);
        this.password = password;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Roles getUserType() {
        return userType;
    }

    public ArrayList<String> getBlockedUsers() {
        return blockedUsers;
    }

    public ArrayList<String> getNoSeeList() {
        return noSee;
    }

    public void loadBlockedList() {
        if (blockListLoaded)
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(ACCOUNT_INFO_PATH)))) {
            br.lines().filter(s -> s.split(";")[0].equals(this.getEmail())).forEach(l -> {
                String[] args = l.split(";");
                if (args.length > 3) {
                    String s = args[3];
                    Arrays.asList(s.split(",")).forEach(blockedUsers::add);
                }
            });
            blockListLoaded = true;
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void loadNotSeeList() {
        if (notSeeLoaded)
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(ACCOUNT_INFO_PATH)))) {
            br.lines().filter(s -> s.split(";")[0].equals(this.getEmail())).forEach(l -> {
                String[] args = l.split(";");
                if (args.length > 4) {
                    String s = args[4];
                    Arrays.asList(s.split(",")).forEach(noSee::add);
                }
            });
            notSeeLoaded = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(Roles userType) {
        this.userType = userType;
    }

    public ArrayList<User> getReceiver() {
        return receivers;
    }

    public ArrayList<Message> getHistory() {
        return history;
    }

    // Methods
    // Add a user to the blocked users list
    public void blockUsers(User user) {
        blockedUsers.add(user.getEmail());
    }

    public void canNotSee(User user) {
        noSee.add(user.getEmail());
    }

    // Load Conversation From List
    public void loadMessage() {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(MESS_PATH)))) {
            br.lines().filter(s -> !s.isBlank()).forEach(s -> {
                Message m = new Message(s);
                if (this.equals(m.getCustomer()) && m.getCustomerVis()
                        && !receivers.contains(m.getSeller()) && !noSee.contains(m.getMessage())) {
                    receivers.add(m.getSeller());
                } else if (this.equals(m.getSeller()) && m.getSellerVis()
                        && !receivers.contains(m.getCustomer()) && !noSee.contains(m.getMessage())) {
                    receivers.add(m.getCustomer());
                }
                history.add(m);
                // if (recievers.contains())
            });
        } catch (Exception e) {
            e.printStackTrace();
            // System.err.println("Error Loading History");
        }
    }

    public ArrayList<Message> getCon(User user) {
        return null;
    }

    @Override
    public String toString() {
        return this.getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return user.getEmail().equals(this.getEmail());
    }
}
