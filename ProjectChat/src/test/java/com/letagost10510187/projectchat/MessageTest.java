/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.letagost10510187.projectchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Message class.
 * 
 * @author prais
 */
public class MessageTest {
    
    @Test
    public void testMessageLength_Success() {
        Message msg = new Message();
        String result = msg.checkMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", result);
    }
    
    @Test
    public void testMessageLength_Failure() {
        Message msg = new Message();
        // Create a message longer than 250 characters
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
}