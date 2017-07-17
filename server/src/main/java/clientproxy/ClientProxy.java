package clientproxy;

import com.google.gson.Gson;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import commandresults.CommandResult;
import commandresults.MessageResult;
import commandresults.PollGamesResult;
import handlers.ServerWebSocket;
import interfaces.IClient;
import model.UnstartedGame;
import utils.Utils;

/**
 * The ClientProxy is the proxy on the server side that does client execution.
 * If the server calls rejectCommand, it has not accepted the command it received from a client. Calling any other
 * method in this class means that the server has accepted the command from a client.
 */
public class ClientProxy implements IClient
{
    private static Logger logger = Logger.getLogger(Utils.SERVER_LOG);
    private Gson gson = new Gson();

    /**
     * Updates all logged in client's game list. Used any time the server modifies the game list in any way.
     * @param gameList The new game list to go to all clients.
     * @param message A message from the server. If there is no message, leave null.
     */
    public void updateAllLoggedInUserGameLists(List<UnstartedGame> gameList, String message)
    {
        ConcurrentHashMap<String, Session> allLoggedInSessions = ServerWebSocket.getLoggedInSessions();
        for (Map.Entry<String, Session> sessionEntry : allLoggedInSessions.entrySet())
        {
            updateSingleUserGameList(sessionEntry.getKey(), gameList, message);
        }
    }

    /**
     * Updates a single client's game list. Will be used for when a client sends a pollgamerequest.
     * @param username
     * @param gameList
     * @param message
     */
    @Override
    public boolean updateSingleUserGameList(String username, List<UnstartedGame> gameList, String message)
    {
        CommandResult result = new PollGamesResult(gameList);
        Session mySession = ServerWebSocket.getMySession(username);
        String resultJson = gson.toJson(result);
        try
        {
            mySession.getRemote().sendString(resultJson);
            return true;
        }
        catch (IOException | WebSocketException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean loginUser(String username, String password, String message, String sessionID)
    {
        CommandResult result = new CommandResult(Utils.LOGIN_TYPE, username, null, message);
        String resultJson = gson.toJson(result);

        //Update Websocket information with the server accepted login information
        Session mySession = ServerWebSocket.getMySessionID(sessionID);
        ServerWebSocket mySocket = ServerWebSocket.getMySocket(mySession);
        mySocket.updateLoggedInSessions(username);

        try
        {
            mySession.getRemote().sendString(resultJson);
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    @Override
    public boolean registerUser(String username, String password, String message, String sessionID)
    {
        CommandResult result = new CommandResult(Utils.REGISTER_TYPE, username, null, message);
        Session mySession = ServerWebSocket.getMySessionID(sessionID);
        String resultJson = gson.toJson(result);

        try
        {
            mySession.getRemote().sendString(resultJson);
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    @Override
    public boolean startGame(String username, String gameName, String message)
    {
        CommandResult result = new CommandResult(Utils.START_TYPE, username, gameName, message);
        Session mySession = ServerWebSocket.getMySession(result.getUsername());
        String resultJson = gson.toJson(result);

        try
        {
            mySession.getRemote().sendString(resultJson);
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    @Override
    public boolean joinGame(String username, String gameName, String message)
    {
        CommandResult result = new CommandResult(Utils.JOIN_TYPE, username, gameName, message);
        String resultJson = gson.toJson(result);

        Session mySession = ServerWebSocket.getMySession(result.getUsername());
        ServerWebSocket mySocket = ServerWebSocket.getMySocket(mySession);
        mySocket.joinGameSession(username, gameName);

        try
        {
            mySession.getRemote().sendString(resultJson);
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    @Override
    public boolean leaveGame(String username, String gameName, String message)
    {
        CommandResult result = new CommandResult(Utils.LEAVE_TYPE, username, gameName, message);
        Session mySession = ServerWebSocket.getMySession(result.getUsername());
        String resultJson = gson.toJson(result);

        try
        {
            mySession.getRemote().sendString(resultJson);
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    @Override
    public boolean createGame(String username, String gameName, String message)
    {
        CommandResult result = new CommandResult(Utils.CREATE_TYPE, username, gameName, message);
        Session mySession = ServerWebSocket.getMySession(result.getUsername());
        String resultJson = gson.toJson(result);

        try
        {
            mySession.getRemote().sendString(resultJson);
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    //only used for webpage testing
    public void clearDatabase(String username, String message)
    {
        Session mySession = ServerWebSocket.getMySession(username);
        MessageResult messageResult = new MessageResult(username, message);
        String resultJson = gson.toJson(messageResult);

        try
        {
            mySession.getRemote().sendString(resultJson);
        }
        catch(IOException ex)
        {
            //
        }
    }

    public boolean rejectCommand(String identifier, String message)
    {
        MessageResult messageResult = new MessageResult(message);
        Session mySession = ServerWebSocket.getMySession(identifier);
        if (mySession == null)
            mySession = ServerWebSocket.getMySessionID(identifier);

        String resultJson = gson.toJson(messageResult);
        try
        {
            mySession.getRemote().sendString(resultJson);
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }
}
