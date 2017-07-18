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

import serverfacade.ServerFacade;
import serverfacade.commands.Command;
import interfaces.ICommand;
import serverfacade.commands.LeaveGameCommandX;

@WebSocket
public class ServerWebSocket
{
    //for keeping track of all connections
    private static ConcurrentHashMap<String, Session> allSessions = new ConcurrentHashMap<>();

    //for keeping track of who to update when the game list changes: <username, session>
    private static ConcurrentHashMap<String, Session> allMenuSessions = new ConcurrentHashMap<>();

    //for keeping track of individual games to update: <gameName, <username, session>>
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> gameSessions = new ConcurrentHashMap<>();

    //for referencing the ServerWebSocket by using a session
    private static ConcurrentHashMap<Session, ServerWebSocket> myServerWebSockets = new ConcurrentHashMap<>();

    public static Session getMySessionID(String sessionID)
    {
        return allSessions.get(sessionID);
    }

    public static ConcurrentHashMap<String, Session> getAllMenuSessions()
    {
        return allMenuSessions;
    }

    public static ConcurrentHashMap<String, Session> getGameSession(String gameName)
    {
        return gameSessions.get(gameName);
    }

    public static Session getMySession(String username)
    {
        return allMenuSessions.get(username);
    }

    public static ServerWebSocket getMySocket(Session mySession)
    {
        return myServerWebSockets.get(mySession);
    }

    public void updateMenuSessions(String username)
    {
        this.username = username;
        allMenuSessions.put(username, this.session);
    }

    public void joinGameSession(String username, String gameName) {
        this.username = username;
        this.gameName = gameName;
        if (gameSessions.containsKey(gameName)) { //if the gameName already exists
            ConcurrentHashMap<String, Session> myGameSession = ServerWebSocket.getGameSession(gameName);
            myGameSession.put(this.username, this.session);
        } else { //create a new hashmap for that game
            ConcurrentHashMap<String, Session> myGameSession = new ConcurrentHashMap<>();
            myGameSession.put(this.username, this.session);
            gameSessions.put(gameName, myGameSession);
        }
    }

    public void leaveGameSession(String username, String gameName)
    {
        this.gameName = null; //set to null to check for logic errors

    }

    private String sessionID;
    private Session session;
    private String username;
    private String gameName;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        synchronized (ServerFacade.class) {
            System.out.println("Close: statusCode = " + statusCode + ", reason = " + reason);
            LeaveGameCommandX kickDC = new LeaveGameCommandX(this.username, this.gameName);
            kickDC.execute();
            //removes session from master list & socket list
            if (ServerWebSocket.allSessions.containsKey(this.sessionID)) {
                allSessions.remove(this.sessionID);
                myServerWebSockets.remove(this.session);
            }
            //removes session from logged in list & game if in one
            if (this.username != null) {
                if (allMenuSessions.containsKey(this.username)) {
                    allMenuSessions.remove(this.username);
                }
                if (this.gameName != null) {
                    ConcurrentHashMap<String, Session> myGameSession = gameSessions.get(gameName);
                    if (myGameSession.containsKey(this.username)) {
                        myGameSession.remove(this.username);
                    }
                }
            }
        }
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }

    /**
     * The onConnect method for websockets is similar to a constructor as it is called at the creation
     * of a new thread that will handle the connection. Each client is only allowed one websocket
     * connection at a time.
     * @param session The Session used to identify the connected client.
     */
    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.sessionID = Integer.toHexString(this.hashCode());
        this.session = session;
        this.session.setIdleTimeout(600000); //timeout occurs if no communication for 10m (may be changed later)

        if (!allSessions.containsKey(this.sessionID)){ //if device not connected, connect and add them
            System.out.println("Connecting new device id: " + this.sessionID);
            allSessions.put(this.sessionID, session);
            myServerWebSockets.put(this.session, this);
        } else {
            try {
                System.out.println("ALREADY CONNECTED");
                session.disconnect();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("Connecting: " + session.getRemoteAddress().getAddress());
    }

    @OnWebSocketMessage
    public void onMessage(String message)
    {

        System.out.println("Message: " + message);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Command.class, new CommandSerializer());
        Gson gson = gsonBuilder.create();

        synchronized (ServerFacade.class) {
            try {
                Command command = gson.fromJson(message, Command.class);
                command.setSessionID(this.sessionID);
                ((ICommand) command).execute();
            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    session.getRemote().sendString("Illegal request! The server has kicked you.");
                    session.disconnect();
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                }
            }
        }
    }
}
