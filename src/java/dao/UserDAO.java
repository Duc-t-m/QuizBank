/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER NITRO
 */
public class UserDAO {
    Connection conn;
    String status;

    public UserDAO() {
        try {
            conn = new DBConnect().getConnection();
            status = "OK";
        } catch (Exception e) {
            status = "Error at connection" + e.getMessage();
        }
    }
    
    public int addUser(User user) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[users]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[email]\n"
                + "           ,[isTeacher])\n"
                + "     VALUES\n"
                + "           (?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getUsername());
            pre.setString(2, user.getPassword());
            pre.setString(3, user.getEmail());
            pre.setInt(4, user.getIsTeacher());
             n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void updateUser(User user) {
        String sql = "UPDATE [dbo].[users]\n"
                + "   SET [password] = ?\n"
                + "      ,[email] = ?\n"
                + "      ,[isTeacher] = ?\n"
                + " WHERE [username] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(4, user.getUsername());
            pre.setString(1, user.getPassword());
            pre.setString(2, user.getEmail());
            pre.setInt(3, user.getIsTeacher());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet getData(String sql){
        ResultSet rs = null;
         Statement state;
        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = state.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;    
    }
    
    public Vector<User> getAllUsers (String sql){
        Vector<User> vector = new Vector<>();
        ResultSet rs = this.getData(sql);
        try {
            while(rs.next()){
                String username = rs.getString(1); 
                String password = rs.getString(2);
                String email = rs.getString(3);
                int isTeacher = rs.getInt(4);
                User user = new User(username, password, email, isTeacher);
                vector.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public void update(String user, String password) {
        String sql = "update users set password=? where username=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, user);
            ps.execute();
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String getPass(String user) {
        String sql = "select password from users where username=?";
        String pass="";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            rs.next();
            pass = rs.getString(1);
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return pass;
    }
    
}
