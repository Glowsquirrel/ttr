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
    
    private ResultSet queryDB(PreparedStatement databaseToDo, String sql) throws SQLException {
        List<Object> foundEntries = new ArrayList<>();
        
        databaseToDo = mToDatabase.prepareStatement(sql);
        return databaseToDo.executeQuery();
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
            String sql = "DELETE FROM user";
            updateDB(databaseToDo, sql);
            
            sql = "DELETE FROM game";
            updateDB(databaseToDo, sql);
            
            sql = "DELETE FROM command";
            updateDB(databaseToDo, sql);
        } catch (SQLException ex) {
            return false;
        }
        finally {
            if(databaseToDo != null) {
                try {
                    databaseToDo.close();
                }
                catch (SQLException ex) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public boolean saveCommandToDatabase(String gameName, Command nextCommand) {
        final int INDEX_COLUMN = 1;
        String sql = "SELECT * FROM command WHERE game= \'" + gameName + "\';";
        PreparedStatement databaseToDo = null;
        ResultSet foundInDB = null;
    
        try {
            foundInDB = queryDB(databaseToDo, sql);
            foundInDB.last();
            int index = foundInDB.getInt(INDEX_COLUMN);
            if(index == mCommandsToKeep) {
                sql = "DELETE * FROM command WHERE game= \'" + gameName + "\';";
                updateDB(databaseToDo, sql);
                
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
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
