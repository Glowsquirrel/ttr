package serverfacade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clientproxy.ClientProxy;
import dao.MasterDAO;;
import interfaces.IServer;
import model.Game;
import model.UnstartedGame;
import model.User;

public class ServerFacade implements IServer {
    
    //Data Members

    private ClientProxy clientProxy = new ClientProxy();
    private MasterDAO mDatabaseAccess;
    
    //Constructors

    public ServerFacade()
    {
        mDatabaseAccess = new MasterDAO();
    }
    
    //Utility Methods
    public void clearDatabase(String username) {
        boolean success = false;
        String message = null;
        
        try {
            mDatabaseAccess.clear();
            message = "Database cleared!";
            success = true;
        } catch(SQLException ex) {
            message = ex.getMessage();
        }

        if (success)
            clientProxy.clearDatabase(username, message);
        else
            clientProxy.rejectCommand(username, message);

    }
    
    private UnstartedGame convertGame(Game fromDatabase) {
        UnstartedGame convertedGame = new UnstartedGame();
        convertedGame.setName(fromDatabase.getID());
    
        List<String> names = new ArrayList<>();
    
        for(User player : fromDatabase.getPlayers()) {
            names.add(player.getUsername());
        }
    
        convertedGame.setUsernames(names);
        convertedGame.setPlayersIn(names.size());
        convertedGame.setPlayersNeeded(fromDatabase.getNumberOfPlayers());
    
        return convertedGame;
    }
    
    //Access Methods

    //TODO: ServerModel should handle all methods listed here.
    //TODO: The ServerModel should not know about any result objects.

    @Override
    public void login(String username, String password, String sessionID) {
        boolean success = false;
        String message = null;
        
        try {
            success = mDatabaseAccess.login(username, password);
            if(!success) {
                message = "Invalid password.";
            }
        } catch(SQLException ex) {
            message = ex.getMessage();
        }

        if (success) {
            clientProxy.loginUser(username, password, message, sessionID);
        } else
            clientProxy.rejectCommand(sessionID, message);
    }



    /**
     * The pollGameList method queries the SeverModel for all unstarted games and then sends them to
     * the single client that requested it via the ClientProxy.
     * @param username The identifier of the single client who should receive the data.
     */
    @Override
    public void pollGameList(String username) {
        boolean success = false;
        List<Game> openGames = null;
        String message = "";
        
        try {
            openGames = mDatabaseAccess.getOpenGames();
            success = true;
        } catch(SQLException ex) {
            message = ex.getMessage();
            openGames = new ArrayList<>();
        }
        
        List<UnstartedGame> unstartedGames = new ArrayList<>();

        for(Game openGame : openGames) {
            unstartedGames.add(convertGame(openGame));
        }

        clientProxy.updateSingleUserGameList(username, unstartedGames, message);

    }
    
    //Mutator Methods

    /**
     * The register method attempts to register a new user with the ServerModel with the given
     * parameters. The result will be sent to the single client via the ClientProxy. If unsuccessful,
     * the client which sent the command will be sent an error message via the ClientProxy.
     * @param username Unique identifier to register by.
     * @param password Password to verify identity.
     */
    @Override
    public void register(String username, String password, String sessionID) {
        boolean success = false;
        String message = "Registered as: " + username;
        
        try {
            success = mDatabaseAccess.register(username, password);
            if(!success) {
                message = "Invalid password.";
            }
        } catch(SQLException ex) {
            message = ex.getMessage();
        }

        if (success)
            clientProxy.registerUser(username, password, message, sessionID);
        else
            clientProxy.rejectCommand(sessionID, message);
    }

    /**
     * The createGame method attempts to create a new game by giving the ServerModel parameters
     * by which to create a new unstarted game. If successful, every logged in user will have their
     * game list updated via the ClientProxy. If unsuccessful, the client which sent the command will
     * be sent an error message via the ClientProxy.
     * @param username Unique identifier of which client wants to create a game.
     * @param gameName Unique identifier of the game to be created.
     * @param playerNum Number of players required for the game to start.
     */
    @Override
    public void createGame(String username, String gameName, int playerNum) {
        boolean success = false;
        String message = "Game created.";
        try {
            mDatabaseAccess.createGame(username, gameName, playerNum);
            success = true;
        } catch(SQLException ex) {
            message = ex.getMessage();
        }

        if (success) {
            //TODO: Ask ServerModel for List<UnstartedGames>. Should call same method as pollGameList.
            List<Game> openGames = null;
            try {
                openGames = mDatabaseAccess.getOpenGames();
                success = true;
            } catch(SQLException ex) {
                message = ex.getMessage();
                openGames = new ArrayList<>();
            }

            List<UnstartedGame> unstartedGames = new ArrayList<>();

            for(Game openGame : openGames) {
                unstartedGames.add(convertGame(openGame));
            }

            //client create game
            clientProxy.createGame(username, gameName, message);
            //update everyone
            clientProxy.updateAllUsersInMenus(unstartedGames, null);
        } else
            clientProxy.rejectCommand(username, message);
    }

