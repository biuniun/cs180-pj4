import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MarketplaceTest {

    private Marketplace marketplace;

    @Before
    public void setUp() {
        marketplace = new Marketplace();
    }

    @Test
    public void testRegisterUser() {
        User user = new User("john@example.com", "password123", "John Doe", "customer");
        marketplace.registerUser(user);
        User registeredUser = marketplace.getUser("john@example.com");
        assertNotNull(registeredUser);
        assertEquals("John Doe", registeredUser.getName());
    }

    @Test
    public void testCreateStore() {
        Seller seller = new Seller("jane@example.com", "password123", "Jane Doe");
        marketplace.registerUser(seller);
        Store store = marketplace.createStore(seller, "Jane's Store");
        assertNotNull(store);
        assertEquals("Jane's Store", store.getName());
    }

    @Test
    public void testSendMessage() {
        User user1 = new User("john@example.com", "password123", "John Doe", "customer");
        User user2 = new User("jane@example.com", "password123", "Jane Doe", "seller");
        marketplace.registerUser(user1);
        marketplace.registerUser(user2);
        marketplace.sendMessage(user1, user2, "Hello, Jane!");
        Message message = marketplace.getMessage(user1, user2);
        assertNotNull(message);
        assertEquals("Hello, Jane!", message.getContent());
    }

}
