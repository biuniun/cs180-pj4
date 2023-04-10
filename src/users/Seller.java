package users;

import java.util.ArrayList;

public class Seller extends User {    
    public Seller(String email, String password)
    {
        super(email, password, Roles.Seller);
    }
}
