/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lssclass;

/**
 *
 * @author prais
 */
public class Login {
    public boolean checkLogin(String username, String password){
        if (username.equals("admin")&& password.equals("1234")){
            return true;
        } else {
            return false;
        }
    }
}
