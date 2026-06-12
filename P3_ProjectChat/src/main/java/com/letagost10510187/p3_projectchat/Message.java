/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.letagost10510187.p3_projectchat;

import java.util.Random;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Message class for handling message creation, validation, storage, and display.
 * Part 3 - Store Data and Display Task Report.
 * 
 * @author prais
 */
public class Message {
    
    // Message attributes
    private String messageId;
    private String messageHash;
    private String sender;
    private String recipient;
    private String messageText;
    private int messageNumber;
    
    // Static counter for total messages sent
    private static int totalMessagesSent = 0;
    
    // Arrays to store messages (Part 3 requirement - no hard-coding)
    private static String[] sentMessages = new String[100];
    private static String[] disregardedMessages = new String[100];
    private static String[] storedMessages = new String[100];
    private static String[] messageHashes = new String[100];
    private static String[] messageIds = new String[100];
    
    // Array counters
    private static int sentCount = 0;
    private static int disregardedCount = 0;
    private static int storedCount = 0;
    private static int hashCount = 0;
    private static int idCount = 0;
    
    // Constructor
    public Message() {
    }
    
    /**
     * Resets all static arrays and counters (for testing purposes).
     */
    public static void resetArrays() {
        sentMessages = new String[100];
        disregardedMessages = new String[100];
        storedMessages = new String[100];
        messageHashes = new String[100];
        messageIds = new String[100];
        sentCount = 0;
        disregardedCount = 0;
        storedCount = 0;
        hashCount = 0;
        idCount = 0;
        totalMessagesSent = 0;
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
        // Add to message ID array
        messageIds[idCount] = this.messageId;
        idCount++;
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
        // Add to hash array
        messageHashes[hashCount] = this.messageHash;
        hashCount++;
        return this.messageHash;
    }
    
