import java.util.ArrayList;

/**
 * Seller
 * 
 * @author Matthew Rops mjrops
 * @version 04-10-2023
 */

public class Seller extends User {
    public Seller(String email) {
        super(email, Roles.Seller);
    }

    @Override
    public ArrayList<Message> getCon(User user) {
        return new ArrayList<>(super.getHistory().stream()
                .filter(m -> m.getCustomer().equals(user))
                .filter(m -> m.getSeller().equals(this))
                .filter(m -> m.isSellerVis()).toList());
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

    @Override
    public String toString() {
        return this.getEmail();
    }
}
