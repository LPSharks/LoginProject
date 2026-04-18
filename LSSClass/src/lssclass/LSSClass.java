/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lssclass;
import java.util.Scanner;
/**
 *
 * @author prais
 */
public class LSSClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        
        System.out.print("Enter username: ");
        String username = s.nextLine();
        
        System.out.print("Enter password: ");
        String password =s.nextLine();
        
        Login l = new Login();
        
        boolean isValid = l.checkLogin(username, password);
        
        if (isValid){
            System.out.println("Login successful");
        } else {
            System.out.println("Invalid username or password");
        }
        
        s.close();
    }
    
}
