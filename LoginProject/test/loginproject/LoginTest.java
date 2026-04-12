/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package loginproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author prais
 */
public class LoginTest {
    
    public LoginTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

@Test
public void testValidUsername() {
    Login login = new Login();
    assertTrue(login.checkUserName("user_"));
}

@Test
public void testInvalidUsername() {
    Login login = new Login();
    assertFalse(login.checkUserName("username")); // no underscore
    assertFalse(login.checkUserName("user_name")); // too long
}

@Test
public void testValidPassword() {
    Login login = new Login();
    assertTrue(login.checkPasswordComplexity("Pass123!"));
}

@Test
public void testInvalidPassword() {
    Login login = new Login();
    assertFalse(login.checkPasswordComplexity("pass")); // too short
    assertFalse(login.checkPasswordComplexity("Password")); // no number/special
}

@Test
public void testValidCellPhone() {
    Login login = new Login();
    assertTrue(login.checkCellPhoneNumber("+27821234567"));
}

@Test
public void testInvalidCellPhone() {
    Login login = new Login();
    assertFalse(login.checkCellPhoneNumber("0821234567")); // missing +27
    assertFalse(login.checkCellPhoneNumber("+27123")); // too short
}
}
