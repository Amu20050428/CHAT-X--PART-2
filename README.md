I CHANGED MY MAIN CLASS CODE FOR CHATx SO THAT WE CAN IMPLEMENT OUR MENU SYSTEM FOR PART 3 HICH WAS FOR MY MESSAGESMANAGER.JAVA  INCLDING ADDING ANOTHER JSON FILE  AND A NEW TESTING CLASS
HERE IS THE NEW MAIN CLASS
package chatx;

import javax.swing.JOptionPane;

public class ChatX {
    public static void main(String[] args) {
        Login login = new Login();
        Methods user = null;  // user object placeholder
        String choice;

        JOptionPane.showMessageDialog(null, 
            "Welcome to ChatX!\nYour personal chat.", 
            "ChatX", 
            JOptionPane.INFORMATION_MESSAGE);

        do {
            choice = JOptionPane.showInputDialog(
                "==== ChatX Menu ====\n"
              + "1. Register\n"
              + "2. Login\n"
              + "3. Exit\n\n"
              + "Enter your choice:"
            );

            if (choice == null) break; // user closed dialog

            switch (choice) {
                case "1":
                    // Register user
                    String username = JOptionPane.showInputDialog("Enter your username:");
                    String password = JOptionPane.showInputDialog("Enter your password:");
                    String cellPhone = JOptionPane.showInputDialog("Enter your South African cellphone number (+27...):");
                    String firstName = JOptionPane.showInputDialog("Enter your first name:");
                    String lastName = JOptionPane.showInputDialog("Enter your last name:");

                    user = new Methods(username, password, cellPhone, firstName, lastName);
                    JOptionPane.showMessageDialog(null, login.registerUser(user));
                    break;

                case "2":
                    // Login user
                    String uName = JOptionPane.showInputDialog("Enter your username:");
                    String pWord = JOptionPane.showInputDialog("Enter your password:");

                    String result = login.loginUser(uName, pWord);
                    JOptionPane.showMessageDialog(null, result);

                    if (result.startsWith("Welcome")) {
                        // Proceed to message menu
                        JOptionPane.showMessageDialog(null, 
                            "✅ Login successful! Welcome to ChatX Messaging.");

                        int numMessages = Integer.parseInt(
                            JOptionPane.showInputDialog("How many messages would you like to send?")
                        );

                        for (int i = 0; i < numMessages; i++) {
                            String recipient = JOptionPane.showInputDialog("Enter recipient cellphone (+27...):");
                            String text = JOptionPane.showInputDialog("Enter message text:");

                            Messages msg = new Messages(recipient, text);

                            // ✅ FIXED: check recipient format using String response
                            String cellCheck = msg.checkRecipientCell();
                            if (!cellCheck.equals("Cell phone number successfully captured.")) {
                                JOptionPane.showMessageDialog(null, "❌ " + cellCheck);
                                i--; // allow retry
                                continue;
                            }

                            JOptionPane.showMessageDialog(null, msg.validateMessageLength());

                            msg.createMessageHash();
                            msg.saveMessageToJSON();

                            String[] options = {"Send", "Disregard", "Store"};
                            int action = JOptionPane.showOptionDialog(
                                null,
                                "Choose an action for this message:",
                                "Message Options",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                options,
                                options[0]
                            );

                            String status = msg.sendMessage(action + 1);
                            JOptionPane.showMessageDialog(null, status);

                            // ✅ FIXED: show message details safely
                            JOptionPane.showMessageDialog(null,
                                "Message Details:\n" +
                                "Recipient: " + recipient + "\n" +
                                "Text: " + text);
                        }

                        JOptionPane.showMessageDialog(null, 
                            "📨 Total messages sent: " + Messages.returnTotalMessages());
                    }
                    break;

                case "3":
                    JOptionPane.showMessageDialog(null, "👋 Exiting ChatX... Goodbye!");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "⚠️ Invalid choice. Try again.");
            }

        } while (!"3".equals(choice));
    }
}
