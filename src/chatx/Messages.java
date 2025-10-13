package chatx;

import org.json.JSONObject;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Messages {
    // Variables
    private static int totalMessagesSent = 0;

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;

    // Constructor
    public Messages(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
    }

    // Generate message ID
    private String generateMessageID() {
        Random rand = new Random();
        long randomNum = 1000000000L + (long) (rand.nextDouble() * 8999999999L);
        return String.valueOf(randomNum);
    }

    // Check message ID validity
    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    // Validate cellphone number format
    public String checkRecipientCell() {
        if (recipient.startsWith("+27") && recipient.length() == 12) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Validate message length
    public String validateMessageLength() {
        if (messageText.length() > 250) {
            int diff = messageText.length() - 250;
            return "Message exceeds 250 characters by " + diff + ", please reduce size.";
        } else if (messageText.isEmpty()) {
            return "Message cannot be empty.";
        } else {
            return "Message ready to send.";
        }
    }

    // Create message hash
    public void createMessageHash() {
        this.messageHash = Integer.toHexString(messageText.hashCode());
    }

    // Choose message action
    public String sendMessage(int action) {
        switch (action) {
            case 1:
                totalMessagesSent++;
                return "Message sent successfully.";
            case 2:
                return "Message disregarded.";
            case 3:
                saveMessageToJSON();
                return "Message saved successfully.";
            default:
                return "Invalid option.";
        }
    }

    // Save message to JSON
    public void saveMessageToJSON() {
        JSONObject messageObj = new JSONObject();
        messageObj.put("messageID", messageID);
        messageObj.put("recipient", recipient);
        messageObj.put("messageText", messageText);
        messageObj.put("messageHash", messageHash);

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(messageObj.toString() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving message: " + e.getMessage());
        }
    }

    // Return total messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
}
