package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *  <h1>Master Database Access Class</h1>
 *  Provides a connection and methods for accessing the database.
 *
 *  @author     Nathan Finch
 *  @since      7/11/2017
 */
public class MasterDAO
{

    //Data

    private static final String USER_TABLE_NAME = "user";
    private static final String GAME_TABLE_NAME = "game";
    private static final String AUTH_TABLE_NAME = "auth";

    private static Logger mLogger;

    static
    {
        //TODO: Initialize logging with the logger running on the server
        //logger = Logger.getLogger("");

    }

    private Connection mDatabaseAccess;
    private UserDAO mUsersAccess;
    private GameDAO mGameAccess;
    private AuthDAO mAuthAccess;

    public MasterDAO()
    {

        mDatabaseAccess = null;

        final String driver = "org.sqlite.JDBC";

        try //Errors will manifest in methods if driver load fails
        {

            Class.forName(driver);

        }
        catch(ClassNotFoundException ex)
        {

            //logger.log(Level.SEVERE, ex.getMessage(), ex);
            return;

        }

        mUsersAccess = new UserDAO();
        mGameAccess = new GameDAO();
        mAuthAccess = new AuthDAO();

    }

    //Master Utility Methods

    /**
     *  <h1>Establish Connection</h1>
     *  Opens a new connection to the database.
     *
     *  @param          databaseName         The relative location for the database being accessed
     *
     *  @throws         SQLException
     */
    public void openConnection(String databaseName) throws SQLException
    {

        final String connection_URL = "jdbc:sqlite:" + databaseName;

        mDatabaseAccess = DriverManager.getConnection(connection_URL);

        mDatabaseAccess.setAutoCommit(false);

    }

    public Connection getConnection()
    {

        return mDatabaseAccess;

    }

    /**
     *  <h1>Close Connection</h1>
     *  Closes an open database connection and/or sets to null.
     *
     *  @param          commit          Whether or not to commit DB changes
     *
     *  @throws         SQLException
     */
    public void closeConnection(boolean commit) throws SQLException
    {
        if(commit)
        {

            mDatabaseAccess.commit();

        }
        else
        {

            mDatabaseAccess.rollback();

        }

        mDatabaseAccess.close();

        mDatabaseAccess = null;

    }

    /**
     *  Modify One Entry
     *  Provides a common method for all DAO to add or remove an entry to their respective database
     *  tables by passing in a specific SQL statement as a String along with a connection to use.
     *
     *  @param          currentConnection   The connection to use to access the database
     *  @param          sqlStatement                 The statement to process in the database
     *
     *  @throws          SQLException
     */
    private void modifyEntry(Connection currentConnection, String sqlStatement) throws SQLException
    {

        PreparedStatement stmt = null;

        try
        {
            stmt = currentConnection.prepareStatement(sqlStatement);

            //Verify single entry
            if(stmt.executeUpdate() != 1)
            {

                throw new SQLException();

            }

        }
        finally
        {

            if(stmt != null)
            {

                stmt.close();

            }

        }

    }

    /*
     *  Takes a SQL result set and parses each entry into a model object, which is added to an
     *  ArrayList to be returned.
     */
    private List<Object> parseResults(ResultSet found, String tableName) throws SQLException
    {

        //TODO:  Convert code to accommodate table name instead of table fields
        return null;

    }

    /**
     *  GetResults
     *  Shared method for DAO to query the database by passing in a SQL String and the number of
     *  fields unique to the table being queried, along with the connection to use.
     *
     *  @param          currentConnection   The connection to use to access the database
     *  @param          sqlStatement        The statement to process in the table
     *  @param          tableName           The table to access in the database
     *
     *  @return                             The objects found in the database which match the query
     *  @throws         SQLException
     */
    private List<Object> getResults(Connection currentConnection, String sqlStatement,
                                    String tableName) throws SQLException
    {

        PreparedStatement currentStatement = null;
        ResultSet foundObjects = null;
        List<Object> foundEntries = new ArrayList<>();

        try
        {

            currentStatement = currentConnection.prepareStatement(sqlStatement);
            foundObjects = currentStatement.executeQuery();
            foundEntries = parseResults(foundObjects, tableName);

        }
        finally
        {

            if(foundObjects != null)
            {

                foundObjects.close();

            }
            if(currentStatement != null)
            {

                currentStatement.close();

            }

        }

        return foundEntries;

    }

    //Master Access Methods

    public UserDAO getUserDAO()
    {
        return mUsersAccess;
    }

    public GameDAO getGameDAO()
    {
        return mGameAccess;
    }

    public AuthDAO getAuthDAO()
    {
        return mAuthAccess;
    }

    //Master Mutator Methods

    /**
     *  <h1>Clear</h1>
     *  Removes everything from the database.
     *
     *  @throws     SQLException
     */
    public void clear() throws SQLException
    {

        PreparedStatement stmt = null;

        try
        {

            String sql = "DELETE FROM user";
            stmt = mDatabaseAccess.prepareStatement(sql);
            stmt.executeUpdate();

            sql = "DELETE FROM game";
            stmt = mDatabaseAccess.prepareStatement(sql);
            stmt.executeUpdate();

            sql = "DELETE FROM players";
            stmt = mDatabaseAccess.prepareStatement(sql);
            stmt.executeUpdate();

            sql = "DELETE FROM auth";
            stmt = mDatabaseAccess.prepareStatement(sql);
            stmt.executeUpdate();

        }
        finally
        {

            if(stmt != null)
            {

                stmt.close();

            }

        }

    }

