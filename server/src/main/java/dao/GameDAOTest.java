package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;
import model.Game;

/**
 *  <h1>Game Table Test Class</h1>
 *  Provides tests for game table capabilities.
 *
 *  @author     Nathan Finch
 *  @since      7/12/2017
 */
public class GameDAOTest
{

    //Data

    private static final String DB_TO_USE = "server/db/tstdb.sqlite";
    private static final String TEST_ID = "d3athMatch";
    private static final String BAD_ID = "n0Jok3";
    private static final boolean TEST_STATUS = false;
    private static final int TEST_NUMBER = 2;

    private MasterDAO mTestTransaction;
    private MasterDAO.GameDAO mGameAccess;
    private Connection mTestConnection;
    
    private Game mGoodGame;
    private Game mBadGame;
    
    //Constructors
    
    public GameDAOTest()
    {
        
        mTestTransaction = null;
        mGameAccess = null;
        mTestConnection = null;
        
        mGoodGame = new Game(TEST_ID, TEST_NUMBER, null, TEST_STATUS);
        mBadGame = null;
        
    }
    
    //Utility Methods
    
    @Before
    public void setUp()
    {
        
        mTestTransaction = new MasterDAO();
        
        mGameAccess = mTestTransaction.getGameDAO();
        
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
            
            mGameAccess = null;
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
        
            mGameAccess.add(mTestConnection, mGoodGame);
        
            mTestConnection.commit();
        
            Game testGame = mGameAccess.get(mTestConnection, mGoodGame.getID());
        
            assertTrue(testGame.getID().equals(mGoodGame.getID()));
            assertTrue(testGame.getNumberOfPlayers() == mGoodGame.getNumberOfPlayers());
            assertTrue(testGame.hasStarted() == mGoodGame.hasStarted());
        
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
        
            mGameAccess.add(mTestConnection, mGoodGame);
        
            mTestConnection.commit();
    
            mGameAccess.add(mTestConnection, mGoodGame);
        
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
        
            mGameAccess.add(mTestConnection, mBadGame);
        
            mTestConnection.commit();
        
            Game testGame = mGameAccess.get(mTestConnection, mGoodGame.getID());
        
            assertFalse(testGame.getID().equals(mGoodGame.getID()));
            assertFalse(testGame.getNumberOfPlayers() == mGoodGame.getNumberOfPlayers());
            assertTrue(testGame.hasStarted() == mGoodGame.hasStarted());
        
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
        
            mGameAccess.add(mTestConnection, mGoodGame);
        
            mTestConnection.commit();
        
            Game testGame = mGameAccess.get(mTestConnection, BAD_ID);
        
            assertFalse(testGame.getID().equals(mGoodGame.getID()));
            assertFalse(testGame.getNumberOfPlayers() == mGoodGame.getNumberOfPlayers());
            assertTrue(testGame.hasStarted() == mGoodGame.hasStarted());
        
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
        
            mGameAccess.add(mTestConnection, mGoodGame);
        
            mTestConnection.commit();
        
            Game testGame = mGameAccess.get(mTestConnection, mGoodGame.getID());
        
            assertTrue(testGame.getID().equals(mGoodGame.getID()));
            assertTrue(testGame.getNumberOfPlayers() == mGoodGame.getNumberOfPlayers());
            assertTrue(testGame.hasStarted() == mGoodGame.hasStarted());
            
            mGameAccess.remove(mTestConnection, mGoodGame.getID());
            
            mTestConnection.commit();
    
            testGame = mGameAccess.get(mTestConnection, mGoodGame.getID());
    
            assertFalse(testGame.getID().equals(mGoodGame.getID()));
            assertFalse(testGame.getNumberOfPlayers() == mGoodGame.getNumberOfPlayers());
            assertTrue(testGame.hasStarted() == mGoodGame.hasStarted());
        
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
        
            mGameAccess.add(mTestConnection, mGoodGame);
        
            mTestConnection.commit();
        
            Game testGame = mGameAccess.get(mTestConnection, mGoodGame.getID());
        
            assertTrue(testGame.getID().equals(mGoodGame.getID()));
            assertTrue(testGame.getNumberOfPlayers() == mGoodGame.getNumberOfPlayers());
            assertTrue(testGame.hasStarted() == mGoodGame.hasStarted());
        
            mGameAccess.remove(mTestConnection, BAD_ID);
        
            mTestConnection.commit();
        
            testGame = mGameAccess.get(mTestConnection, mGoodGame.getID());
        
            assertTrue(testGame.getID().equals(mGoodGame.getID()));
            assertTrue(testGame.getNumberOfPlayers() == mGoodGame.getNumberOfPlayers());
            assertTrue(testGame.hasStarted() == mGoodGame.hasStarted());
        
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
        
            mGameAccess.add(mTestConnection, mGoodGame);
        
            mTestConnection.commit();
        
            Game testGame = mGameAccess.get(mTestConnection, mGoodGame.getID());
        
            assertTrue(testGame.getID().equals(mGoodGame.getID()));
            assertTrue(testGame.getNumberOfPlayers() == mGoodGame.getNumberOfPlayers());
            assertTrue(testGame.hasStarted() == mGoodGame.hasStarted());
        
            mGameAccess.remove(mTestConnection, mGoodGame.getID());
        
            mTestConnection.commit();
        
            testGame = mGameAccess.get(mTestConnection, BAD_ID);
        
            assertFalse(testGame.getID().equals(mGoodGame.getID()));
            assertFalse(testGame.getNumberOfPlayers() == mGoodGame.getNumberOfPlayers());
            assertTrue(testGame.hasStarted() == mGoodGame.hasStarted());
        
        }
        catch(SQLException ex)
        {
        
            ex.printStackTrace();
        
        }
        
    }
    
    @Test
    public void testUpdateStatus()
    {
        
        try
        {
    
            mGameAccess.add(mTestConnection, mGoodGame);
    
            mTestConnection.commit();
    
            Game testGame = mGameAccess.get(mTestConnection, mGoodGame.getID());
    
            assertTrue(testGame.getID().equals(mGoodGame.getID()));
            assertTrue(testGame.getNumberOfPlayers() == mGoodGame.getNumberOfPlayers());
            assertTrue(testGame.hasStarted() == mGoodGame.hasStarted());
            
            mGameAccess.updateStatus(mTestConnection, mGoodGame.getID());
            
            mTestConnection.commit();
    
            testGame = mGameAccess.get(mTestConnection, mGoodGame.getID());
    
            assertTrue(testGame.getID().equals(mGoodGame.getID()));
            assertTrue(testGame.getNumberOfPlayers() == mGoodGame.getNumberOfPlayers());
            assertFalse(testGame.hasStarted() == mGoodGame.hasStarted());
            
        }
        catch(SQLException ex)
        {
            
            ex.printStackTrace();
            
        }
        
    }
    
}