    /**
     * Allows user to choose to send, store, or disregard the message.
     * Populates the appropriate arrays based on choice.
     * @param choice 1=Send, 2=Disregard, 3=Store
     * @return Appropriate message
     */
    public String sendMessage(int choice) {
        switch (choice) {
            case 1:
                // Send the message - add to sentMessages array
                totalMessagesSent++;
                sentMessages[sentCount] = messageText; // Store just the message text
                sentCount++;
                return "Message successfully sent.";
            case 2:
                // Disregard the message - add to disregardedMessages array
                disregardedMessages[disregardedCount] = messageText;
                disregardedCount++;
                return "Press 0 to delete the message.";
            case 3:
                // Store the message - add to storedMessages array
                storeMessage();
                storedMessages[storedCount] = messageText;
                storedCount++;
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }
    
    // ========== PART 3: ARRAY GETTERS ==========
    
    /**
     * Returns all sent messages array.
     * @return Array of sent message texts
     */
    public static String[] getSentMessagesArray() {
        String[] result = new String[sentCount];
        for (int i = 0; i < sentCount; i++) {
            result[i] = sentMessages[i];
        }
        return result;
    }
    
    /**
     * Returns all disregarded messages array.
     * @return Array of disregarded message texts
     */
    public static String[] getDisregardedMessagesArray() {
        String[] result = new String[disregardedCount];
        for (int i = 0; i < disregardedCount; i++) {
            result[i] = disregardedMessages[i];
        }
        return result;
    }
    
    /**
     * Returns all stored messages array.
     * @return Array of stored message texts
     */
    public static String[] getStoredMessagesArray() {
        String[] result = new String[storedCount];
        for (int i = 0; i < storedCount; i++) {
            result[i] = storedMessages[i];
        }
        return result;
    }
    
    /**
     * Returns all message hashes array.
     * @return Array of message hashes
     */
    public static String[] getMessageHashesArray() {
        String[] result = new String[hashCount];
        for (int i = 0; i < hashCount; i++) {
            result[i] = messageHashes[i];
        }
        return result;
    }
    
    /**
     * Returns all message IDs array.
     * @return Array of message IDs
     */
    public static String[] getMessageIdsArray() {
        String[] result = new String[idCount];
        for (int i = 0; i < idCount; i++) {
            result[i] = messageIds[i];
        }
        return result;
    }
    
    // ========== PART 3: STORED MESSAGES MENU METHODS ==========
    
    /**
     * a. Display sender and recipient of all stored messages.
     * @return Formatted string with sender and recipient details
     */
    public String displaySenderAndRecipient() {
        if (storedCount == 0) {
            return "No stored messages found.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("========== STORED MESSAGES - SENDER & RECIPIENT ==========\n\n");
        
        for (int i = 0; i < storedCount; i++) {
            sb.append("Message ").append(i + 1).append(":\n");
            sb.append("  Sender: ").append(sender != null ? sender : "Unknown").append("\n");
            sb.append("  Recipient: ").append(recipient != null ? recipient : "Unknown").append("\n");
            sb.append("  Message: ").append(storedMessages[i]).append("\n\n");
        }
        
        return sb.toString();
    }
    /**
     * b. Display the longest stored message.
     * @return The longest stored message
     */
    public String displayLongestStoredMessage() {
        if (storedCount == 0) {
            return "No stored messages found.";
        }
        
        String longest = storedMessages[0];
        for (int i = 1; i < storedCount; i++) {
            if (storedMessages[i] != null && storedMessages[i].length() > longest.length()) {
                longest = storedMessages[i];
            }
        }
        
        return longest;
    }
    
    /**
     * c. Search for a message ID and display corresponding recipient and message.
     * @param searchId The message ID to search for
     * @return Recipient and message if found, or not found message
     */
    public String searchByMessageId(String searchId) {
        // Search through all message IDs
        for (int i = 0; i < idCount; i++) {
            if (messageIds[i] != null && messageIds[i].equals(searchId)) {
                // Find the message text - check all arrays at this index
                String foundMessage = null;
                
                // The message could be in sent, stored, or disregarded
                // We need to find which one corresponds to this ID
                // Since IDs are added in order, and messages are added in order,
                // the index i should match the message index
                
                if (i < sentCount && sentMessages[i] != null) {
                    foundMessage = sentMessages[i];
                } else if (i < storedCount && storedMessages[i] != null) {
                    foundMessage = storedMessages[i];
                } else if (i < disregardedCount && disregardedMessages[i] != null) {
                    foundMessage = disregardedMessages[i];
                }
                
                // If not found at same index, search all arrays
                if (foundMessage == null) {
                    for (int j = 0; j < sentCount; j++) {
                        if (sentMessages[j] != null) {
                            foundMessage = sentMessages[j];
                            break;
                        }
                    }
                }
                if (foundMessage == null) {
                    for (int j = 0; j < storedCount; j++) {
                        if (storedMessages[j] != null) {
                            foundMessage = storedMessages[j];
                            break;
                        }
                    }
                }
                if (foundMessage == null) {
                    for (int j = 0; j < disregardedCount; j++) {
                        if (disregardedMessages[j] != null) {
                            foundMessage = disregardedMessages[j];
                            break;
                        }
                    }
                }
                
                if (foundMessage != null) {
                    return "Recipient: " + recipient + "\nMessage: " + foundMessage;
                }
            }
        }
        return "Message ID not found.";
    }
    
    /**
     * d. Search all messages sent or stored regarding a particular recipient.
     * @param searchRecipient The recipient to search for
     * @return All matching messages
     */
    public String searchByRecipient(String searchRecipient) {
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        
        // Search sent messages
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i] != null) {
                sb.append("[Sent] ").append(sentMessages[i]).append("\n");
                found = true;
            }
        }
        
