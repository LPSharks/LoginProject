/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package loginproject;
import java.util.Scanner;
/**
 *
 * @author prais
 */
public class LoginProject {

     // Scanner object for reading user input
    private static Scanner scanner = new Scanner(System.in);
    
    // Login object to store user data
    private static Login loginSystem;
    
    /**
     * Main method - Entry point of the application
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialize the login system
        loginSystem = new Login();
        
        // Display welcome message
        displayWelcome();
        
        // Main program loop
        boolean running = true;
        
        while (running) {
            displayMenu();
            
            // Get user choice
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine().trim();
            
            // Process user choice
            switch (choice) {
                case "1":
                    // Register new account
                    handleRegistration();
                    break;
                    
                case "2":
                    // Login to existing account
                    handleLogin();
                    break;
                    
                case "3":
                    // Exit application
                    System.out.println("\n========================================");
                    System.out.println("Thank you for using the Login System!");
                    System.out.println("Goodbye!");
                    System.out.println("========================================");
                    running = false;
                    break;
                    
                default:
                    System.out.println("\n*** Invalid choice! Please enter 1, 2, or 3. ***\n");
            }
        }
        
        // Close scanner to prevent resource leak
        scanner.close();
    }
    
    private static void displayWelcome() {
        System.out.println("========================================");
        System.out.println("     USER REGISTRATION & LOGIN SYSTEM");
        System.out.println("========================================");
        System.out.println("   South African Cell Phone Validator");
        System.out.println("========================================\n");
    }
    
    private static void displayMenu() {
        System.out.println("------------- MAIN MENU -------------");
        System.out.println("1. Register New Account");
        System.out.println("2. Login to Account");
        System.out.println("3. Exit Application");
        System.out.println("-------------------------------------\n");
    }
    
    private static void handleRegistration() {
        System.out.println("\n========== ACCOUNT REGISTRATION ==========\n");
        
        // Collect personal information
        System.out.print("Enter your First Name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Enter your Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        // Update login system with names (create new instance with names)
        loginSystem = new Login(firstName, lastName);
        
        // Collect username
        System.out.println("\n--- Username Requirements ---");
        System.out.println("• Must contain an underscore (_)");
        System.out.println("• Maximum 5 characters long");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        
        // Collect password
        System.out.println("\n--- Password Requirements ---");
        System.out.println("• At least 8 characters long");
        System.out.println("• At least one CAPITAL letter");
        System.out.println("• At least one number (0-9)");
        System.out.println("• At least one special character (!@#$%^&*)");
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();
        
        // Collect cell phone number
        System.out.println("\n--- Cell Phone Requirements ---");
        System.out.println("• Must include international code (+27)");
        System.out.println("• Followed by exactly 9 digits");
        System.out.println("• Example: +27838968976");
        System.out.print("Enter South African Cell Phone Number: ");
        String cellPhone = scanner.nextLine().trim();
        
        // Process registration
        System.out.println("\n---------- Processing Registration ----------");
        
        // Call registerUser method from Login class
        String result = loginSystem.registerUser(username, password, cellPhone, 
                                                  firstName, lastName);
        
        // Display result
        System.out.println(result);
        
        // Check if registration was successful
        if (loginSystem.isRegistered()) {
            System.out.println("\n✓✓✓ REGISTRATION SUCCESSFUL! ✓✓✓");
            System.out.println("You can now login with your credentials.\n");
        } else {
            System.out.println("\n✗✗✗ REGISTRATION FAILED ✗✗✗");
            System.out.println("Please review the error messages above and try again.\n");
        }
    }
    
    private static void handleLogin() {
        System.out.println("\n========== LOGIN ==========\n");
        
        // Check if any user is registered
        if (!loginSystem.isRegistered()) {
            System.out.println("*** No registered user found! ***");
            System.out.println("Please register an account first (Menu option 1).\n");
            return;
        }
        
        //Collect login credentials
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();
        
        System.out.println("\n---------- Verifying Credentials ----------");
        
        //Call loginUser method to verify credentials
        boolean loginSuccess = loginSystem.loginUser(username, password);
        
        //Get appropriate message based on result
        String statusMessage = loginSystem.returnLoginStatus(loginSuccess);
        
        //Display result
        System.out.println(statusMessage);
        
        if (loginSuccess) {
            System.out.println("\n--- User Details ---");
            System.out.println("Username: " + loginSystem.getUsername());
            System.out.println("Cell Phone: " + loginSystem.getCellPhoneNumber());
            System.out.println();
        } else {
            System.out.println();
        }
    }
}
