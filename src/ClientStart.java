/**
 * Starter Class
 * 
 * @author Nicholas Degen
 * @version 04-10-2023
 */

public class ClientStart {
    public static void main(String[] args) {
        try {
            CLIClient.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
