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

    @Override
    public void login(String username, String password, String sessionID){
        LoginCommandData commandData = new LoginCommandData(username, password);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void register(String username, String password, String sessionID) {
        RegisterCommandData commandData = new RegisterCommandData(username, password);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void pollGameList(String username) {
        PollGamesCommandData commandData = new PollGamesCommandData(username);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void createGame(String username, String gameName, int playerNum) {
        CreateGameCommandData commandData = new CreateGameCommandData(username, gameName, playerNum);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void joinGame(String username, String gameName) {
        JoinGameCommandData commandData = new JoinGameCommandData(username, gameName);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void leaveGame(String username, String gameName) {
        LeaveGameCommandData commandData = new LeaveGameCommandData(username, gameName);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void startGame(String username, String gameName) {
        StartGameCommandData commandData = new StartGameCommandData(username, gameName);
        clientCommunicator.doCommand(commandData);
    }

}
