package DAO;

import java.sql.*;
import java.util.UUID;
import Model.AuthToken;

public class AuthTokenDAO {
    private Connection connection;

    /**
     * All DAO objects are created by a DAOMaster which enables connection sharing.
     */
    AuthTokenDAO(Connection c){
        this.connection = c;
    }

    /**
     * Creates and inserts in database a valid Authentication Token for the specified user.
     * @param username The username of the user to be given a valid Authentication Token.
     */
    public AuthToken createOrRefreshAuthToken(String username){
        PreparedStatement stmt;
        try{
            //System.out.println("Generating token for: " + username);
            AuthToken checkExistingToken = getAuthTokenWithUsername(username);
            if (checkExistingToken != null){ //token already exists, so delete it and add a fresh one
                //System.out.println("Token already exists for " + username);
                deleteAuthToken(username);
                //connection.commit();
            }
            String token = UUID.randomUUID().toString();
            String sql = "INSERT INTO authentication (username, AuthToken, creationTime) VALUES (?, ?);";

            //TODO insert current time into authToken table

            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, token);
            stmt.execute();
        }catch (SQLException ex){
            System.out.println("FAILED TO GENERATE AUTHTOKEN FOR " + username);
            ex.printStackTrace();
        }
        return getAuthTokenWithUsername(username);
    }
    /**
     * Retrieves an Authentication Token for the specified user. Returns null if there is not a valid Authentication Token.
     * @param username The username of the user who may or may not have a valid Authentication Token.
     * @return AuthToken
     */
    public AuthToken getAuthTokenWithUsername(String username){
        PreparedStatement stmt;
        AuthToken token = null;
        try{
            String sql = "SELECT authToken FROM authentication WHERE username = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) //if the query has something in its resultset...
                token = new AuthToken(username, rs.getString(1));

        }catch (SQLException ex){
            System.out.println("Failed to get an authToken for: " + username);
            ex.printStackTrace();
        }
        return token;
    }


    private void deleteAuthToken(String username){
        PreparedStatement stmt;
        try{
            //System.out.println("deleting token for: " + username);
            String sql = "DELETE FROM authentication WHERE username = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Failed to delete authToken for: " + username);
        }
    }

    //TODO implement a timer for authTokens

}
