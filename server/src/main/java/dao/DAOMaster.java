package dao;

import java.sql.*;

/**
 * A class that manages the other DAO classes by using the same connection and retrieving information from multiple tables with multiple SQL statements.
 */
public class DAOMaster {

    static{ //loads the database driver and database name at start
        try{
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }catch (ClassNotFoundException ex){
            System.out.println("FAILED TO LOAD DRIVER");
            //could not load database driver
        }
    }

    /**
     * A database connection to be used for all operations from a single request.
     */
    private Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    /**
     * Initializes a new connection to the family map database when creating a DAOMaster object.
     */
    public DAOMaster(){ //when preparing to do DAO operations, create a connection that they can all use
        try{
            //TODO create database file
            String dbName = "???";
            String connectionURL = "jdbc:sqlite:" + dbName;
            connection = DriverManager.getConnection(connectionURL);
            connection.setAutoCommit(false);
        }catch (SQLException ex){
            System.out.println("SQL exception. Did you find the file?");
            //connection failed
        }
    }

    public boolean finishTransactionAndCloseConnection(boolean keepChanges){
        try {
            if (!connection.isClosed()) {
                if (keepChanges)
                    connection.commit();
                else
                    connection.rollback();
                connection.close();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("FAILED TO CLOSE CONNECTION");
            return false;
        }
        return true;
    }

    /**
     * Creates a new UserDAO object with the master's database connection.
     * @return UserDAO
     */
    public UserDAO createUserDAO(){
        return new UserDAO(connection);
    }

    /**
     * Creates a new AuthTokenDAO object with the master's database connection.
     * @return AuthTokenDAO
     */
    public AuthTokenDAO createAuthTokenDAO(){
        return new AuthTokenDAO(connection);
    }

    public boolean clearAll(){

        return true;
    }

    public boolean clearTable(String tableName){
        //System.out.println("clearing table: " + tableName);
        try{
            PreparedStatement stmt;
            String sql = "DELETE FROM " + tableName;
            stmt = connection.prepareStatement(sql);
            stmt.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
            System.out.printf("Failed to clear %s table%n", tableName);
            return false;
        }
        return true;
    }
}
