/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.letagost10510187.p3_projectchat;
import java.util.Scanner;
/**
 * ProjectChat - Main application for QuickChat messaging system.
 * Combines Part 1 (Login), Part 2 (Messages), and Part 3 (Stored Data & Reports).
 * 
 * @author prais
 */
public class P3_ProjectChat {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // ========== PART 1: LOGIN SECTION ==========
        Login login = new Login();
        boolean loggedIn = false;
        
        System.out.println("========================================");
        System.out.println("    QUICKCHAT - LOGIN REQUIRED");
        System.out.println("========================================\n");
        
        // Register first
        System.out.println("----- REGISTER -----");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter Username (max 5 chars with _): ");
        String username = scanner.nextLine();
        
        System.out.print("Enter Password (8+ chars, capital, number, special): ");
        String password = scanner.nextLine();
        
        System.out.print("Enter Cell Phone (+27...): ");
        String cellPhone = scanner.nextLine();
        
        String regResult = login.registerUser(username, password, cellPhone, firstName, lastName);
        System.out.println("\n" + regResult);
        
        if (!regResult.contains("successfully captured")) {
            System.out.println("Registration failed. Exiting.");
            scanner.close();
            return;
        }
        
        // Login
        System.out.println("\n----- LOGIN -----");
        System.out.print("Enter Username: ");
        String loginUser = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String loginPass = scanner.nextLine();
        
        boolean isLoggedIn = login.loginUser(loginUser, loginPass);
        String loginStatus = login.returnLoginStatus(isLoggedIn);
        System.out.println("\n" + loginStatus);
        
        if (!isLoggedIn) {
            System.out.println("Login failed. Exiting.");
            scanner.close();
            return;
        }
        
        loggedIn = true;
        
