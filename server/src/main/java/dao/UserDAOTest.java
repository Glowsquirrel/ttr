package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.*;
import java.sql.Connection;
import java.sql.SQLException;
import model.User;

import static org.junit.Assert.assertTrue;

/**
 *  <h1>User Table Test Class</h1>
 *  Provides tests for user table capabilities.
 *
 *  @author     Nathan Finch
 *  @since      7/12/2017
 */
public class UserDAOTest
{

    //Data

    private static final String DB_TO_USE = "server/db/tstdb.sqlite";
    private static final String TEST_USERNAME = "T0mJan3";
    private static final String TEST_PASSWORD = "password1";
    private static final String BAD_USERNAME = "game0verMan";

    private MasterDAO mTestTransaction;
    private MasterDAO.UserDAO mUserAccess;
    private Connection mTestConnection;
    
    private User mGoodUser;
    private User mBadUser;

    //Constructors

    public UserDAOTest()
    {

        mTestTransaction = null;
        mUserAccess = null;
        mTestConnection = null;
    
        mGoodUser = new User(TEST_USERNAME, TEST_PASSWORD);
        mBadUser = null;
        
    }
    
    //Utility Methods
    
    @Before
    public void setUp()
    {
        
        mTestTransaction = new MasterDAO();
        
        mUserAccess = mTestTransaction.getUserDAO();
        
        try
        {
            
            mTestTransaction.openConnection(DB_TO_USE);
            mTestTransaction.clear();
            mTestTransaction.closeConnection(true);
            mTestTransaction.openConnection(DB_TO_USE);
            
        }
        catch(SQLException ex)
        {
    
            ex.printStackTrace();
            
        }
        
        mTestConnection = mTestTransaction.getConnection();
        
    }

    @After
    public void tearDown()
    {
        
        try
        {
            
            if(mTestConnection != null && !mTestConnection.isClosed())
            {
                
                mTestTransaction.closeConnection(false);
                
            }
            
        }
        catch(SQLException ex)
        {
            
            System.out.println(ex.getMessage());
            
        }
        finally
        {
            
            mUserAccess = null;
            mTestTransaction = null;
            
        }
        
        mTestConnection = null;
        
    }
    
    //Testing Methods
    
    @Test
    public void testGoodAddGoodGet()
    {
    
        try
        {
            
            mUserAccess.add(mTestConnection, mGoodUser);
            
            mTestConnection.commit();
            
            User testUser = mUserAccess.get(mTestConnection, mGoodUser.getUsername());
            
            assertTrue(testUser.getUsername().equals(mGoodUser.getUsername()));
            assertTrue(testUser.getPassword().equals(mGoodUser.getPassword()));
            
        }
        catch(SQLException ex)
        {
    
            ex.printStackTrace();
            
        }
        
    }
    
    @Test
    public void testAddTwice()
    {
        
        try
        {
    
            mUserAccess.add(mTestConnection, mGoodUser);
    
            mTestConnection.commit();
            
            mUserAccess.add(mTestConnection, mGoodUser);
    
            mTestConnection.commit();
            
        }
        catch(SQLException ex)
        {
            
            assertTrue(ex.getMessage().contains("A PRIMARY KEY constraint failed"));
            
        }
        
    }
    
    @Test
    public void testBadAddGoodGet()
    {
        
        try
        {
            
            mUserAccess.add(mTestConnection, mBadUser);
            
            mTestConnection.commit();
    
            User testUser = mUserAccess.get(mTestConnection, mGoodUser.getUsername());
    
            assertTrue(testUser.getUsername().equals(""));
            assertTrue(testUser.getPassword().equals(""));
            
            
        }
        catch(SQLException ex)
        {
    
            ex.printStackTrace();
    
        }
        
    }
    
    @Test
    public void testGoodAddBadGet()
    {
    
        try
        {
        
            mUserAccess.add(mTestConnection, mGoodUser);
        
            mTestConnection.commit();
        
            User testUser = mUserAccess.get(mTestConnection, BAD_USERNAME);
        
            assertTrue(testUser.getUsername().equals(""));
            assertTrue(testUser.getPassword().equals(""));
        
        
        }
        catch(SQLException ex)
        {
        
            ex.printStackTrace();
        
        }
        
    }
    
    @Test
    public void testGoodRemoveGoodGet()
    {
    
        try
        {
        
            mUserAccess.add(mTestConnection, mGoodUser);
        
            mTestConnection.commit();
        
            User testUser = mUserAccess.get(mTestConnection, mGoodUser.getUsername());
        
            assertTrue(testUser.getUsername().equals(mGoodUser.getUsername()));
            assertTrue(testUser.getPassword().equals(mGoodUser.getPassword()));
            
            mUserAccess.remove(mTestConnection, mGoodUser.getUsername());
            
            mTestConnection.commit();
    
            testUser = mUserAccess.get(mTestConnection, mGoodUser.getUsername());
    
            assertTrue(testUser.getUsername().equals(""));
            assertTrue(testUser.getPassword().equals(""));
        
        }
        catch(SQLException ex)
        {
        
            ex.printStackTrace();
        
        }
        
    }
    
    @Test
    public void testBadRemoveGoodGet()
    {
    
        try
        {
        
            mUserAccess.add(mTestConnection, mGoodUser);
        
            mTestConnection.commit();
        
            User testUser = mUserAccess.get(mTestConnection, mGoodUser.getUsername());
        
            assertTrue(testUser.getUsername().equals(mGoodUser.getUsername()));
            assertTrue(testUser.getPassword().equals(mGoodUser.getPassword()));
        
            mUserAccess.remove(mTestConnection, BAD_USERNAME);
        
            mTestConnection.commit();
        
            testUser = mUserAccess.get(mTestConnection, mGoodUser.getUsername());
        
            assertTrue(testUser.getUsername().equals(mGoodUser.getUsername()));
            assertTrue(testUser.getPassword().equals(mGoodUser.getPassword()));
        
        }
        catch(SQLException ex)
        {
        
            ex.printStackTrace();
        
        }
        
    }
    
    @Test
    public void testGoodRemoveBadGet()
    {
        
        try
        {
            
            mUserAccess.add(mTestConnection, mGoodUser);
            
            mTestConnection.commit();
            
            User testUser = mUserAccess.get(mTestConnection, mGoodUser.getUsername());
            
            assertTrue(testUser.getUsername().equals(mGoodUser.getUsername()));
            assertTrue(testUser.getPassword().equals(mGoodUser.getPassword()));
            
            mUserAccess.remove(mTestConnection, mGoodUser.getUsername());
            
            mTestConnection.commit();
            
            testUser = mUserAccess.get(mTestConnection, BAD_USERNAME);
            
            assertTrue(testUser.getUsername().equals(""));
            assertTrue(testUser.getPassword().equals(""));
            
        }
        catch(SQLException ex)
        {
            
            ex.printStackTrace();
            
        }
        
    }

}
