package users;

import java.util.ArrayList;

public class User 
{
    private int id;
    private String username;
    private String password;
    private String userType;
    private ArrayList<User> blockedUsers;
    
    public User(int id, String username, String password, String userType, ArrayList<User> blockedUsers)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.blockedUsers = blockedUsers;
    }
    
    //Getters
    public int getID() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getUserType() {
        return userType;
    }
    public ArrayList<User> getBlockedUsers() {
        return blockedUsers;
    }
    
    //Setters
    public void setID(int id) {
        this.id = id;
    }
    public void getUsername(String username) {
        this.username = username;
    }
    public void getPassword(String password) {
        this.password = password;
    }
    public void getUserType(String userType) {
        this.userType = userType;
    }
    public void getBlockedUsers(ArrayList<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }
    
    //Methods
    //Add a user to the blocked users list
    public ArrayList<User> blockUsers(User user) {
        blockedUsers.add(user);
        return blockedUsers;
    }
    
    /*
    *sendMessage has to be in this form "[seller].sendMessage([buyer], [message content], [file name]"
    *Operates under the assumption that there's a constraint in main that prevents the same type of user from messaging each other
    */
    public void sendMessage(User reciever, String content, File fileName) {
        //Get the sender's and reciever's name
        String messageSender = this.getUsername();
        String messageReciever = reciever.getUsername();
        String timeStamp = LocalDateTime.now().toString(); //gets timestamp at this point and converts it to string
        
        //ASSUMES FILE EXISTS writes the message to the end of the existing file
        FileWriter writer;
        try {
            writer = new FileWriter(fileName, true);
            //Writes a message with all of the content seperated by semicolons (;).
            //sYes represents the sender is allowed to view the message
            //rYes represents the reciever is allowed to view the message
            writer.write(messageSender + ";" + messageReciever + ";" + content + ";sYes;rYes;" + timeStamp + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
