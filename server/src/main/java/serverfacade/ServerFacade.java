package serverfacade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import commandresults.CommandResult;
import commandresults.LoginResult;
import commandresults.PollGamesResult;
import dao.MasterDAO;
import interfaces.IServer;
import model.Game;
import model.UnstartedGame;
import model.User;

public class ServerFacade implements IServer
{
    
    //Data Members
    
    private static final String CLEAR_TYPE = "cleardb";
    private static final String REGISTER_TYPE = "register";
    private static final String CREATE_TYPE = "creategame";
    private static final String JOIN_TYPE = "joingame";
    private static final String LEAVE_TYPE = "leavegame";
    private static final String START_TYPE = "startgame";
    
    private MasterDAO mDatabaseAccess;
    
    //Constructors

    public ServerFacade()
    {
        
        mDatabaseAccess = new MasterDAO();
        
    }
    
    //Utility Methods
    
    public CommandResult clearDatabase()
    {
        
        boolean success = false;
        String message = "";
        
        try
        {
    
            mDatabaseAccess.clear();
            success = true;
            
        }
        catch(SQLException ex)
        {
    
            message = ex.getMessage();
            
        }
        
        return new CommandResult(CLEAR_TYPE, success, message);
        
    }
    
    private UnstartedGame convertGame(Game fromDatabase)
    {
    
        UnstartedGame convertedGame = new UnstartedGame();
    
        convertedGame.setName(fromDatabase.getID());
    
        List<String> names = new ArrayList<>();
    
        for(User player : fromDatabase.getPlayers())
        {

            names.add(player.getUsername());
        
        }
    
        convertedGame.setUsernames(names);
    
        convertedGame.setPlayersIn(names.size());
    
        convertedGame.setPlayersNeeded(fromDatabase.getNumberOfPlayers());
    
        return convertedGame;
        
    }
    
    //Access Methods

    @Override
    public LoginResult login(String username, String password)
    {
        
        boolean success = false;
        String message = "Success.";
        
        try
        {
    
            success = mDatabaseAccess.login(username, password);
            if(!success)
            {

                message = "Invalid password.";

            }
            
        }
        catch(SQLException ex)
        {
            
            message = ex.getMessage();
            
        }
        
        return new LoginResult(success, username, message);
        
    }
    

    @Override
    public PollGamesResult pollGameList(String username)
    {

        boolean success = false;
        List<Game> openGames = null;
        String message = "";
        
        try
        {
    
            openGames = mDatabaseAccess.getOpenGames();
            success = true;
            
        }
        catch(SQLException ex)
        {
            
            message = ex.getMessage();
            openGames = new ArrayList<>();
            
        }
        
        List<UnstartedGame> unstartedGames = new ArrayList<>();
        
        for(Game openGame : openGames)
        {
            
            unstartedGames.add(convertGame(openGame));
            
        }
        
        return new PollGamesResult(success, message, unstartedGames);
    }
    
    //Mutator Methods
    
    @Override
    public CommandResult register(String username, String password)
    {
        
        boolean success = false;
        String message = "Success.";
        
        try
        {
            
            success = mDatabaseAccess.register(username, password);

            if(!success)
            {

                message = "Invalid password.";

            }
            
        }
        catch(SQLException ex)
        {
            
            message = ex.getMessage();
            
        }
        
        return new CommandResult(REGISTER_TYPE, success, message);
    }

    @Override
    public CommandResult createGame(String username, String gameName, int playerNum)
    {
        
        boolean success = false;
        String message = "Game created.";
        
        try
        {
            
            mDatabaseAccess.createGame(username, gameName, playerNum);
            success = true;
            
        }
        catch(SQLException ex)
        {
            
            message = ex.getMessage();
            
        }
        
        return new CommandResult(CREATE_TYPE, success, message);
        
    }

    @Override
    public CommandResult joinGame(String username, String gameName)
    {
        
        boolean success = false;
        String message = "Game joined.";
        
        try
        {
            
            mDatabaseAccess.joinGame(username, gameName);
            success = true;
            
        }
        catch(SQLException ex)
        {
            
            message = ex.getMessage();
            
        }
        
        return new CommandResult(JOIN_TYPE, success, message);
        
    }

    @Override
    public CommandResult leaveGame(String username, String gameName)
    {
        
        boolean success = false;
        String message = "Left game.";
        
        try
        {
            
            mDatabaseAccess.leaveGame(username, gameName);
            success = true;
            
        }
        catch(SQLException ex)
        {
            
            message = ex.getMessage();
            
        }
        
        return new CommandResult(LEAVE_TYPE, success, message);
        
    }

    @Override
    public CommandResult startGame(String gameName)
    {
        
        boolean success = false;
        String message = "Game started.";
        
        try
        {
            
            mDatabaseAccess.startGame(gameName);
            success = true;
            
        }
        catch(SQLException ex)
        {
            
            message = ex.getMessage();
            
        }
        
        return new CommandResult(START_TYPE, success, message);
        
    }
    
}
