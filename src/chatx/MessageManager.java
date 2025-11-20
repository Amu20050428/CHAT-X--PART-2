package chatx;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileWriter;
import java.io.FileReader;

public class MessageManager {

    private final String[] messages;
    private final String[] recipients;
    private final String[] messageIDs;
    private final String[] messageHashes;
    private int count = 0;

    public MessageManager(int size) {
        messages = new String[size];
        recipients = new String[size];
        messageIDs = new String[size];
        messageHashes = new String[size];
    }

    // ---------------------------------------
    // 1. Add a message
    // ---------------------------------------
    public void addMessage(String msg, String recipient) {

        messages[count] = msg;
        recipients[count] = recipient;

        messageIDs[count] = createMessageID(msg, count);
        messageHashes[count] = createMessageHash(msg, count);

        count++;
    }

    private String createMessageID(String msg, int index) {
        String part = msg.length() >= 3 ? msg.substring(0, 3) : msg;
        return part.toUpperCase() + "-" + (index + 1);
    }

    private String createMessageHash(String msg, int index) {
        int hashValue = msg.hashCode() + index;
        return "HASH" + Math.abs(hashValue);
    }

    // ---------------------------------------
    // 2. Get longest message
    // ---------------------------------------
    public String getLongestMessage() {
        if (count == 0) return "No messages available.";

        String longest = messages[0];
        for (int i = 1; i < count; i++) {
            if (messages[i].length() > longest.length()) {
                longest = messages[i];
            }
        }
        return longest;
    }

    // ---------------------------------------
    // 3. Search by ID
    // ---------------------------------------
    public String searchByID(String id) {

        for (int i = 0; i < count; i++) {
            if (messageIDs[i].equals(id)) {

                return "Message Found:\n" +
                        "ID: " + messageIDs[i] + "\n" +
                        "Recipient: " + recipients[i] + "\n" +
                        "Message: " + messages[i] + "\n" +
                        "Hash: " + messageHashes[i];
            }
        }

        return "Message ID not found.";
    }

    // ---------------------------------------
    // 4. Search by recipient
    // ---------------------------------------
    public String searchByRecipient(String number) {

        for (int i = 0; i < count; i++) {
            if (recipients[i].equals(number)) {

                return "Recipient Found:\n" +
                        "Message: " + messages[i] + "\n" +
                        "ID: " + messageIDs[i] + "\n" +
                        "Hash: " + messageHashes[i];
            }
        }

        return "Recipient not found.";
    }

    // ---------------------------------------
    // 5. Delete by hash
    // ---------------------------------------
    public boolean deleteByHash(String hash) {

        for (int i = 0; i < count; i++) {
            if (messageHashes[i].equals(hash)) {

                // shift left
                for (int j = i; j < count - 1; j++) {
                    messages[j] = messages[j + 1];
                    recipients[j] = recipients[j + 1];
                    messageIDs[j] = messageIDs[j + 1];
                    messageHashes[j] = messageHashes[j + 1];
                }

                count--;
                return true;
            }
        }
        return false;
    }

    // ---------------------------------------
    // 6. Get message-by-ID
    // ---------------------------------------
    public String getMessageById(String id) {

        for (int i = 0; i < count; i++) {
            if (messageIDs[i].equals(id)) {
                return messages[i];
            }
        }

        return "Message ID not found.";
    }

    // ---------------------------------------
    // 7. Display Report
    // ---------------------------------------
    public String displayReport() {

        if (count == 0) return "No messages to display.";

        StringBuilder report = new StringBuilder("=== ChatX Message Report ===\n");

        for (int i = 0; i < count; i++) {

            report.append("\nMessage #").append(i + 1)
                    .append("\nID: ").append(messageIDs[i])
                    .append("\nRecipient: ").append(recipients[i])
                    .append("\nMessage: ").append(messages[i])
                    .append("\nHash: ").append(messageHashes[i])
                    .append("\n---------------------------");
        }

        return report.toString();
    }

    // ---------------------------------------
    // 8. Save to JSON
    // ---------------------------------------
    public void saveMessagesToJSON(String fileName) {
        try {
            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < count; i++) {
                JSONObject obj = new JSONObject();
                obj.put("message", messages[i]);
                obj.put("recipient", recipients[i]);
                obj.put("id", messageIDs[i]);
                obj.put("hash", messageHashes[i]);

                jsonArray.add(obj);
            }

            FileWriter file = new FileWriter(fileName);
            file.write(jsonArray.toJSONString());
            file.close();

            System.out.println("JSON file saved successfully!");

        } catch (Exception e) {
            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }

    // ---------------------------------------
    // 9. Load from JSON
    // ---------------------------------------
    public void loadMessagesFromJSON(String fileName) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(fileName));

            count = 0;

            for (Object obj : jsonArray) {
                JSONObject jsonObj = (JSONObject) obj;

                messages[count] = (String) jsonObj.get("message");
                recipients[count] = (String) jsonObj.get("recipient");
                messageIDs[count] = (String) jsonObj.get("id");
                messageHashes[count] = (String) jsonObj.get("hash");

                count++;
            }

            System.out.println("JSON file loaded successfully!");

        } catch (Exception e) {
            System.out.println("Error reading JSON: " + e.getMessage());
        }
    }
}