        // ========== PART 2 & 3: QUICKCHAT MESSAGING ==========
        if (loggedIn) {
            System.out.println("\n========================================");
            System.out.println("    Welcome to QuickChat.");
            System.out.println("========================================\n");
            
            // Get the logged-in user's phone number for sender
            String senderPhone = login.getCellPhoneNumber();
            
            // Ask how many messages
            System.out.print("How many messages do you wish to enter? ");
            int numMessages = 0;
            try {
                numMessages = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Exiting.");
                scanner.close();
                return;
            }
            
            // Create Messages object
            Message msg = new Message();
            
            // Main menu loop
            boolean running = true;
            int currentMsg = 0;
            
            while (running && currentMsg < numMessages) {
                System.out.println("\n---------- MENU ----------");
                System.out.println("1. Send Messages");
                System.out.println("2. Show recently sent messages");
                System.out.println("3. Stored Messages");
                System.out.println("4. Quit");
                System.out.print("\nEnter option (1-4): ");
                
                String choice = scanner.nextLine();
                
                switch (choice) {
                    case "1":
                        // Send Message
                        currentMsg++;
                        System.out.println("\n----- MESSAGE " + currentMsg + " OF " + numMessages + " -----");
                        
                        // Generate Message ID
                        String msgId = msg.createMessageId();
                        System.out.println("Message ID generated: " + msgId);
                        
                        // Get Recipient
                        System.out.print("Enter Recipient Cell Number: ");
                        String recipient = scanner.nextLine();
                        String cellCheck = msg.checkRecipientCell(recipient);
                        System.out.println(cellCheck);
                        
                        // Set the sender (logged-in user)
                        msg.setSender(senderPhone);
                        
                        if (!cellCheck.contains("successfully captured")) {
                            currentMsg--; // Don't count this message
                            break;
                        }
                        
                        // Get Message Text
                        System.out.print("Enter Message (max 250 chars): ");
                        String text = scanner.nextLine();
                        String lenCheck = msg.checkMessageLength(text);
                        System.out.println(lenCheck);
                        
                        if (!lenCheck.contains("ready to send")) {
                            currentMsg--; // Don't count this message
                            break;
                        }
                        
                        // Set message number (0-based index)
                        msg.setMessageNumber(currentMsg - 1);
                        
                        // Create Hash
                        String hash = msg.createMessageHash();
                        
                        // Display message details before action
                        System.out.println("\n----- MESSAGE DETAILS -----");
                        System.out.println("Message ID: " + msgId);
                        System.out.println("Message Hash: " + hash.toUpperCase());
                        System.out.println("Recipient: " + recipient);
                        System.out.println("Message: " + text);
                        
                        // Ask for action
                        System.out.println("\nWhat would you like to do?");
                        System.out.println("1. Send Message");
                        System.out.println("2. Disregard Message");
                        System.out.println("3. Store Message to send later");
                        System.out.print("Enter choice (1-3): ");
                        
                        int action = 0;
                        try {
                            action = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid choice.");
                            currentMsg--;
                            break;
                        }
                        
                        String result = msg.sendMessage(action);
                        System.out.println("\n" + result);
                        
                        // If user selected delete (disregard), ask for 0
                        if (result.contains("Press 0 to delete")) {
                            System.out.print("Enter 0 to confirm deletion: ");
                            String deleteConfirm = scanner.nextLine();
                            if (deleteConfirm.equals("0")) {
                                System.out.println("Message deleted.");
                                currentMsg--;
                            }
                        }
                        
                        // Show total sent so far
                        System.out.println("\nTotal messages sent so far: " + Message.returnTotalMessagesSent());
                        break;
                        
                    case "2":
                        // Show recently sent messages
                        System.out.println("\n----- RECENT MESSAGES -----");
                        System.out.println(msg.printMessages());
                        break;
                        
                    case "3":
                        // ========== PART 3: STORED MESSAGES MENU ==========
                        boolean storedMenuRunning = true;
                        while (storedMenuRunning) {
                            System.out.println("\n---------- STORED MESSAGES MENU ----------");
                            System.out.println("a. Display sender and recipient of all stored messages");
                            System.out.println("b. Display the longest stored message");
                            System.out.println("c. Search for a message ID");
                            System.out.println("d. Search messages by recipient");
                            System.out.println("e. Delete a message using message hash");
                            System.out.println("f. Display full report");
                            System.out.println("g. Back to main menu");
                            System.out.print("\nEnter option (a-g): ");
                            
                            String storedChoice = scanner.nextLine().toLowerCase();
                            
                            switch (storedChoice) {
                                case "a":
                                    System.out.println("\n" + msg.displaySenderAndRecipient());
                                    break;
                                case "b":
                                    System.out.println("\nLongest stored message:");
                                    System.out.println(msg.displayLongestStoredMessage());
                                    break;
                                case "c":
                                    System.out.print("\nEnter Message ID to search: ");
                                    String searchId = scanner.nextLine();
                                    System.out.println("\n" + msg.searchByMessageId(searchId));
                                    break;
                                case "d":
                                    System.out.print("\nEnter recipient to search: ");
                                    String searchRecip = scanner.nextLine();
                                    System.out.println("\n" + msg.searchByRecipient(searchRecip));
                                    break;
                                case "e":
                                    System.out.print("\nEnter message hash to delete: ");
                                    String hashToDelete = scanner.nextLine();
                                    System.out.println("\n" + msg.deleteByMessageHash(hashToDelete));
                                    break;
                                case "f":
                                    System.out.println("\n" + msg.displayReport());
                                    break;
                                case "g":
                                    storedMenuRunning = false;
                                    break;
                                default:
                                    System.out.println("\nInvalid option. Please enter a-g.");
                            }
                        }
                        break;
                        
                    case "4":
                        // Quit
                        System.out.println("\nThank you for using QuickChat!");
                        System.out.println("Total messages sent: " + Message.returnTotalMessagesSent());
                        running = false;
                        break;
                        
                    default:
                        System.out.println("\nInvalid option. Please enter 1, 2, 3, or 4.");
                }
            }
            
            // If all messages entered
            if (currentMsg >= numMessages) {
                System.out.println("\nAll " + numMessages + " messages have been processed.");
                System.out.println("Total messages sent: " + Message.returnTotalMessagesSent());
            }
        }
        
        scanner.close();
    }
}

