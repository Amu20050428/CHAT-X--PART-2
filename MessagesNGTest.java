package chatx;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MessagesNGTest {

    private Messages message1;
    private Messages message2;
    private Messages invalidRecipient;
    private Messages longMessage;

    @BeforeMethod
    public void setUp() {
        message1 = new Messages("+27718693002", "Hi Mike, can you join us for dinner tonight");
        message2 = new Messages("08575975889", "Hi Keegan, did you receive the payment?");
        invalidRecipient = new Messages("0834567890", "Testing invalid number");
        longMessage = new Messages("+27718693002", "A".repeat(270));
    }

    @Test
    public void testValidMessageLength() {
        String result = message1.validateMessageLength();
        Assert.assertEquals(result, "Message ready to send.");
    }

    @Test
    public void testMessageTooLong() {
        String result = longMessage.validateMessageLength();
        Assert.assertTrue(result.startsWith("Message exceeds 250 characters"),
                "Should return correct failure message for long text");
    }

    @Test
    public void testValidRecipientNumber() {
        String result = message1.checkRecipientCell();
        Assert.assertEquals(result, "Cell phone number successfully captured.");
    }

    @Test
    public void testInvalidRecipientNumber() {
        String result = invalidRecipient.checkRecipientCell();
        Assert.assertEquals(result,
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    public void testSendMessage() {
        String result = message1.sendMessage(1);
        Assert.assertEquals(result, "Message sent successfully.");
    }

    @Test
    public void testDiscardMessage() {
        String result = message2.sendMessage(2);
        Assert.assertEquals(result, "Message disregarded.");
    }

    @Test
    public void testTotalMessagesSentIncrement() {
        int before = Messages.returnTotalMessages();
        message1.sendMessage(1);
        int after = Messages.returnTotalMessages();
        Assert.assertEquals(after, before + 1);
    }
}