    /**
     * The joinGame method is an attempt by a client to join an existing game. If successful, every
     * logged in user will have their game list updated via the ClientProxy. If unsuccessful, the client
     * which sent the command will be sent an error message via the ClientProxy.
     * @param username Unique identifier of which client wants to join a game.
     * @param gameName The name of the game to be joined.
     */
    @Override
    public void joinGame(String username, String gameName) {
        boolean success = false;
        String message = null;

        try {
            mDatabaseAccess.joinGame(username, gameName);
            success = true;
        } catch(SQLException ex) {
            message = "Could not join game";
        }

        if (success) {
            //TODO: Ask ServerModel for List<UnstartedGames>. Should call same method as pollGameList.
            List<Game> openGames;
            try
            {
                openGames = mDatabaseAccess.getOpenGames();
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

            //client join game
            clientProxy.joinGame(username, gameName, message);
            //update everyone
            clientProxy.updateAllUsersInMenus(unstartedGames, message);
        } else
                clientProxy.rejectCommand(username, message);
    }

    /**
     * The leaveGame method is an attempt by a client to leave a game. If successful, every logged in
     * user will have their game list updated via the ClientProxy. If unsuccessful, the client which
     * sent the command will be sent an error message via the ClientProxy.
     * @param username Unique identifier of which client wants to leave a game.
     * @param gameName Unique identifier of which game the client wants to leave.
     */
    @Override
    public void leaveGame(String username, String gameName) {
        boolean success = false;
        String message = null;
        
        try {
            mDatabaseAccess.leaveGame(username, gameName);
            success = true;
        } catch(SQLException ex) {
            message = "Could not leave game";
        }

        if (success) {
            //TODO: Ask ServerModel for List<UnstartedGames>. Should call same method as pollGameList.
            List<Game> openGames;
            try {
                openGames = mDatabaseAccess.getOpenGames();
            } catch(SQLException ex) {
                message = ex.getMessage();
                openGames = new ArrayList<>();
            }

            List<UnstartedGame> unstartedGames = new ArrayList<>();

            for(Game openGame : openGames) {
                unstartedGames.add(convertGame(openGame));
            }

            //client leave game
            clientProxy.leaveGame(username, gameName, message);
            //update everyone
            clientProxy.updateAllUsersInMenus(unstartedGames, message);
        }
    }

    /**
     * The startGame method is an attempt by a client to start the game identified by the given string.
     * If successful, every logged in user will have their game list updated via the ClientProxy. Each
     * client that is in the current game will be started along with the client who sent the request. If
     * unsuccessful, the client which sent the command will be sent an error message via the ClientProxy.
     * @param gameName Unique identifier of the game to be started.
     * @param username Unique identifier of the client which wants to start the game.
     */
    @Override
    public void startGame(String gameName, String username) {
        boolean success = false;
        String message = null;
        
        try {
            mDatabaseAccess.startGame(gameName);
            success = true;
        } catch(SQLException ex) {
            message = ex.getMessage();
        }

        if (success) {
            //TODO: Ask ServerModel for List<UnstartedGames>. Should call same method as pollGameList.
            List<Game> openGames;
            try {
                openGames = mDatabaseAccess.getOpenGames();
            } catch(SQLException ex) {
                message = ex.getMessage();
                openGames = new ArrayList<>();
            }

            List<UnstartedGame> unstartedGames = new ArrayList<>();

            for(Game openGame : openGames) {
                unstartedGames.add(convertGame(openGame));
            }

            clientProxy.startGame(username, gameName, message);
            clientProxy.updateAllUsersInMenus(unstartedGames, message);
        } else
            clientProxy.rejectCommand(username, message);
    }
    
}
