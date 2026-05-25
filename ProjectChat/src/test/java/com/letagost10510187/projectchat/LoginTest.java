/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.letagost10510187.projectchat;

/**
 *
 * @author prais
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    
    private Login login;
    
    @BeforeEach
    public void setUp() {
        login = new Login();
    }
    
    // ==================== TEST: checkUserName ====================
    
    @Test
    public void testCheckUserNameValid() {
        assertTrue(login.checkUserName("ab_cd"), "Valid username with underscore and <=5 chars should pass");
    }
    
    @Test
    public void testCheckUserNameNoUnderscore() {
        assertFalse(login.checkUserName("abcde"), "Username without underscore should fail");
    }
    
    @Test
    public void testCheckUserNameTooLong() {
        assertFalse(login.checkUserName("abcdef_"), "Username longer than 5 chars should fail");
    }
    
    @Test
    public void testCheckUserNameNull() {
        assertFalse(login.checkUserName(null), "Null username should fail");
    }
    
    @Test
    public void testCheckUserNameEmpty() {
        assertFalse(login.checkUserName(""), "Empty username should fail");
    }
    
    // ==================== TEST: checkPasswordComplexity ====================
    
    @Test
    public void testCheckPasswordComplexityValid() {
        // Needs: 8+ chars, uppercase, number, special character
        assertTrue(login.checkPasswordComplexity("Passw0rd!"), "Valid complex password should pass");
    }
    
    @Test
    public void testCheckPasswordTooShort() {
        assertFalse(login.checkPasswordComplexity("Pass1!"), "Password less than 8 chars should fail");
    }
    
    @Test
    public void testCheckPasswordNoUppercase() {
        assertFalse(login.checkPasswordComplexity("passw0rd!"), "Password without uppercase should fail");
    }
    
    @Test
    public void testCheckPasswordNoNumber() {
        assertFalse(login.checkPasswordComplexity("Password!"), "Password without number should fail");
    }
    
    @Test
    public void testCheckPasswordNoSpecialChar() {
        assertFalse(login.checkPasswordComplexity("Passw0rd"), "Password without special char should fail");
    }
    
    @Test
    public void testCheckPasswordNull() {
        assertFalse(login.checkPasswordComplexity(null), "Null password should fail");
    }
    
    // ==================== TEST: checkCellPhoneNumber ====================
    
    @Test
    public void testCheckCellPhoneNumberValid() {
        // SA format: +27 followed by exactly 9 digits
        assertTrue(login.checkCellPhoneNumber("+27123456789"), "Valid SA phone number should pass");
    }
    
    @Test
    public void testCheckCellPhoneNumberInvalidFormat() {
        assertFalse(login.checkCellPhoneNumber("0123456789"), "Phone without +27 should fail");
    }
    
    @Test
    public void testCheckCellPhoneNumberTooShort() {
        assertFalse(login.checkCellPhoneNumber("+271234567"), "Phone with less than 9 digits should fail");
    }
    
    @Test
    public void testCheckCellPhoneNumberTooLong() {
        assertFalse(login.checkCellPhoneNumber("+271234567890"), "Phone with more than 9 digits should fail");
    }
    
    @Test
    public void testCheckCellPhoneNumberNull() {
        assertFalse(login.checkCellPhoneNumber(null), "Null phone number should fail");
    }
    
    @Test
    public void testCheckCellPhoneNumberEmpty() {
        assertFalse(login.checkCellPhoneNumber(""), "Empty phone number should fail");
    }
    
    // ==================== TEST: registerUser ====================
    
    @Test
    public void testRegisterUserSuccess() {
        String result = login.registerUser("ab_cd", "Passw0rd!", "+27123456789", "John", "Doe");
        assertEquals("Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.", result);
        assertTrue(login.isRegistered());
        assertEquals("ab_cd", login.getUsername());
        assertEquals("John", login.getFirstName());
        assertEquals("Doe", login.getLastName());
        assertEquals("+27123456789", login.getCellPhoneNumber());
    }
    
    @Test
    public void testRegisterUserInvalidUsername() {
        String result = login.registerUser("abcdef", "Passw0rd!", "+27123456789", "John", "Doe");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", result);
        assertFalse(login.isRegistered());
    }
    
    @Test
    public void testRegisterUserInvalidPassword() {
        String result = login.registerUser("ab_cd", "password", "+27123456789", "John", "Doe");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);
        assertFalse(login.isRegistered());
    }
    
    @Test
    public void testRegisterUserInvalidPhone() {
        String result = login.registerUser("ab_cd", "Passw0rd!", "0123456789", "John", "Doe");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);
        assertFalse(login.isRegistered());
    }
    
    // ==================== TEST: loginUser ====================
    
    @Test
    public void testLoginUserSuccess() {
        login.registerUser("ab_cd", "Passw0rd!", "+27123456789", "John", "Doe");
        boolean result = login.loginUser("ab_cd", "Passw0rd!");
        assertTrue(result, "Correct credentials should login successfully");
    }
    
    @Test
    public void testLoginUserWrongPassword() {
        login.registerUser("ab_cd", "Passw0rd!", "+27123456789", "John", "Doe");
        boolean result = login.loginUser("ab_cd", "WrongPass1!");
        assertFalse(result, "Wrong password should fail login");
    }
    
    @Test
    public void testLoginUserWrongUsername() {
        login.registerUser("ab_cd", "Passw0rd!", "+27123456789", "John", "Doe");
        boolean result = login.loginUser("wrong_", "Passw0rd!");
        assertFalse(result, "Wrong username should fail login");
    }
    
    @Test
    public void testLoginUserNotRegistered() {
        boolean result = login.loginUser("ab_cd", "Passw0rd!");
        assertFalse(result, "Login without registration should fail");
    }
    
    // ==================== TEST: returnLoginStatus ====================
    
    @Test
    public void testReturnLoginStatusSuccess() {
        login.registerUser("ab_cd", "Passw0rd!", "+27123456789", "John", "Doe");
        login.loginUser("ab_cd", "Passw0rd!");
        String status = login.returnLoginStatus(true);
        assertEquals("Welcome John Doe, it is great to see you again.", status);
    }
    
    @Test
    public void testReturnLoginStatusFailure() {
        String status = login.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", status);
    }
    
    // ==================== TEST: Getters and Setters ====================
    
    @Test
    public void testSetAndGetFirstName() {
        login.setFirstName("John");
        assertEquals("John", login.getFirstName());
    }
    
    @Test
    public void testSetAndGetLastName() {
        login.setLastName("Doe");
        assertEquals("Doe", login.getLastName());
    }
    
    @Test
    public void testConstructorWithNames() {
        Login namedLogin = new Login("John", "Doe");
        assertEquals("John", namedLogin.getFirstName());
        assertEquals("Doe", namedLogin.getLastName());
        assertFalse(namedLogin.isRegistered());
    }
}