    //Nested DAO Classes

    /**
     *  <h1>User Data Access Class</h1>
     *  Provides methods to modify or exchange User data between the database and server.
     */
    class UserDAO
    {

        //Constructors

        UserDAO(){}

        //Access Methods

        /**
         *  <h1>Get</h1>
         *  Provides a way for the server facade to retrieve a User from the database.
         *
         *  @param      username            User to be retrieved from the database
         *  @param      currentConnection   Connection for accessing the database
         *
         *  @return                 U       ser object based on user table entry
         *
         *  @throws     SQLException
         */
        User get(String username, Connection currentConnection) throws SQLException
        {

            String sql = "SELECT * FROM user WHERE username= \'" + username + "\';";

            ArrayList<User> found = new ArrayList<>();

            for(Object o : getResults(currentConnection, sql, USER_TABLE_NAME))
            {
                //Verify User
                if(o instanceof User)
                {

                    found.add((User)o);

                }

            }

            if(found.size() == 0)
            {

                //Avoid null pointer
                return new User("", "");

            }
            else
            {

                return found.get(0);

            }

        }

        /**
         *  <h1>Exists</h1>
         *  Provides a way for the server facade to check if a User exists already in the database.
         *
         *  @param      username    The ID of the User being checked for
         *  @param      currentConnection   Connection for accessing the database
         *
         *  @return                 True if the ID exists or False otherwise
         *
         *  @throws     SQLException
         */
        boolean exists(String username, Connection currentConnection) throws SQLException
        {

            User temp = this.get(username, currentConnection);

            //Check for empty
            return !temp.getUsername().equals("");

        }

        //Mutator Methods

        /**
         *  <h1>Add</h1>
         *  Provides a way for the server facade to insert a new User into the database.
         *
         *  @param      newUser             User object to be added to the database
         *  @param      currentConnection   Connection for accessing the database
         *
         *  @throws     SQLException
         */
        void add(User newUser, Connection currentConnection) throws SQLException
        {

            String sql = "INSERT INTO user VALUES (\'" + newUser.getUsername() + "\', \'" +
                    newUser.getPassword() + "\');";

            modifyEntry(currentConnection, sql);

        }

        /**
         *  <h1>Remove</h1>
         *  Provides a way for the server facade to remove a specific User in the database.
         *
         *  @param      username            The ID of the User to be removed from the database
         *  @param      currentConnection   Connection for accessing the database
         *
         *  @throws     SQLException
         */
        void remove(String username, Connection currentConnection) throws SQLException
        {

            String sql = "DELETE FROM user WHERE username= \'" + username + "\';";

            modifyEntry(currentConnection, sql);

        }
    }

    class GameDAO
    {

        //TODO:  Add game table DAO, including removal of all players for a game that's removed

        //Constructors

        GameDAO(){}

    }

    /**
     *  <h1>Authentication Data Access Class</h1>
     *  Provides methods to modify or exchange authentication data between the database and server
     *  facade.
     */
    class AuthDAO
    {
        //Constructors

        AuthDAO(){}

        //Access Methods

        /**
         *  <h1>Get</h1>
         *  Provides a way for the server to get an authorization entry from the database.
         *
         *  @param      currentConnection    The current connection being used for database access
         *  @param      currentToken         The authorization token being looked for
         *
         *  @return                          AuthToken object created from the database entry; empty
         *                                   if nothing was found
         *  @throws     SQLException
         */
        AuthToken get(Connection currentConnection, String currentToken) throws SQLException
        {

            String sql = "SELECT * FROM auth WHERE auth_token= \'" + currentToken + "\';";

            ArrayList<AuthToken> found = new ArrayList<>();

            for(Object o : getResults(currentConnection, sql, AUTH_TABLE_NAME))
            {
                if(o instanceof AuthToken)
                {

                    //Verify Auth
                    found.add((AuthToken) o);

                }
            }
            if(found.size() == 0)
            {

                //Avoid null pointer
                return new AuthToken("");

            }
            else
            {

                return found.get(0);

            }

        }

        //Mutator Methods

        /**
         *  <h1>Add</h1>
         *  Provides a way for the server facade to track authentication for a user.
         *
         *  @param      currentConnection       The current connection being used for database access
         *  @param      newAuth                 The details for a user to be used for verifying
         *                                      credentials/ID
         *
         *
         *  @throws     SQLException
         */
        void add(Connection currentConnection, AuthToken newAuth) throws SQLException
        {

            String sql = "INSERT INTO auth VALUES (\'" + newAuth.getAuthToken() + "\', \'" +
                    newAuth.getUsername() + "\', \'" + newAuth.getExpires() + "\');";

            modifyEntry(currentConnection, sql);

        }

        /**
         *  <h1>Remove</h1>
         *  Provides a way for the server facade to remove an authentication entry from the
         *  database.
         *
         *  @param      currentConnection  The current connection being used for database access
         *  @param      currentToken       The token to be removed from the database
         *
         *  @throws     SQLException
         */
        void remove(Connection currentConnection, AuthToken currentToken) throws SQLException
        {

            String sql = "DELETE FROM auth WHERE auth_token= \'" + currentToken + "\';";

            modifyEntry(currentConnection, sql);

        }
    }

}
