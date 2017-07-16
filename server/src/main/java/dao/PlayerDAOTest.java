package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.Game;
import model.User;

/**
 *  <h1>Player Table Test Class</h1>
 *  Provides tests for player table capabilities.
 *
 *  @author     Nathan Finch
 *  @since      7/12/2017
 */
public class PlayerDAOTest
{

    //Data

    private static final String DB_TO_USE = "server/db/tstdb.sqlite";
    private static final String TEST_USERNAME_1 = "G0b";
    private static final String TEST_USERNAME_2 = "M1chael";
    private static final String TEST_ID_1 = "Tr0n";
    private static final String TEST_ID_2 = "1egacy";
    private static final boolean TEST_STATUS = false;
    private static final int TEST_NUMBER = 2;

    private MasterDAO mTestTransaction;
    private MasterDAO.UserDAO mUserAccess;
    private MasterDAO.GameDAO mGameAccess;
    private MasterDAO.PlayerDAO mPlayerAccess;
    private Connection mTestConnection;
    
    private User mUser1;
    private User mUser2;
    private Game mGame1;
    private Game mGame2;
    
    //Constructors
    
    public PlayerDAOTest()
    {
        
        mTestTransaction = null;
        mPlayerAccess = null;
        mTestConnection = null;
        
        mUser1 = new User(TEST_USERNAME_1, TEST_USERNAME_2);
        mUser2 = new User(TEST_USERNAME_2, TEST_USERNAME_1);
        
        mGame1 = new Game(TEST_ID_1, TEST_NUMBER, null, TEST_STATUS);
        mGame2 = new Game(TEST_ID_2, TEST_NUMBER, null, TEST_STATUS);
        
    }
    
    //Utility Methods
    
    @Before
    public void setUp()
    {
        
        mTestTransaction = new MasterDAO();
    
        mUserAccess = mTestTransaction.getUserDAO();
        mGameAccess = mTestTransaction.getGameDAO();
        mPlayerAccess = mTestTransaction.getPlayerDAO();
        
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
            
            mPlayerAccess = null;
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
    
            mUserAccess.add(mTestConnection, mUser1);
            mUserAccess.add(mTestConnection, mUser2);
            mGameAccess.add(mTestConnection, mGame1);
            mGameAccess.add(mTestConnection, mGame2);
            
            mTestConnection.commit();
            
            mPlayerAccess.add(mTestConnection, TEST_USERNAME_1, TEST_ID_1);
            mPlayerAccess.add(mTestConnection, TEST_USERNAME_2, TEST_ID_2);
            
            mTestConnection.commit();
    
            List<User> testList = mPlayerAccess.getPlayers(mTestConnection, TEST_ID_1);
            
            assertTrue(testList.get(0).getUsername().equals(mUser1.getUsername()));
            assertTrue(testList.get(0).getPassword().equals(mUser1.getPassword()));
            
            testList = mPlayerAccess.getPlayers(mTestConnection, TEST_ID_2);
    
            assertTrue(testList.get(0).getUsername().equals(mUser2.getUsername()));
            assertTrue(testList.get(0).getPassword().equals(mUser2.getPassword()));
            
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
    
            mUserAccess.add(mTestConnection, mUser1);
            mGameAccess.add(mTestConnection, mGame1);
            mGameAccess.add(mTestConnection, mGame2);
    
            mTestConnection.commit();
    
            mPlayerAccess.add(mTestConnection, TEST_USERNAME_1, TEST_ID_1);
            
            mTestConnection.commit();
            
            mPlayerAccess.add(mTestConnection, TEST_USERNAME_1, TEST_ID_2);
    
            mTestConnection.commit();
    
            List<User> testList = mPlayerAccess.getPlayers(mTestConnection, TEST_ID_1);
    
            assertTrue(testList.get(0).getUsername().equals(mUser1.getUsername()));
            assertTrue(testList.get(0).getPassword().equals(mUser1.getPassword()));
    
            testList = mPlayerAccess.getPlayers(mTestConnection, TEST_ID_2);
    
            assertTrue(testList.get(0).getUsername().equals(mUser1.getUsername()));
            assertTrue(testList.get(0).getPassword().equals(mUser1.getPassword()));
            
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
        
            mUserAccess.add(mTestConnection, mUser1);
            mGameAccess.add(mTestConnection, mGame1);
        
            mTestConnection.commit();
        
            mPlayerAccess.add(mTestConnection, TEST_USERNAME_1, TEST_ID_1);
        
            mTestConnection.commit();
        
            List<User> testList = mPlayerAccess.getPlayers(mTestConnection, TEST_ID_2);
        
            assertTrue(testList.size() == 0);
        
        }
        catch(SQLException ex)
        {
        
            ex.printStackTrace();
        
        }
        
    }
    
    @Test
    public void testGoodRemoveGoodExists()
    {
    
        try
        {
            
            mUserAccess.add(mTestConnection, mUser2);
            mGameAccess.add(mTestConnection, mGame2);
        
            mTestConnection.commit();
            
            mPlayerAccess.add(mTestConnection, TEST_USERNAME_2, TEST_ID_2);
        
            mTestConnection.commit();
        
            assertTrue(mPlayerAccess.exists(mTestConnection, TEST_USERNAME_2, TEST_ID_2));
        
            mPlayerAccess.remove(mTestConnection, TEST_USERNAME_2, TEST_ID_2);
            
            assertFalse(mPlayerAccess.exists(mTestConnection, TEST_USERNAME_2, TEST_ID_2));
        
        }
        catch(SQLException ex)
        {
        
            ex.printStackTrace();
        
        }
        
    }
    
    @Test
    public void testBadRemoveGoodExists()
    {
    
        try
        {
        
            mUserAccess.add(mTestConnection, mUser2);
            mGameAccess.add(mTestConnection, mGame2);
        
            mTestConnection.commit();
        
            mPlayerAccess.add(mTestConnection, TEST_USERNAME_2, TEST_ID_2);
        
            mTestConnection.commit();
        
            assertTrue(mPlayerAccess.exists(mTestConnection, TEST_USERNAME_2, TEST_ID_2));
        
            mPlayerAccess.remove(mTestConnection, TEST_USERNAME_1, TEST_ID_1);
        
            assertTrue(mPlayerAccess.exists(mTestConnection, TEST_USERNAME_2, TEST_ID_2));
        
        }
        catch(SQLException ex)
        {
        
            ex.printStackTrace();
        
        }
        
    }

    @Test
    public void testGoodRemoveBadExists()
    {
    
        try
        {
        
            mUserAccess.add(mTestConnection, mUser1);
            mGameAccess.add(mTestConnection, mGame1);
        
            mTestConnection.commit();
        
            mPlayerAccess.add(mTestConnection, TEST_USERNAME_1, TEST_ID_1);
        
            mTestConnection.commit();
        
            assertTrue(mPlayerAccess.exists(mTestConnection, TEST_USERNAME_1, TEST_ID_1));
        
            mPlayerAccess.remove(mTestConnection, TEST_USERNAME_1, TEST_ID_1);
        
            assertFalse(mPlayerAccess.exists(mTestConnection, TEST_USERNAME_2, TEST_ID_2));
        
        }
        catch(SQLException ex)
        {
        
            ex.printStackTrace();
        
        }
        
    }
    
}
