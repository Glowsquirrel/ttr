package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import commandresults.CommandResult;
import serverfacade.commands.Command;
import serverfacade.commands.ICommand;
import serverfacade.commands.LeaveGameCommand;

@WebSocket
public class ServerWebSocket
{
    //for keeping track of all connections
    private static ConcurrentHashMap<String, Session> allSessions = new ConcurrentHashMap<>();

    //for keeping track of who to update when the game list changes: <username, session>
    private static ConcurrentHashMap<String, Session> loggedInSessions = new ConcurrentHashMap<>();

    //for keeping track of individual games to update: <gameName, <username, session>>
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> gameSessions = new ConcurrentHashMap<>();

    //for referencing the ServerWebSocket by using a session
    private static ConcurrentHashMap<Session, ServerWebSocket> myServerWebSockets = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Session> getLoggedInSessions()
    {
        return loggedInSessions;
    }

    public static ConcurrentHashMap<String, Session> getGameSession(String gameName)
    {
        return gameSessions.get(gameName);
    }

    public static Session getMySession(String username)
    {
        return loggedInSessions.get(username);
    }

    public static ServerWebSocket getMySocket(Session mySession)
    {
        return myServerWebSockets.get(mySession);
    }

    public void updateLoggedInSessions(String username)
    {
        this.username = username;
        loggedInSessions.put(username, this.session);
    }

    public void joinGameSession(String username, String gameName)
    {
        this.username = username;
        this.gameName = gameName;
        if (gameSessions.containsKey(gameName)) //if the gameName already exists
        {
            ConcurrentHashMap<String, Session> myGameSession = ServerWebSocket.getGameSession(gameName);
            myGameSession.put(this.username, this.session);
        }
        else //create a new hashmap for that game
        {
            ConcurrentHashMap<String, Session> myGameSession = new ConcurrentHashMap<>();
            myGameSession.put(this.username, this.session);

        }
    }

    public void leaveGameSession(String username, String gameName)
    {
        this.gameName = null; //set to null to check for logic errors

    }

    private Session session;
    private String sessionID;
    private String username;
    private String gameName;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        System.out.println("Close: statusCode = " + statusCode + ", reason = " + reason);
        LeaveGameCommand kickDC = new LeaveGameCommand(this.username, this.gameName);
        kickDC.execute();
        //removes session from master list & socket list
        if (ServerWebSocket.allSessions.containsKey(this.sessionID))
        {
            allSessions.remove(this.sessionID);
            myServerWebSockets.remove(this.session);
        }
        //removes session from logged in list
        if (loggedInSessions.containsKey(this.username))
        {
            loggedInSessions.remove(this.username);
        }
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) { //this method is like a constructor
        this.sessionID = Integer.toHexString(this.hashCode());
        this.session = session;
        this.session.setIdleTimeout(600000); //timeout occurs if no communication for 10m (may be changed later)
        if (!allSessions.containsKey(this.sessionID)){ //device not connected, connect and add them
            System.out.println("Connecting new device id: " + this.sessionID);
            allSessions.put(this.sessionID, session);
            myServerWebSockets.put(this.session, this);
        }
        else {
            try {
                System.out.println("ALREADY CONNECTED");
                session.disconnect();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }

        System.out.println("Connect: " + session.getRemoteAddress().getAddress());
    }

    @OnWebSocketMessage
    public void onMessage(String message) {

        System.out.println("Message: " + message);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Command.class, new CommandSerializer());
        Gson gson = gsonBuilder.create();

        try {
            Command command = gson.fromJson(message, Command.class);
            ((ICommand) command).execute();




            /*
            The following code is only here temporary:

            CommandResult results = ((ICommand) command).execute();
            // A null result means the command failed
            if (results == null)
                return;

            //REQUIRED!! Adds the user's session into logged in user list.
            if (results.getType().equals(Utils.LOGIN_TYPE)){
                this.username = ((LoginResult)results).getUsername();
                loggedInSessions.put(this.username, this.session);
                String loginWorked = gson.toJson(results);
                session.getRemote().sendString(loginWorked);
            }
            //REQUIRED!! Adds the user's session to a specific game.
            else if (command.getType().equals(Utils.JOIN_TYPE)){
                this.gameName = ((JoinGameCommand)command).getGameName();
            }
            //REQUIRED!! Removes the user's session from a specific game
            else if (command.getType().equals(Utils.LEAVE_TYPE)){

            }
            //Unsure if required. Might have a started game Session list?
            else if (command.getType().equals(Utils.START_TYPE)){

            }

            //send the single client that asked its PollGamesResult
            else if (command.getType().equals("pollgames") && results.isSuccess()){
                String pollGameList = gson.toJson(results);
                session.getRemote().sendString(pollGameList);
            }

            //handle failed commands
            else if (!results.isSuccess()){ //if command goes to model but is false, only the client that sent the bad command needs to know
                String failedResult = gson.toJson(results);
                session.getRemote().sendString(failedResult);
            }
            */

        }catch (Exception ex){
            ex.printStackTrace();
            try {
                session.getRemote().sendString("Illegal request or bad gateway! Disconnecting from server.");
                session.disconnect();
            }catch (IOException ioex){
                ioex.printStackTrace();
            }
        }
    }
}
