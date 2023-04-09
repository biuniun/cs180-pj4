package users;

import java.util.ArrayList;

public class Customer extends User
{    
    public Customer(int id, String username, String password, String userType, ArrayList<User> blockedUsers)
    {
        super(id, username, password, userType, blockedUsers);
    }
}
