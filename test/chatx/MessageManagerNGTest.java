/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package chatx;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;

/**
 * TestNG tests for MessageManager
 */
public class MessageManagerNGTest {

    @Test
    public void testAddMessageStoresCorrectly() {
        MessageManager mm = new MessageManager(5);

        mm.addMessage("Hello", "+27712345678");

        Assert.assertEquals(mm.getMessageById("HEL-1"), "Hello");
    }

    @Test
    public void testCreateMessageID() {
        MessageManager mm = new MessageManager(5);

        mm.addMessage("Chat", "+27712345678");

        String result = mm.searchByID("CHA-1");

        Assert.assertTrue(result.contains("Message Found"));
    }

    @Test
    public void testCreateMessageHash() {
        MessageManager mm = new MessageManager(5);

        mm.addMessage("Test Hash", "+27712345678");

        String report = mm.displayReport();

        Assert.assertTrue(report.contains("HASH"));
    }

    @Test
    public void testGetLongestMessage() {
        MessageManager mm = new MessageManager(5);

        mm.addMessage("Short", "+27712345678");
        mm.addMessage("This is definitely the longest message", "+27787654321");

        Assert.assertEquals(mm.getLongestMessage(),
                "This is definitely the longest message");
    }

    @Test
    public void testSearchByIDFound() {
        MessageManager mm = new MessageManager(5);

        mm.addMessage("Test", "+27712345678");

        String result = mm.searchByID("TES-1");

        Assert.assertTrue(result.contains("Message Found"));
    }

    @Test
    public void testSearchByIDNotFound() {
        MessageManager mm = new MessageManager(5);

        mm.addMessage("ABC", "+27712345678");

        String result = mm.searchByID("XYZ-1");

        Assert.assertEquals(result, "Message ID not found.");
    }

    @Test
    public void testSearchByRecipientFound() {
        MessageManager mm = new MessageManager(5);

        mm.addMessage("Hello", "+27711111111");

        String result = mm.searchByRecipient("+27711111111");

        Assert.assertTrue(result.contains("Recipient Found"));
    }

    @Test
    public void testSearchByRecipientNotFound() {
        MessageManager mm = new MessageManager(5);

        mm.addMessage("Hello", "+27722222222");

        String result = mm.searchByRecipient("+27799999999");

        Assert.assertEquals(result, "Recipient not found.");
    }

    @Test
    public void testDeleteByHash() {
        MessageManager mm = new MessageManager(5);

        mm.addMessage("Delete Me", "+27712345678");

        String reportBefore = mm.displayReport();
        String hash = reportBefore.split("Hash: ")[1].trim();

        boolean deleted = mm.deleteByHash(hash);

        Assert.assertTrue(deleted);
    }

    @Test
    public void testSaveAndLoadJSON() {
        MessageManager mm = new MessageManager(5);

        mm.addMessage("JSON Test", "+27712345678");

        mm.saveMessagesToJSON("testMessages.json");

        File f = new File("testMessages.json");
        Assert.assertTrue(f.exists());

        MessageManager loaded = new MessageManager(5);
        loaded.loadMessagesFromJSON("testMessages.json");

        String result = loaded.searchByRecipient("+27712345678");

        Assert.assertTrue(result.contains("Recipient Found"));
    }
}