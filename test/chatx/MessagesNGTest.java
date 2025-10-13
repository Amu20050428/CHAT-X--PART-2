/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package chatx;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MessagesNGTest {

    private Messages validMessage;
    private Messages longMessage;
    private Messages emptyMessage;
    private Messages invalidRecipientMessage;

    @BeforeMethod
    public void setUp() {
        validMessage = new Messages("+27831234567", "Hello Tshiamo!");
        longMessage = new Messages("+27831234567", "A".repeat(260));
        emptyMessage = new Messages("+27831234567", "");
        invalidRecipientMessage = new Messages("0831234567", "Hi!");
    }

    @Test
    public void testMessageIDIsValid() {
        Assert.assertTrue(validMessage.checkMessageID(),
                "Message ID should be exactly 10 characters long");
    }

    @Test
    public void testRecipientCellValid() {
        Assert.assertTrue(validMessage.checkRecipientCell(),
                "Recipient with +27 format should be valid");
    }

    @Test
    public void testRecipientCellInvalid() {
        Assert.assertFalse(invalidRecipientMessage.checkRecipientCell(),
                "Recipient without +27 format should be invalid");
    }

    @Test
    public void testMessageLengthTooLong() {
        String result = longMessage.validateMessageLength();
        Assert.assertTrue(result.contains("too long"),
                "Should detect message longer than 250 characters");
    }

    @Test
    public void testMessageLengthEmpty() {
        String result = emptyMessage.validateMessageLength();
        Assert.assertEquals(result, "Message cannot be empty.");
    }

    @Test
    public void testMessageLengthValid() {
        String result = validMessage.validateMessageLength();
        Assert.assertEquals(result, "Message length is valid.");
    }

    @Test
    public void testCreateMessageHash() {
        validMessage.createMessageHash();
        Assert.assertNotNull(validMessage, "Message hash should not be null after creation");
    }

    @Test
    public void testSendMessageSuccess() {
        String result = validMessage.sendMessage(1);
        Assert.assertEquals(result, "Message sent successfully.");
    }

    @Test
    public void testSendMessageDisregarded() {
        String result = validMessage.sendMessage(2);
        Assert.assertEquals(result, " The Message is disregarded.");
    }

    @Test
    public void testSendMessageSaved() {
        String result = validMessage.sendMessage(3);
        Assert.assertEquals(result, " The  Message is saved successfully.");
    }

    @Test
    public void testSendMessageInvalidOption() {
        String result = validMessage.sendMessage(5);
        Assert.assertEquals(result, "Invalid option.");
    }

    @Test
    public void testTotalMessagesIncrement() {
        int before = Messages.returnTotalMessages();
        validMessage.sendMessage(1);
        int after = Messages.returnTotalMessages();
        Assert.assertEquals(after, before + 1, "Total messages should increment by 1");
    }
}
