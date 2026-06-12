/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.letagost10510187.p3_projectchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Message class - Part 3.
 * 
 * @author prais
 */
public class MessageTest {
    
    // Reset arrays before each test to ensure independence
    @BeforeEach
    public void setUp() {
        Message.resetArrays();
    }
    
    // ========== PART 2 TESTS ==========
    
    @Test
    public void testMessageLength_Success() {
        Message msg = new Message();
        String result = msg.checkMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", result);
    }
    
    @Test
    public void testMessageLength_Failure() {
        Message msg = new Message();
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longMessage.append("A");
        }
        String result = msg.checkMessageLength(longMessage.toString());
        assertTrue(result.contains("Message exceeds 250 characters by 10"));
    }
    
    @Test
    public void testRecipientCell_Success() {
        Message msg = new Message();
        String result = msg.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }
    
    @Test
    public void testRecipientCell_Failure_NoPlus() {
        Message msg = new Message();
        String result = msg.checkRecipientCell("08575975889");
        assertEquals("Cell phone number is incorrectly formatted or does not contain " +
                     "an international code. Please correct the number and try again.", result);
    }
    
    @Test
    public void testCreateMessageHash() {
        Message msg = new Message();
        msg.setMessageId("0012345678");
        msg.setMessageText("Hi Mike, can you join us for dinner tonight?");
        msg.setMessageNumber(0);
        
        String hash = msg.createMessageHash();
        assertEquals("00:0:HITONIGHT", hash);
    }
    
    @Test
    public void testMessageIDCreated() {
        Message msg = new Message();
        String id = msg.createMessageId();
        assertNotNull(id);
        assertEquals(10, id.length());
        assertTrue(msg.checkMessageID());
    }
    
    @Test
    public void testSendMessage_Send() {
        Message msg = new Message();
        msg.setMessageId("0012345678");
        msg.setMessageHash("00:0:HITONIGHT");
        msg.setRecipient("+27718693002");
        msg.setMessageText("Hi Mike, can you join us for dinner tonight?");
        msg.setMessageNumber(0);
        
        String result = msg.sendMessage(1); // Send
        assertEquals("Message successfully sent.", result);
    }
    
    @Test
    public void testSendMessage_Disregard() {
        Message msg = new Message();
        String result = msg.sendMessage(2); // Disregard
        assertEquals("Press 0 to delete the message.", result);
    }
    
    @Test
    public void testSendMessage_Store() {
        Message msg = new Message();
        msg.setMessageId("0012345678");
        msg.setMessageHash("00:0:HITONIGHT");
        msg.setRecipient("+27718693002");
        msg.setMessageText("Hi Mike, can you join us for dinner tonight?");
        msg.setMessageNumber(0);
        
        String result = msg.sendMessage(3); // Store
        assertEquals("Message successfully stored.", result);
    }
    
    @Test
    public void testReturnTotalMessagesSent() {
        int total = Message.returnTotalMessagesSent();
        assertTrue(total >= 0);
    }
    
    // ========== PART 3 TESTS ==========
    
    @Test
    public void testSentMessagesArrayPopulated() {
        // Message 1: Sent
        Message msg1 = new Message();
        msg1.setMessageId("1111111111");
        msg1.setMessageHash("11:0:CAKEGET");
        msg1.setRecipient("+27834557896");
        msg1.setMessageText("Did you get the cake?");
        msg1.setMessageNumber(0);
        msg1.sendMessage(1); // Send
        
        // Message 4: Sent
        Message msg4 = new Message();
        msg4.setMessageId("4444444444");
        msg4.setMessageHash("44:1:DINNERTIME");
        msg4.setRecipient("0838884567");
        msg4.setMessageText("It is dinner time!");
        msg4.setMessageNumber(1);
        msg4.sendMessage(1); // Send
        
        String[] sent = Message.getSentMessagesArray();
        assertEquals(2, sent.length);
        assertEquals("Did you get the cake?", sent[0]);
        assertEquals("It is dinner time!", sent[1]);
    }
    
    @Test
    public void testDisplayLongestStoredMessage() {
        Message msg = new Message();
        msg.setMessageId("2222222222");
        msg.setMessageHash("22:0:WHEREON");
        msg.setRecipient("+27838884567");
        msg.setMessageText("Where are you? You are late! I have asked you to be on time.");
        msg.setMessageNumber(0);
        msg.sendMessage(3); // Store
        
        String longest = msg.displayLongestStoredMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }
    
    @Test
    public void testSearchByMessageId() {
        Message msg = new Message();
        msg.setMessageId("0838884567");
        msg.setMessageHash("08:0:ITDINNER");
        msg.setRecipient("0838884567");
        msg.setMessageText("It is dinner time!");
        msg.setMessageNumber(0);
        msg.sendMessage(1); // Send
        
        String result = msg.searchByMessageId("0838884567");
        assertTrue(result.contains("It is dinner time!"));
    }
    
    @Test
    public void testSearchByRecipient() {
        Message msg2 = new Message();
        msg2.setMessageId("2222222222");
        msg2.setMessageHash("22:0:WHEREON");
        msg2.setRecipient("+27838884567");
        msg2.setMessageText("Where are you? You are late! I have asked you to be on time.");
        msg2.setMessageNumber(0);
        msg2.sendMessage(3); // Store
        
        Message msg5 = new Message();
        msg5.setMessageId("5555555555");
        msg5.setMessageHash("55:1:OKLEAVING");
        msg5.setRecipient("+27838884567");
        msg5.setMessageText("Ok, I am leaving without you.");
        msg5.setMessageNumber(1);
        msg5.sendMessage(3); // Store
        
        String result = msg5.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }
    
    @Test
    public void testDeleteByMessageHash() {
        Message msg = new Message();
        msg.setMessageId("2222222222");
        msg.setMessageHash("22:0:WHEREON");
        msg.setRecipient("+27838884567");
        msg.setMessageText("Where are you? You are late! I have asked you to be on time.");
        msg.setMessageNumber(0);
        msg.sendMessage(3); // Store
        
        String result = msg.deleteByMessageHash("22:0:WHEREON");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("successfully deleted"));
    }
    
    @Test
    public void testDisplayReport() {
        Message msg = new Message();
        msg.setMessageId("1111111111");
        msg.setMessageHash("11:0:CAKEGET");
        msg.setRecipient("+27834557896");
        msg.setMessageText("Did you get the cake?");
        msg.setMessageNumber(0);
        msg.sendMessage(1); // Send
        
        String report = msg.displayReport();
        assertTrue(report.contains("Message Hash"));
        assertTrue(report.contains("Recipient"));
        assertTrue(report.contains("Message"));
        assertTrue(report.contains("Did you get the cake?"));
    }
}