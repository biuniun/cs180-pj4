package users;

import java.util.ArrayList;

public class Seller extends User {    
    public Seller(int id, String username, String password, String userType, ArrayList<User> blockedUsers)
    {
        super(id, username, password, userType, blockedUsers);
    }
}
