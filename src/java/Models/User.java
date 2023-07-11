/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author ACER NITRO
 */
public class User {
    String username;
    String password;
    String email;
    int isTeacher;

    public User() {
    }

    public User(String username, String password, String email, int isTeacher) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isTeacher = isTeacher;
    }

    public User(String password, String email, int isTeacher) {
        this.password = password;
        this.email = email;
        this.isTeacher = isTeacher;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(int isTeacher) {
        this.isTeacher = isTeacher;
    }
    
}
