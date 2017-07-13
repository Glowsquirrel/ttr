package dao;

import org.sqlite.core.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    //Data Members

    private static final String DB_TO_USE = "server/db/ttrdb.sqlite";

    private static final String USER_TABLE_NAME = "user";
    private static final String GAME_TABLE_NAME = "game";
    private static final String PLAYERS_TABLE_NAME = "players";

    private static Logger mLogger;

    static
    {

        mLogger = Logger.getLogger("serverlog");

    }

    private Connection mDatabaseAccess;
    private UserDAO mUsersAccess;
    private GameDAO mGameAccess;
    private PlayerDAO mPlayersAccess;

    //Constructors

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

            mLogger.log(Level.SEVERE, ex.getMessage(), ex);
            return;

        }

        mUsersAccess = new UserDAO();
        mGameAccess = new GameDAO();
        mPlayersAccess = new PlayerDAO();

    }

    //Master Access Methods
    
    UserDAO getUserDAO()
    {
        
        return mUsersAccess;
        
    }
    
    GameDAO getGameDAO()
    {
        
        return mGameAccess;
        
    }
    
    PlayerDAO getPlayerDAO()
    {
        
        return mPlayersAccess;
        
    }

    public boolean login(String username, String password) throws SQLException
    {

        User currentUser;

        boolean success = false;

        try
        {

            this.openConnection(DB_TO_USE);

            User prospectiveLogin = mUsersAccess.get(mDatabaseAccess, username);

            if(!prospectiveLogin.getUsername().equals(""))
            {

                currentUser = mUsersAccess.get(mDatabaseAccess, username);

                success = true;

            }
            else
            {

                throw new SQLException("Invalid username.");

            }

        }
        finally
        {

            this.closeConnection(success);

        }

        return !currentUser.getUsername().equals("") && currentUser.getPassword().equals(password);

    }

    public List<Game> getOpenGames() throws SQLException
    {

        List<Game> openGames;

        boolean success = false;

        try
        {

            this.openConnection(DB_TO_USE);

            openGames = mGameAccess.getGames(mDatabaseAccess);
            
            for(Game openGame : openGames)
            {
                
                openGame.setPlayers(mPlayersAccess.getPlayers(mDatabaseAccess, openGame.getID()));
                
            }

            success = true;

        }
        finally
        {

            this.closeConnection(success);

        }

        return openGames;

    }

    //Master Mutator Methods

    public boolean register(String username, String password) throws SQLException
    {

        boolean success = false;

        try
        {

            this.openConnection(DB_TO_USE);

            User prospectiveUser = mUsersAccess.get(mDatabaseAccess, username);

            //Check if the user is already registered
            if(prospectiveUser.getUsername().equals(""))
            {

                mUsersAccess.add(mDatabaseAccess, new User(username, password));

                success = true;

                return true;

            }
            else
            {

                throw new SQLException("User already exists.");

            }

        }
        finally
        {

            this.closeConnection(success);

        }


    }

    public void createGame(String creator, String gameID, int numberOfPlayers) throws SQLException
    {

        Game newGame = new Game(gameID, numberOfPlayers, null, false);

        boolean success = false;

        try
        {

            this.openConnection(DB_TO_USE);

            Game prospectiveGame = mGameAccess.get(mDatabaseAccess, gameID);

            if(prospectiveGame.getID().equals(""))
            {

                mGameAccess.add(mDatabaseAccess, newGame);

                mPlayersAccess.add(mDatabaseAccess, creator, gameID);

                success = true;

            }
            else
            {

                throw new SQLException("Game already exists.");

            }

        }
        finally
        {

            this.closeConnection(success);

        }

    }

    public void joinGame(String username, String gameID) throws SQLException
    {

        boolean success = false;

        try
        {

            this.openConnection(DB_TO_USE);

            Game prospectiveGame = mGameAccess.get(mDatabaseAccess, gameID);
            List<User> players = mPlayersAccess.getPlayers(mDatabaseAccess, gameID);

            //Check to ensure game exists and there are spots open
            if(!prospectiveGame.getID().equals("") && players.size() <
                    prospectiveGame.getNumberOfPlayers() && !mPlayersAccess.exists(mDatabaseAccess,
                                                                                    username,
                                                                                    gameID))
            {

                mPlayersAccess.add(mDatabaseAccess, username, gameID);
                success = true;

            }
            else
            {

                throw new SQLException("Cannot join game.");

            }


        }
        finally
        {

            this.closeConnection(success);

        }

    }

    public void leaveGame(String username, String gameID) throws SQLException
    {

        boolean success = false;

        try
        {

            this.openConnection(DB_TO_USE);

            mPlayersAccess.remove(mDatabaseAccess, username, gameID);

            List<User> players = mPlayersAccess.getPlayers(mDatabaseAccess, gameID);

            if(players.size() == 1)
            {

                mGameAccess.remove(mDatabaseAccess, gameID);

            }

            success = true;

        }
        finally
        {

            this.closeConnection(success);

        }

    }

    public void startGame(String gameID) throws SQLException
    {

        boolean success = false;

        try
        {

            this.openConnection(DB_TO_USE);

            mGameAccess.updateStatus(mDatabaseAccess, gameID);

            success = true;

        }
        finally
        {

            this.closeConnection(success);

        }

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

        final String connectionURL = "jdbc:sqlite:" + databaseName;

        mDatabaseAccess = DriverManager.getConnection(connectionURL);

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
            if(stmt.executeUpdate() > 1)
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

    /**
     *  Parse Results
     *  Takes a SQL result set and parses each entry into a model object, which is added to an
     *  List to be returned.
     *
     *  @param          found           The result set of found entries from the database
     *  @param          tableName       The type of object in the set
     *
     *  @return                         The list of parsed objects
     *  @throws         SQLException
     */
    private List<Object> parseResults(ResultSet found, String tableName) throws SQLException
    {

        List<Object> foundEntries = new ArrayList<>();

        while(found.next())
        {
            
            int fields = 2;
            if(tableName.equals(GAME_TABLE_NAME))
            {
                
                fields = 3;
                
            }

            //Container for table field data
            String[] objectAttributes = new String[fields];

            //Avoid null values
            Arrays.fill(objectAttributes, "");

            //Get the fields for each object that was found
            for(int i = 1; i <= objectAttributes.length; ++i)
            {

                objectAttributes[i - 1] = found.getString(i);

            }

            if(tableName.equals(USER_TABLE_NAME))
            {

                foundEntries.add(new User(objectAttributes[0], objectAttributes[1]));

            }
            else if(tableName.equals(GAME_TABLE_NAME))
            {

                int numberOfPlayers = Integer.parseInt(objectAttributes[1]);
                boolean started = objectAttributes[2].equals("1");

                foundEntries.add(new Game(objectAttributes[0], numberOfPlayers, null, started));

            }
            else
            {

                foundEntries.add(mUsersAccess.get(mDatabaseAccess, objectAttributes[0]));

            }

        }

        return foundEntries;

    }

    /**
     *  Get Results
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

        boolean success = false;

        try
        {

            this.openConnection(DB_TO_USE);

            String sql = "DELETE FROM user";
            stmt = mDatabaseAccess.prepareStatement(sql);
            stmt.executeUpdate();

            sql = "DELETE FROM game";
            stmt = mDatabaseAccess.prepareStatement(sql);
            stmt.executeUpdate();

            sql = "DELETE FROM players;";
            stmt = mDatabaseAccess.prepareStatement(sql);
            stmt.executeUpdate();

            success = true;

        }
        finally
        {

            if(stmt != null)
            {

                stmt.close();

            }
            if(!mDatabaseAccess.isClosed())
            {

                this.closeConnection(success);

            }

        }

    }

    //Nested DAO Classes

    /**
     *  <h1>User Data Access Class</h1>
     *  Provides methods to modify or exchange User data between the database and server facade.
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
         *  @param      currentConnection   Connection for accessing the database
         *  @param      username            User to be retrieved from the database
         *
         *  @return                 U       ser object based on user table entry
         *
         *  @throws     SQLException
         */
        User get(Connection currentConnection, String username) throws SQLException
        {

            String sql = "SELECT * FROM user WHERE username= \'" + username + "\';";

            List<User> found = new ArrayList<>();

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

        //Mutator Methods

        /**
         *  <h1>Add</h1>
         *  Provides a way for the server facade to insert a new User into the database.
         *
         *  @param      currentConnection   Connection for accessing the database
         *  @param      newUser             User object to be added to the database
         *
         *  @throws     SQLException
         */
        void add(Connection currentConnection, User newUser) throws SQLException
        {

            if(newUser != null)
            {

                String sql = "INSERT INTO user VALUES (\'" + newUser.getUsername() + "\', \'" +
                                     newUser.getPassword() + "\');";

                modifyEntry(currentConnection, sql);
                
            }

        }

        /**
         *  <h1>Remove</h1>
         *  Provides a way for the server facade to remove a specific User in the database.
         *
         *  @param      currentConnection   Connection for accessing the database
         *  @param      username            The ID of the User to be removed from the database
         *
         *  @throws     SQLException
         */
        void remove(Connection currentConnection, String username) throws SQLException
        {

            String sql = "DELETE FROM user WHERE username= \'" + username + "\';";

            modifyEntry(currentConnection, sql);

        }
        
    }

    /**
     *  <h1>Game Data Access Class</h1>
     *  Provides methods to modify or exchange Game data between the database and server facade.
     */
    class GameDAO
    {

        //Constructors

        GameDAO(){}

        //Access Methods

        /**
         *  <h1>Get</h1>
         *  Provides a way for the server facade to retrieve a Game from the database.
         *
         *  @param      currentConnection   Connection for accessing the database
         *  @param      ID                  Game to be retrieved from the database
         *
         *  @return                         Game object based on game table entry
         *
         *  @throws     SQLException
         */
        Game get(Connection currentConnection, String ID) throws SQLException
        {

            String gameSQL = "SELECT * FROM game WHERE ID= \'" + ID + "\';";

            String playerSQL = "SELECT * FROM players WHERE ID= \'" + ID + "\';";

            List<Game> foundGames = new ArrayList<>();

            List<User> foundPlayers = new ArrayList<>();

            for(Object foundGame : getResults(currentConnection, gameSQL, GAME_TABLE_NAME))
            {
                //Verify Game
                if(foundGame instanceof Game)
                {

                    //Cast to game type to be able to set players list
                    Game currentGame = (Game)foundGame;

                    //Get players
                    for(Object foundPlayer : getResults(currentConnection, playerSQL,
                            PLAYERS_TABLE_NAME))
                    {

                        //Verify User
                        if(foundPlayer instanceof User)
                        {

                            foundPlayers.add((User)foundPlayer);

                        }

                    }

                    currentGame.setPlayers(foundPlayers);

                    foundGames.add(currentGame);

                }

            }

            if(foundGames.size() == 0)
            {

                //Avoid null pointer
                return new Game("", 0, null, false);

            }
            else
            {

                return foundGames.get(0);

            }

        }

        List<Game> getGames(Connection currentConnection) throws SQLException
        {

            String sql = "SELECT * FROM game WHERE started= 0;";

            List<Game> foundGames = new ArrayList<>();

            for(Object foundGame : getResults(currentConnection, sql, GAME_TABLE_NAME))
            {
                //Verify Game
                if(foundGame instanceof Game)
                {

                    foundGames.add((Game)foundGame);

                }

            }

            return foundGames;

        }

        //Mutator Methods

        /**
         *  <h1>Add</h1>
         *  Provides a way for the server facade to insert a new Game into the database.
         *
         *  @param      currentConnection   Connection for accessing the database
         *  @param      newGame             Game object to be added to the database
         *
         *  @throws     SQLException
         */
        void add(Connection currentConnection, Game newGame) throws SQLException
        {

            //Don't try to create a game that's null or already in the database
            if(newGame == null || !this.get(currentConnection, newGame.getID()).getID().equals(""))
            {

                return;

            }

            int started = 0;

            if(newGame.hasStarted())
            {

                started = 1;

            }

            String sql = "INSERT INTO game VALUES (\'" + newGame.getID() + "\', \'" +
                    newGame.getNumberOfPlayers() + "\', \'" + started + "\');";

            modifyEntry(currentConnection, sql);

        }

        /**
         *  <h1>Remove</h1>
         *  Provides a way for the server facade to remove a specific Game in the database.
         *
         *  @param      currentConnection   Connection for accessing the database
         *  @param      ID                  The ID of the Game to be removed from the database
         *
         *  @throws     SQLException
         */
        void remove(Connection currentConnection, String ID) throws SQLException
        {

            String sql = "DELETE FROM game WHERE ID= \'" + ID + "\';";

            modifyEntry(currentConnection, sql);

        }

        void updateStatus(Connection currentConnection, String ID) throws SQLException
        {

            String sql = "UPDATE game SET started= 1 WHERE ID= (\'" + ID + "\');";

            modifyEntry(currentConnection, sql);

        }

    }

    /**
     *  <h1>Player Data Access Class</h1>
     *  Provides methods to modify or exchange player data between the database and server
     */
    class PlayerDAO
    {

        //Constructors

        PlayerDAO(){}

        //Access Methods

        /**
         *  <h1>Get Players</h1>
         *  Provides a way for the server facade to retrieve playersfrom the database
         *
         *  @param      currentConnection   Connection for accessing the database
         *  @param      gameID              User to be retrieved from the database
         *
         *  @return                         User objects based on players table entry
         *
         *  @throws     SQLException
         */
        List<User> getPlayers(Connection currentConnection, String gameID) throws SQLException
        {

            String sql = "SELECT * FROM players WHERE ID= \'" + gameID + "\';";

            List<User> found = new ArrayList<>();

            for(Object o : getResults(currentConnection, sql, PLAYERS_TABLE_NAME))
            {
                //Verify User
                if(o instanceof User)
                {

                    found.add((User)o);

                }

            }

            return found;

        }

        /**
         *  <h1>Exists</h1>
         *  Provides a way for the server facade to check if a player is part of a game
         *
         *  @param      currentConnection   Connection for accessing the database
         *  @param      username            The ID of the User being checked for
         *  @param      gameID              The game the player may be a part of
         *
         *  @return                 True if the ID exists or False otherwise
         *  @throws     SQLException
         */
        boolean exists(Connection currentConnection, String username, String gameID)
                throws SQLException
        {

            String sql = "SELECT * FROM players WHERE ID= \'" + gameID + "\' AND player= \'" +
                            username + "\';";

            List<User> found = new ArrayList<>();

            for(Object o : getResults(currentConnection, sql, PLAYERS_TABLE_NAME))
            {
                //Verify User
                if(o instanceof User)
                {

                    found.add((User)o);

                }

            }

            return found.size() == 1;

        }

        //Mutator Methods

        /**
         *  <h1>Add</h1>
         *  Provides a way for the server facade to insert a new player into the database.
         *
         *  @param      currentConnection   Connection for accessing the database
         *  @param      player              The username of the player in the game
         *  @param      gameID              The game the player is a part of
         *
         *  @throws     SQLException
         */
        void add(Connection currentConnection, String player, String gameID) throws SQLException
        {

            String sql = "INSERT INTO players VALUES (\'" + player + "\', \'" + gameID + "\');";

            modifyEntry(currentConnection, sql);

        }

        /**
         *  <h1>Remove</h1>
         *  Provides a way for the server facade to remove a specific player from a given game
         *  in the database.
         *
         *  @param      currentConnection   Connection for accessing the database
         *  @param      player              The username of the player in the game
         *  @param      gameID              The game the player is a part of
         *
         *  @throws     SQLException
         */
        void remove(Connection currentConnection, String player, String gameID) throws SQLException
        {

            String sql = "DELETE FROM players WHERE player= \'" + player + "\' AND ID= \'" +
                            gameID + "\';";

            modifyEntry(currentConnection, sql);

        }

    }

}
