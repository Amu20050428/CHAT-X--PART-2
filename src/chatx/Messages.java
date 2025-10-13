package chatx;

import javax.swing.JOptionPane;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Messages {
    // Variables
    private static int totalMessagesSent = 0;
    private static int messageCounter = 0;

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

    // Method to generate a random message ID
    private String generateMessageID() {
        Random rand = new Random();
        long randomNum = 1000000000L + (long) (rand.nextDouble() * 8999999999L);
        return String.valueOf(randomNum);
    }

    // Check if message ID is valid
    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    // Check if cellphone number is valid
    public boolean checkRecipientCell() {
        if (recipient.startsWith("+27") && recipient.length() == 12) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null,
                "Invalid cellphone number format. Please use +27 followed by 9 digits.");
            return false;
        }
    }

    // Validate the length of the message
    public String validateMessageLength() {
        if (messageText.length() > 250) {
            int diff = messageText.length() - 250;
            return "Message is too long by " + diff + " characters.";
        } else if (messageText.isEmpty()) {
            return "Message cannot be empty.";
        } else {
            return "Message length is valid.";
        }
    }

    // Create a hash for the message
    public void createMessageHash() {
        this.messageHash = Integer.toHexString(messageText.hashCode());
    }

    // Choose what to do with the message
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

    // Save the message to a JSON file
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

    // Display message details
    public void printMessageDetails() {
        JOptionPane.showMessageDialog(null,
            "Message Details:\n" +
            "Message ID: " + messageID + "\n" +
            "Recipient: " + recipient + "\n" +
            "Text: " + messageText + "\n" +
            "Hash: " + messageHash);
    }

    // Return total messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
}
