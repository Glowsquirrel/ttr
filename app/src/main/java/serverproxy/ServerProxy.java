package serverproxy;

import clientcommunicator.ClientCommunicator;
import commandresults.CommandResult;
import commandresults.LoginResult;
import commandresults.PollGamesResult;
import serverfacade.commands.CreateGameCommandData;
import serverfacade.commands.JoinGameCommandData;
import serverfacade.commands.LeaveGameCommandData;
import serverfacade.commands.LoginCommandData;
import interfaces.IServer;
import serverfacade.commands.PollGamesCommandData;
import serverfacade.commands.RegisterCommandData;
import serverfacade.commands.StartGameCommandData;
import websocket.ClientWebSocket;

/**
 * The ServerProxy is the proxy on the client side that lets the server do the actual execution.
 */
public class ServerProxy implements IServer{
    private ClientCommunicator clientCommunicator = new ClientCommunicator();
    private ClientWebSocket webSocket = ClientWebSocket.getClientWebSocket();

    @Override
    public LoginResult login(String username, String password){
        LoginCommandData commandData = new LoginCommandData(username, password);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    @Override
    public CommandResult register(String username, String password) {
        RegisterCommandData commandData = new RegisterCommandData(username, password);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    @Override
    public PollGamesResult pollGameList(String username) {
        PollGamesCommandData commandData = new PollGamesCommandData(username);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    @Override
    public CommandResult createGame(String username, String gameName, int playerNum) {
        CreateGameCommandData commandData = new CreateGameCommandData(username, gameName, playerNum);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    @Override
    public CommandResult joinGame(String username, String gameName) {
        JoinGameCommandData commandData = new JoinGameCommandData(username, gameName);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    @Override
    public CommandResult leaveGame(String username, String gameName) {
        LeaveGameCommandData commandData = new LeaveGameCommandData(username, gameName);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    @Override
    public CommandResult startGame(String gameName, String username) {
        StartGameCommandData commandData = new StartGameCommandData(gameName);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    public void createWebSocket(String username, String gameName){

    }
}
