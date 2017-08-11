package database.RelationalDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import commands.Command;
import database.IDatabase;
import model.StartedGame;
import model.User;

/**
 *  <h1>RDBDAO</h1>
 *  Data access object for the relational database
 *
 *  @author     Nathan Finch
 *  @since      08.10.2017
 */
public class RDBDAO implements IDatabase {
    private static final String URL_PREFIX = "jdbc:sqlite:";
    
    private String mURLPostfix;
    private int mCommandsToKeep;
    private Connection mToDatabase;
    
    public RDBDAO() {
        mToDatabase = null;
        final String driver = "org.sqlite.JDBC";
        mURLPostfix = "ttr-rdb.sqlite";
        
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    public RDBDAO(int commandLimit) {
        this();
        mCommandsToKeep = commandLimit;
    }
    public RDBDAO(int commandLimit, String dbLocation) {
        this(commandLimit);
        mURLPostfix = dbLocation;
    }
    
    private void openConnection() throws SQLException {
        mToDatabase = DriverManager.getConnection(URL_PREFIX + mURLPostfix);
        mToDatabase.setAutoCommit(false);
    }
    
    private void closeConnection(boolean commit) throws SQLException {
        if(commit) {
            mToDatabase.commit();
        } else {
            mToDatabase.rollback();
        }
        mToDatabase = null;
    }
    
    private void updateDB(PreparedStatement databaseToDo, String sql) throws SQLException {
        databaseToDo = mToDatabase.prepareStatement(sql);
        databaseToDo.executeUpdate();
    }
    
    private void updateDB(PreparedStatement databaseToDo, String sql, String gameName, int index,
                          Object toBlob) throws SQLException {
        final int NAME_COLUMN = 0;
        final int INDEX_COLUMN = 1;
        final int CMD_COLUMN = 2;
        
        databaseToDo = mToDatabase.prepareStatement(sql);
        databaseToDo.setString(NAME_COLUMN, gameName);
        databaseToDo.setInt(INDEX_COLUMN, index);
        databaseToDo.setObject(CMD_COLUMN, toBlob);
        databaseToDo.executeUpdate();
    }
    
    private ResultSet queryDB(PreparedStatement databaseToDo, String sql) throws SQLException {
        List<Object> foundEntries = new ArrayList<>();
        
        databaseToDo = mToDatabase.prepareStatement(sql);
        return databaseToDo.executeQuery();
    }
    
    private boolean cleanUp(PreparedStatement databaseToDo) {
        try {
            if(databaseToDo != null) {
                databaseToDo.close();
            }
            if(!mToDatabase.isClosed()) {
                closeConnection(true);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     *  <h1>clearDatabase</h1>
     *  Clears the database tables
     *
     *  @return     True if successful or false if there was an error
     *
     *  @pre        none
     *  @post       The database's tables will be empty
     *  @post       The database schema will be unchanged
     */
    @Override
    public boolean clearDatabase() {
        PreparedStatement databaseToDo = null;
        
        try {
            openConnection();
            String sql = "DELETE FROM user";
            updateDB(databaseToDo, sql);
            
            sql = "DELETE FROM game";
            updateDB(databaseToDo, sql);
            
            sql = "DELETE FROM command";
            updateDB(databaseToDo, sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        finally {
                return cleanUp(databaseToDo);
        }
    }
    
    @Override
    public boolean saveCommandToDatabase(String gameName, Command nextCommand) {
        final int INDEX_COLUMN = 1;
        
        String sql = "SELECT * FROM command WHERE game= \'" + gameName + "\' ORDER BY cmd_index;";
        PreparedStatement databaseToDo = null;
        ResultSet foundInDB = null;
    
        try {
            openConnection();
            foundInDB = queryDB(databaseToDo, sql);
            
            //Move to the last result and check its index
            foundInDB.last();
            int index = foundInDB.getInt(INDEX_COLUMN);
            if(index + 1 == mCommandsToKeep) {
                //Flush the commands for this game and tell the server to send the game state
                sql = "DELETE * FROM command WHERE game= \'" + gameName + "\';";
                updateDB(databaseToDo, sql);
                return true;
            } else {
                sql = "INSERT INTO command(game, cmd_index, command) VALUES(?, ?, ?);";
                updateDB(databaseToDo, sql, gameName, index + 1, nextCommand);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cleanUp(databaseToDo);
        }
    
    return false;
    }
    
    @Override
    public void saveNewStartedGameToDatabase(StartedGame myGame) {
        
    }
    
    @Override
    public void updateStartedGameInDatabase(StartedGame myGame) {
        
    }
    
    @Override
    public void saveNewUserToDatabase(User myNewUser) {
        
    }
    
    @Override
    public Set<User> loadUsersFromDatabase() {
        return null;
    }
    
    @Override
    public Map<String, StartedGame> loadStartedGamesFromDatabase() {
        return null;
    }
    
    @Override
    public Map<String, List<Command>> loadOutstandingCommandsFromDatabase() {
        return null;
    }
}
