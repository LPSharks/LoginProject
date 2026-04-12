/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginproject;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 *
 * @author prais
 */
public class Login {
    
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;
    private boolean isRegistered;
    
    // Regular expression pattern for South African phone numbers
    // South Africa country code is +27, followed by exactly 9 digits
    private static final String SA_PHONE_REGEX = "^\\+27\\d{9}$";
    
    public Login() {
        this.username = "";
        this.password = "";
        this.cellPhoneNumber = "";
        this.firstName = "";
        this.lastName = "";
        this.isRegistered = false;
    }
    
    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = "";
        this.password = "";
        this.cellPhoneNumber = "";
        this.isRegistered = false;
    }
    
    public boolean checkUserName(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        // Must contain underscore AND be 5 characters or less
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasCapitalLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;
        
        // Loop through each character to check requirements
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            
            if (Character.isUpperCase(c)) {
                hasCapitalLetter = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                // Any character that is not a letter or digit is special
                hasSpecialCharacter = true;
            }
        }
        
        // All three conditions must be true
        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
    }
    
    public boolean checkCellPhoneNumber(String cellPhone) {
        if (cellPhone == null || cellPhone.isEmpty()) {
            return false;
        }
        
        // Create pattern and matcher for regex validation
        Pattern pattern = Pattern.compile(SA_PHONE_REGEX);
        Matcher matcher = pattern.matcher(cellPhone);
        
        return matcher.matches();
    }
    
    public String registerUser(String username, String password, String cellPhone, 
                               String firstName, String lastName) {
        
        // Step 1: Validate username format
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your " +
                   "username contains an underscore and is no more than five characters in length.";
        }
        
        // Step 2: Validate password complexity
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password " +
                   "contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        // Step 3: Validate cell phone number
        if (!checkCellPhoneNumber(cellPhone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        
        // Step 4: All validations passed - store user details
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isRegistered = true;
        
        // Return success message
        return "Username successfully captured.\n" +
               "Password successfully captured.\n" +
               "Cell phone number successfully added.";
    }
    
    public boolean loginUser(String inputUsername, String inputPassword) {
        // Check if user is registered first
        if (!this.isRegistered) {
            return false;
        }
        
        // Compare input credentials with stored credentials
        boolean usernameMatch = this.username.equals(inputUsername);
        boolean passwordMatch = this.password.equals(inputPassword);
        
        return usernameMatch && passwordMatch;
    }
    
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            // Successful login - personalized welcome message
            return "Welcome " + this.firstName + " " + this.lastName + 
                   ", it is great to see you again.";
        } else {
            // Failed login - error message
            return "Username or password incorrect, please try again.";
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public String getCellPhoneNumber() {
        return this.cellPhoneNumber;
    }
    
    public boolean isRegistered() {
        return this.isRegistered;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
 

