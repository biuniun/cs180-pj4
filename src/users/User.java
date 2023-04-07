package users;

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
    public int getID()
    {
        return id;
    }
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
    public String getUserType()
    {
        return userType;
    }
    public ArrayList<User> getBlockedUsers()
    {
        return blockedUsers;
    }
    
    //Setters
    public void setID(int id)
    {
        this.id = id;
    }
    public void getUsername(String username)
    {
        this.username = username;
    }
    public void getPassword(String password)
    {
        this.password = password;
    }
    public void getUserType(String userType)
    {
        this.userType = userType;
    }
    public void getBlockedUsers(ArrayList<User> blockedUsers)
    {
        this.blockedUsers = blockedUsers;
    }
    
    //Methods
    //Add a user to the blocked users list
    public ArrayList<User> blockUsers(User user)
    {
        blockedUsers.add(user);
        return blockedUsers;
    }
}
