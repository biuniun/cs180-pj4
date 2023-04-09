package users;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author matth
 */
public class Message 
{
    private User sender;
    private User reciever;
    private String content;
    private String senderView;
    private String recieverView;
    private String timestamp;
    
    public Message(User sender, User reciever, String content, String senderView, String recieverView, String timestamp)
    {
        this.sender = sender;
        this.reciever = reciever;
        this.content = content;
        this.senderView = senderView;
        this.recieverView = recieverView;
        this.timestamp = timestamp;
    }
    
    //Getters
    public User getSender()
    {
        return sender;
    }
    public User getReciever()
    {
        return reciever;
    }
    public String getContent()
    {
        return content;
    }
    public String getSenderView()
    {
        return senderView;
    }
    public String getRecieverView()
    {
        return recieverView;
    }
    public String getTimestamp()
    {
        return timestamp;
    }
    
    //Setters
    public void setSender(User sender)
    {
        this.sender = sender;
    }
    public void setReciever(User reciever)
    {
        this.reciever = reciever;
    }
    
    //Methods
    /*
    *works under the assumpion that the message is sent in a "[messageSender];[messageReciever];[Content];sYes;rYes;[timeStamp]" format
    *deletes the old content of the message and replaces it with the new message
    *the timestamp isn't updated (might need to change)
    *both sender and reciever can see the change
    */
    public void editMessage(String newContent, File fileName)
    {
        try {
            FileReader reader = new FileReader (fileName);
            BufferedReader bf = new BufferedReader(reader);
            File tempFile = new File(fileName.getAbsolutePath() + ".tmp");
            FileWriter writer = new FileWriter(tempFile);
            
            String line;
            while ((line = bf.readLine()) != null) {
                if (line.contains(this.content)) {
                    String[] parts = line.split(";"); //split the contents of the line into an array
                    if(parts[2].equals(this.content))
                    {
                        parts[2] = newContent; //changes the old content to the new content
                        line = String.join(";", parts); //joins the modified array into a string
                    }
                    writer.write(line);
                }
                bf.close();
                writer.close();
                fileName.delete(); //deletes the origional file
                tempFile.renameTo(fileName); //replaces the origional file with the temp file that contains the modified content
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
    *works under the assumpion that the message is sent in a "[messageSender];[messageReciever];[Content];1;1;[timeStamp]" format
    *changes the viewability of the message so that the sender can't see the message
    *it does this by changing the sYes value to sNo  
    *the timestamp isn't updated (might need to change)
    *both sender and reciever can see the change
    */
    public void deleteMessage(File fileName) {
        try {
            FileReader reader = new FileReader (fileName);
            BufferedReader bf = new BufferedReader(reader);
            File tempFile = new File(fileName.getAbsolutePath() + ".tmp");
            FileWriter writer = new FileWriter(tempFile);
            
            String line;
            while ((line = bf.readLine()) != null) {
                if (line.contains(this.content)) {
                    String[] parts = line.split(";"); //split the contents of the line into an array
                    if(parts[3].equals(this.getSenderView()))
                    {
                        parts[3] = "sNo"; //changes the yes value to no
                        line = String.join(";", parts); //joins the modified array into a string
                    }
                    writer.write(line);
                }
                bf.close();
                writer.close();
                fileName.delete(); //deletes the origional file
                tempFile.renameTo(fileName); //replaces the origional file with the temp file that contains the modified content
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
