import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UniversalTest {
    @Test
    public void testCase() {
        String[] a = { "12", "34" };
        String b = "12;34";
        boolean check = false;
        if (b.split(";")[0].equals(a[0]) && b.split(";")[1].equals(a[1]))
            check = true;
        assertTrue(check);
    }
}  
