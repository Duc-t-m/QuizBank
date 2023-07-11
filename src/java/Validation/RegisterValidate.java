/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validation;

import dao.UserDAO;
import Models.User;
import java.util.Vector;

/**
 *
 * @author ACER NITRO
 */
public class RegisterValidate {
    public static String usernameValidate (String username){
        String msg = "Username existed!";
        UserDAO dao = new UserDAO();
        Vector<User> vec = dao.getAllUsers("select * from users where username='"+username+"'");
        if(vec.size() > 0){
            return msg;
        }
        else
            return null;
    }
    
    public static String passwordValidate (String password){
        String msg ="Invalid password";
        if(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,14}$")){
            return null;
        }
        else
            return msg;
    }
}
