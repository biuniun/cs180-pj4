package users;

import java.io.File;
import java.util.ArrayList;

public class User {
    private static final String MESS_PATH = "file" + File.separator + "message";
    private String email;
    private String password;
    private Roles userType;
    private Dashboard dashboard;
    private ArrayList<User> blockedUsers;
    private ArrayList<User> reciever;
    private ArrayList<Message> history;

    public User(String email, String password, Roles userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.dashboard = new Dashboard();
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

    public ArrayList<User> getBlockedUsers() {
        return blockedUsers;
    }

    public Dashboard getDashboard() {
        return dashboard;
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

    public void setBlockedUsers(ArrayList<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    // Methods
    // Add a user to the blocked users list
    public ArrayList<User> blockUsers(User user) {
        blockedUsers.add(user);
        return blockedUsers;
    }

}
