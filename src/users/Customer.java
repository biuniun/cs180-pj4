package users;

import java.util.ArrayList;

public class Customer extends User
{    
    public Customer(int id, String username, String password, ArrayList<User> blockedUsers)
    {
        super(username, password, Roles.Customer);
    }
}
