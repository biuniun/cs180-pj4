package users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("test@example.com", "password123", Roles.Customer);
    }

    @Test
    public void testUserCreation() {
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(Roles.Customer, user.getUserType());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmail());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testSetUserType() {
        user.setUserType(Roles.Seller);
        assertEquals(Roles.Seller, user.getUserType());
    }
}
