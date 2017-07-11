package dao;

import java.sql.*;

import model.User;

public class UserDAO {
    private Connection connection;

    /**
     * All DAO objects are created by a MasterDAO which enables connection sharing.
     */
    UserDAO(Connection c){
        this.connection = c;
    }

    /**
     * Receives registration information from the register service, registers into the database and returns a User model.
     * @param username Registers a new user with this username.
     * @param password Registers a new user with this password.
     * @return User
     */
    public User register(String username, String password){
        PreparedStatement stmt;
        try{
            String sql = "INSERT INTO users (username, password)\n" +
                    "VALUES (?, ?);";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }

        return new User(username, password);
    }

    public User getUser(String username){
        PreparedStatement stmt;
        User user = null;
        try{
            String sql = "SELECT * FROM users WHERE username = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                user = new User(rs.getString(1), rs.getString(2));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }

        return user;
    }

    /**
     * Receives login information from the login service. If valid, returns a User model.
     * Otherwise login will return null.
     * @param username Username of user to login.
     * @param password Password of user to login.
     * @return User
     */
    public User login(String username, String password){
        PreparedStatement stmt;
        User user = null;
        try{
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                user = new User(rs.getString(1), rs.getString(2));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Login has FAILED!");
            return null;
        }

        return user;
    }



}
