import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import users.*;
import client.*;

public class OtherTests {
    private Login login;

    @BeforeEach
    public void setUp() {
        login = new Login("test_account_list.txt");
    }

    @Test
    public void testRegistration() {
        Scanner scanner = new Scanner("test@example.com\npassword123\nCustomer\n");
        assertTrue(login.registerUser(scanner));
        assertNotNull(login.getUsers().get("test@example.com"));
    }

    @Test
    public void testLogin() {
        Scanner scanner = new Scanner("test@example.com\npassword123\n");
        String email = login.loginUser(scanner);
        assertEquals("test@example.com", email);
    }

    @Test
    public void testEditAccount() {
        User user = new User("test@example.com", "password123", Roles.Customer);
        Scanner scanner = new Scanner("newtest@example.com\nnewpassword123\n");
        login.editAccount(scanner, user.getEmail());
        assertNull(login.getUsers().get("test@example.com"));
        assertNotNull(login.getUsers().get("newtest@example.com"));
        assertEquals("newpassword123", login.getUsers().get("newtest@example.com").getPassword());
    }

    @Test
    public void testDeleteAccount() {
        User user = new User("test@example.com", "password123", Roles.Customer);
        login.getUsers().put(user.getEmail(), user);
        login.deleteAccount(user.getEmail());
        assertNull(login.getUsers().get("test@example.com"));
    }

    @Test
    public void testMessagingBetweenSellers() {
        Seller seller1 = new Seller("seller1@example.com", "password123");
        Seller seller2 = new Seller("seller2@example.com", "password456");
        
        Message message = new Message(seller1, seller2, "Hello!", true, true);
        assertNotNull(message);
        assertEquals(seller1, message.getSeller());
        assertEquals(seller2, message.getCustomer());
        assertEquals("Hello!", message.getMessage());
        assertTrue(message.getSellerVis());
        assertTrue(message.getCustomerVis());
    }
}
