package database.RelationalDatabase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
    private PreparedStatement mDatabaseToDo;
    
    public RDBDAO() {
        mToDatabase = null;
        final String driver = "org.sqlite.JDBC";
        mURLPostfix = "ttr-rdb.sqlite";
        
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        mCommandsToKeep = 0;
        mDatabaseToDo = null;
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
    
    private void updateDB(String sql) throws SQLException {
        mDatabaseToDo = mToDatabase.prepareStatement(sql);
        mDatabaseToDo.executeUpdate();
    }
    
    private void updateDB(String sql, String gameName, int index, Object toBlob)
                            throws SQLException {
        final int NAME_COLUMN = 0;
        final int INDEX_COLUMN = 1;
        final int CMD_COLUMN = 2;
    
        mDatabaseToDo = mToDatabase.prepareStatement(sql);
        mDatabaseToDo.setString(NAME_COLUMN, gameName);
        mDatabaseToDo.setInt(INDEX_COLUMN, index);
        mDatabaseToDo.setObject(CMD_COLUMN, toBlob);
        mDatabaseToDo.executeUpdate();
    }
    
    private void updateDB(String sql, String gameName, Object toBlob) throws SQLException {
        final int NAME_COLUMN = 0;
        final int GAME_COLUMN = 1;
    
        mDatabaseToDo = mToDatabase.prepareStatement(sql);
        mDatabaseToDo.setString(NAME_COLUMN, gameName);
        mDatabaseToDo.setObject(GAME_COLUMN, toBlob);
        mDatabaseToDo.executeUpdate();
    }
    
    private ResultSet queryDB(String sql) throws SQLException {
        List<Object> foundEntries = new ArrayList<>();
    
        mDatabaseToDo = mToDatabase.prepareStatement(sql);
        return mDatabaseToDo.executeQuery();
    }
    
    private boolean cleanUp() {
        try {
            if(mDatabaseToDo != null) {
                mDatabaseToDo.close();
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
        try {
            openConnection();
            String sql = "DELETE FROM user";
            updateDB(sql);
            
            sql = "DELETE FROM game";
            updateDB(sql);
            
            sql = "DELETE FROM command";
            updateDB(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        finally {
                return cleanUp();
        }
    }
    
    @Override
    public boolean saveCommandToDatabase(String gameName, Command nextCommand) {
        final int INDEX_COLUMN = 1;
        
        String sql = "SELECT * FROM command WHERE game= \'" + gameName + "\' ORDER BY cmd_index;";
        ResultSet foundInDB = null;
    
        try {
            openConnection();
            foundInDB = queryDB(sql);
            
            //Move to the last result and check its index
            foundInDB.last();
            int index = foundInDB.getInt(INDEX_COLUMN);
            if(index + 1 == mCommandsToKeep) {
                //Flush the commands for this game and tell the server to send the game state
                sql = "DELETE * FROM command WHERE game= \'" + gameName + "\';";
                updateDB(sql);
                return true;
            } else {
                sql = "INSERT INTO command(game, cmd_index, command) VALUES(?, ?, ?);";
                updateDB(sql, gameName, index + 1, nextCommand);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cleanUp();
        }
    
    return false;
    }
    
    @Override
    public void saveNewStartedGameToDatabase(StartedGame myGame) {
        try {
            openConnection();
    
            String sql = "INSERT INTO game(ID, state) VALUES(?, ?);";
            updateDB(sql, myGame.getGameName(), myGame);
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            cleanUp();
        }
    }
    
    @Override
    public void updateStartedGameInDatabase(StartedGame myGame) {
        try {
            openConnection();
            
            String sql = "UPDATE game SET state= ? WHERE game= ?;";
            updateDB(sql, myGame.getGameName(), myGame);
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            cleanUp();
        }
    }
    
    @Override
    public void saveNewUserToDatabase(User myNewUser) {
        try {
            openConnection();
        
            String sql = "INSERT INTO user VALUES(" + myNewUser.getUsername() + ", "
                            + myNewUser.getPassword() + ");";
            updateDB(sql);
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            cleanUp();
        }
    }
    
    @Override
    public Set<User> loadUsersFromDatabase() {
        final int USERNAME_COLUMN = 0;
        final int PASSWORD_COLUMN = 1;
        Set<User> allUsers = new HashSet<>();
        ResultSet foundInDB = null;
        
        try {
            openConnection();
            
            String sql = "SELECT * FROM user;";
            foundInDB = queryDB(sql);
            while(foundInDB.next()) {
                String username = foundInDB.getString(USERNAME_COLUMN);
                String password = foundInDB.getString(PASSWORD_COLUMN);
                
                allUsers.add(new User(username, password));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                foundInDB.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
            cleanUp();
        }
        
        return allUsers;
    }
    
    @Override
    public Map<String, StartedGame> loadStartedGamesFromDatabase() {
        final int STATE_COLUMN = 1;
        Map<String, StartedGame> allGames = new HashMap<>();
        ResultSet foundInDB = null;
    
        try {
            openConnection();
        
            String sql = "SELECT * FROM game;";
            foundInDB = queryDB(sql);
            while(foundInDB.next()) {
                byte[] buffer = foundInDB.getBytes(STATE_COLUMN);
                ObjectInputStream foundObject = null;
            
                if(buffer != null) {
                    foundObject = new ObjectInputStream(new ByteArrayInputStream(buffer));
                }
            
                Object deserializedObject = foundObject.readObject();
                if(deserializedObject instanceof StartedGame) {
                    StartedGame deserializedGame = (StartedGame)deserializedObject;
                    allGames.put(deserializedGame.getGameName(), deserializedGame);
                }
            }
        } catch(SQLException | IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                foundInDB.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
            cleanUp();
        }
        
        return allGames;
    }
    
    @Override
    public Map<String, List<Command>> loadOutstandingCommandsFromDatabase() {
        final int GAME_COLUMN = 0;
        final int CMD_COLUMN = 2;
        Map<String, List<Command>> allCommands = new HashMap<>();
        ResultSet foundInDB = null;
    
        try {
            openConnection();
        
            String sql = "SELECT * FROM command;";
            foundInDB = queryDB(sql);
            while(foundInDB.next()) {
                String gameName = foundInDB.getString(GAME_COLUMN);
                byte[] buffer = foundInDB.getBytes(CMD_COLUMN);
                ObjectInputStream foundObject = null;
            
                if(buffer != null) {
                    foundObject = new ObjectInputStream(new ByteArrayInputStream(buffer));
                }
            
                Object deserializedObject = foundObject.readObject();
                if(deserializedObject instanceof Command) {
                    Command deserializedCmd = (Command)deserializedObject;
                    if(!allCommands.containsKey(gameName)) {
                        allCommands.put(gameName, new ArrayList<Command>());
                    }
                    allCommands.get(gameName).add(deserializedCmd);
                }
            }
        } catch(SQLException | IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                foundInDB.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
            cleanUp();
        }
    
        return allCommands;
    }
}
