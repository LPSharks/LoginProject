/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.letagost10510187.projectchat;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Message class for handling message creation, validation, and storage.
 * Part 2 of the project.
 * 
 * @author prais
 */
public class Message {
    
    // Message attributes
    private String messageId;
    private String messageHash;
    private String recipient;
    private String messageText;
    private int messageNumber;
    
    // Static counter for total messages sent
    private static int totalMessagesSent = 0;
    
    // Array to store all sent messages
    private static String[] sentMessages = new String[100];
    private static int messageCount = 0;
    
    // Constructor
    public Message() {
    }
    
    /**
     * Generates a random 10-digit message ID.
     * @return The generated message ID as a String
     */
    public String createMessageId() {
        Random r = new Random();
        StringBuilder b = new StringBuilder();
        
        for (int i = 0; i < 10; i++) {
            int digit = r.nextInt(10);
            b.append(digit);
        }
        
        this.messageId = b.toString();
        return this.messageId;
    }
    
    /**
     * Checks that the message ID is not more than 10 characters.
     * @return true if valid, false otherwise
     */
    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }
    
    /**
     * Checks that the recipient cell number is correctly formatted.
     * Must contain international code (+) and be no more than 10 chars after code.
     * @param cell The cell number to check
     * @return Success or failure message
     */
    public String checkRecipientCell(String cell) {
        if (cell == null || cell.isEmpty()) {
            return "Cell phone number is incorrectly formatted or does not contain " +
                   "an international code. Please correct the number and try again.";
        }
        
        // Check if it starts with + (international code)
        if (!cell.startsWith("+")) {
            return "Cell phone number is incorrectly formatted or does not contain " +
                   "an international code. Please correct the number and try again.";
        }
        
        // Check total length (should be reasonable - +27 + up to 10 digits = max 13)
        if (cell.length() > 13) {
            return "Cell phone number is incorrectly formatted or does not contain " +
                   "an international code. Please correct the number and try again.";
        }
        
        // Check that after +, all characters are digits
        String digitsOnly = cell.substring(1);
        boolean allDigits = true;
        for (char c : digitsOnly.toCharArray()) {
            if (!Character.isDigit(c)) {
                allDigits = false;
                break;
            }
        }
        
        if (!allDigits) {
            return "Cell phone number is incorrectly formatted or does not contain " +
                   "an international code. Please correct the number and try again.";
        }
        
        this.recipient = cell;
        return "Cell phone number successfully captured.";
    }
    
    /**
     * Checks if the message text is within 250 characters.
     * @param text The message text
     * @return Success or failure message
     */
    public String checkMessageLength(String text) {
        if (text == null) {
            return "Message exceeds 250 characters by 0; please reduce the size.";
        }
        
        if (text.length() <= 250) {
            this.messageText = text;
            return "Message ready to send.";
        } else {
            int excess = text.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
    
    /**
     * Creates the message hash.
     * Format: first 2 digits of ID + ":" + message number + ":" + first word + last word (ALL CAPS)
     * Example: 00:0:HITHANKS
     * @return The message hash
     */
    public String createMessageHash() {
        // Get first 2 digits of message ID
        String firstTwo = messageId.substring(0, 2);
        
        // Get message number as string
        String msgNum = String.valueOf(messageNumber);
        
        // Get first and last words from message
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        
        // Remove punctuation from words
        lastWord = lastWord.replaceAll("[^A-Z]", "");
        firstWord = firstWord.replaceAll("[^A-Z]", "");
        
        // Build hash
        this.messageHash = firstTwo + ":" + msgNum + ":" + firstWord + lastWord;
        return this.messageHash;
    }
    
    /**
     * Allows user to choose to send, store, or disregard the message.
     * @param choice 1=Send, 2=Disregard, 3=Store
     * @return Appropriate message
     */
    public String sendMessage(int choice) {
        switch (choice) {
            case 1:
                // Send the message
                totalMessagesSent++;
                String details = "Message ID: " + messageId + "\n" +
                               "Message Hash: " + messageHash + "\n" +
                               "Recipient: " + recipient + "\n" +
                               "Message: " + messageText;
                sentMessages[messageCount] = details;
                messageCount++;
                return "Message successfully sent.";
            case 2:
                // Disregard the message
                return "Press 0 to delete the message.";
            case 3:
                // Store the message
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }
    
    /**
     * Returns all messages sent during the program.
     * @return String containing all sent messages
     */
    public String printMessages() {
        if (messageCount == 0) {
            return "No messages have been sent yet.";
        }
        
        StringBuilder allMessages = new StringBuilder();
        allMessages.append("========== ALL SENT MESSAGES ==========\n\n");
        
        for (int i = 0; i < messageCount; i++) {
            allMessages.append("Message ").append(i + 1).append(":\n");
            allMessages.append(sentMessages[i]).append("\n\n");
        }
        
        return allMessages.toString();
    }
    
    /**
     * Returns the total number of messages sent.
     * @return Total messages sent
     */
    public static int returnTotalMessagesSent() {
        return totalMessagesSent;
    }
    
    /**
     * Stores messages in a JSON file.
     * Reference: https://www.w3schools.com/js/js_json_intro.asp
     * Using org.json.simple library for Java JSON handling
     */
    public void storeMessage() {
        try {
            JSONObject messageObj = new JSONObject();
            messageObj.put("messageId", messageId);
            messageObj.put("messageHash", messageHash);
            messageObj.put("recipient", recipient);
            messageObj.put("messageText", messageText);
            messageObj.put("messageNumber", messageNumber);
            
            JSONArray messageList = new JSONArray();
            messageList.add(messageObj);
            
            FileWriter file = new FileWriter("messages.json", true);
            file.write(messageList.toJSONString());
            file.write("\n");
            file.flush();
            file.close();
            
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }
    
    // Getters and Setters
    
    public String getMessageId() {
        return messageId;
    }
    
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    public String getMessageHash() {
        return messageHash;
    }
    
    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public String getMessageText() {
        return messageText;
    }
    
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    
    public int getMessageNumber() {
        return messageNumber;
    }
    
    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }
}