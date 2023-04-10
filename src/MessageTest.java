package users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageTest {
    private Seller seller1;
    private Seller seller2;
    private Message message;

    @BeforeEach
    public void setUp() {
        seller1 = new Seller("seller1@example.com", "password123");
        seller2 = new Seller("seller2@example.com", "password123");
        message = new Message(seller1, seller2, "Hello!", true, true);
    }

    @Test
    public void testMessageCreation() {
        assertNotNull(message);
        assertEquals(seller1, message.getSeller());
        assertEquals(seller2, message.getCustomer());
        assertEquals("Hello!", message.getMessage());
        assertEquals(true, message.getSellerVis());
        assertEquals(true, message.getCustomerVis());
    }

    @Test
    public void testSetMessage() {
        message.setMessage("New message content");
        assertEquals("New message content", message.getMessage());
    }

    @Test
    public void testSetSellerVis() {
        message.setSellerVis(false);
        assertEquals(false, message.getSellerVis());
    }

    @Test
    public void testSetCustomerVis() {
        message.setCustomerVis(false);
        assertEquals(false, message.getCustomerVis());
    }
}