        // Search stored messages
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i] != null) {
                sb.append("[Stored] ").append(storedMessages[i]).append("\n");
                found = true;
            }
        }
        
        if (!found) {
            return "No messages found for recipient: " + searchRecipient;
        }
        
        return sb.toString();
    }
    
    /**
     * e. Delete a message using the message hash.
     * @param hashToDelete The hash of the message to delete
     * @return Success or failure message
     */
    public String deleteByMessageHash(String hashToDelete) {
        // Find the hash in the messageHashes array
        int hashIndex = -1;
        for (int i = 0; i < hashCount; i++) {
            if (messageHashes[i] != null && messageHashes[i].equals(hashToDelete)) {
                hashIndex = i;
                break;
            }
        }
        
        if (hashIndex == -1) {
            return "Message hash not found.";
        }
        
        // The hash corresponds to a message that was processed
        // We need to find which array it's in and delete it
        // Hashes are created in order: first sent messages, then disregarded, then stored
        
        String deletedMessage = null;
        
        // Try to find in stored messages first (most common for delete)
        if (hashIndex < storedCount && storedMessages[hashIndex] != null) {
            deletedMessage = storedMessages[hashIndex];
            // Shift remaining stored messages
            for (int j = hashIndex; j < storedCount - 1; j++) {
                storedMessages[j] = storedMessages[j + 1];
            }
            storedMessages[storedCount - 1] = null;
            storedCount--;
        }
        // Try sent messages
        else if (hashIndex < sentCount && sentMessages[hashIndex] != null) {
            deletedMessage = sentMessages[hashIndex];
            for (int j = hashIndex; j < sentCount - 1; j++) {
                sentMessages[j] = sentMessages[j + 1];
            }
            sentMessages[sentCount - 1] = null;
            sentCount--;
        }
        // Try disregarded messages
        else if (hashIndex < disregardedCount && disregardedMessages[hashIndex] != null) {
            deletedMessage = disregardedMessages[hashIndex];
            for (int j = hashIndex; j < disregardedCount - 1; j++) {
                disregardedMessages[j] = disregardedMessages[j + 1];
            }
            disregardedMessages[disregardedCount - 1] = null;
            disregardedCount--;
        }
        
        // Remove from hashes array
        for (int j = hashIndex; j < hashCount - 1; j++) {
            messageHashes[j] = messageHashes[j + 1];
        }
        messageHashes[hashCount - 1] = null;
        hashCount--;
        
        if (deletedMessage != null) {
            return "Message: \"" + deletedMessage + "\" successfully deleted.";
        }
        
        return "Message hash found but message could not be deleted.";
    }
    
    /**
     * f. Display a report that lists full details of all stored messages.
     * @return Formatted report
     */
    public String displayReport() {
        if (storedCount == 0 && sentCount == 0) {
            return "No messages to display in report.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("================== MESSAGE REPORT ==================\n\n");
        
        // Display sent messages
        if (sentCount > 0) {
            sb.append("----- SENT MESSAGES -----\n");
            for (int i = 0; i < sentCount; i++) {
                sb.append("Message ").append(i + 1).append(":\n");
                sb.append("  Message Hash: ").append(i < hashCount ? messageHashes[i] : "N/A").append("\n");
                sb.append("  Recipient: ").append(recipient).append("\n");
                sb.append("  Message: ").append(sentMessages[i]).append("\n\n");
            }
        }
        
        // Display stored messages
        if (storedCount > 0) {
            sb.append("----- STORED MESSAGES -----\n");
            for (int i = 0; i < storedCount; i++) {
                sb.append("Message ").append(i + 1).append(":\n");
                sb.append("  Message Hash: ").append(i + sentCount < hashCount ? messageHashes[i + sentCount] : "N/A").append("\n");
                sb.append("  Recipient: ").append(recipient).append("\n");
                sb.append("  Message: ").append(storedMessages[i]).append("\n\n");
            }
        }
        
        sb.append("===================================================");
        return sb.toString();
    }
    
    /**
     * Returns all messages sent during the program (legacy method).
     * @return String containing all sent messages
     */
    public String printMessages() {
        if (sentCount == 0) {
            return "No messages have been sent yet.";
        }
        
        StringBuilder allMessages = new StringBuilder();
        allMessages.append("========== ALL SENT MESSAGES ==========\n\n");
        
        for (int i = 0; i < sentCount; i++) {
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
    
    /**
     * Reads stored messages from JSON file into the storedMessages array.
     * Reference: https://www.w3schools.com/js/js_json_intro.asp
     */
    public void readStoredMessagesFromJson() {
        try {
            JSONParser parser = new JSONParser();
            BufferedReader reader = new BufferedReader(new FileReader("messages.json"));
            String line;
            
            while ((line = reader.readLine()) != null) {
                JSONArray array = (JSONArray) parser.parse(line);
                for (Object obj : array) {
                    JSONObject messageObj = (JSONObject) obj;
                    String text = (String) messageObj.get("messageText");
                    if (storedCount < storedMessages.length) {
                        storedMessages[storedCount] = text;
                        storedCount++;
                    }
                }
            }
            reader.close();
            
        } catch (IOException | ParseException e) {
            System.out.println("Error reading messages: " + e.getMessage());
        }
    }
    
    // Getters and Setters
    
    public String getMessageId() {
        return messageId;
    }
    
    public void setMessageId(String messageId) {
        this.messageId = messageId;
        messageIds[idCount] = messageId;
        idCount++;
    }
    
    public String getMessageHash() {
        return messageHash;
    }
    
    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
        messageHashes[hashCount] = messageHash;
        hashCount++;
    }
    
    public String getSender() {
        return sender;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
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