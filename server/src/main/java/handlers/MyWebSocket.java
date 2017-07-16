package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import commandresults.CommandResult;
import commandresults.LoginResult;
import interfaces.Observer;
import okio.ByteString;
import serverfacade.commands.Command;
import serverfacade.commands.ICommand;
import serverfacade.commands.JoinGameCommand;
import serverfacade.commands.LeaveGameCommand;

@WebSocket
public class MyWebSocket extends WebSocketAdapter implements Observer {
    private static HashMap<String, Session> allSessions = new HashMap<>();
    private static HashMap<String, Session> loggedInSessions = new HashMap<>();
    private static HashMap<String, HashMap<String, Session>> gameSessions = new HashMap<>();

    public static HashMap<String, Session> getLoggedInSessions() {
        return loggedInSessions;
    }

    private Session session;
    private String sessionID;
    private String username;
    private String gameName;

    private int count = 0;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
        if (this.gameName != null) {
            LeaveGameCommand kickDC = new LeaveGameCommand(this.username, this.gameName);
            kickDC.execute();
        }
        if (MyWebSocket.allSessions.containsKey(this.sessionID)) { //removes socket from map
            allSessions.remove(this.sessionID);
        }
        if (loggedInSessions.containsKey(this.username)){
            loggedInSessions.remove(this.username);
        }
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) { //this method is like a constructor
        if (!allSessions.containsKey(this.sessionID)){ //device not connected, connect and add them
            this.sessionID = Integer.toHexString(this.hashCode());
            this.session = session;
            this.session.setIdleTimeout(100000);
            System.out.println("Connecting new device id: " + this.sessionID);
            allSessions.put(this.sessionID, session);
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

        /*
        try {
            session.getRemote().sendString("Hello android! x " + count++); //send a connected message. not actually needed
        }catch (IOException ex){
            ex.printStackTrace();
        }
        */

    }

    @OnWebSocketMessage
    public void onMessage(String commandJson) {

        System.out.println("Message: " + commandJson);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Command.class, new CommandSerializer());
        Gson gson = gsonBuilder.create();

        try {
            Command command = gson.fromJson(commandJson, Command.class);
            CommandResult results = ((ICommand) command).execute();

            if (results.getType().equals("login") && results.isSuccess()){
                this.username = ((LoginResult)results).getUsername();
                loggedInSessions.put(this.username, this.session);
                String loginWorked = gson.toJson(results);
                session.getRemote().sendString(loginWorked);
            }
            else if (command.getType().equals("joingame") && results.isSuccess()){
                this.gameName = ((JoinGameCommand)command).getGameName();
            }
            else if (!results.isSuccess()){ //if command goes to model but is false, only the client that sent the bad command needs to know
                String failedResult = gson.toJson(results);
                session.getRemote().sendString(failedResult);
            }
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

    @Override
    public void update() {

    }
}
