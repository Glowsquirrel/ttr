package serverproxy;

import clientcommunicator.ClientCommunicator;
import serverfacade.commands.CreateGameCommand;
import serverfacade.commands.JoinGameCommand;
import serverfacade.commands.LeaveGameCommand;
import serverfacade.commands.LoginCommand;
import interfaces.IServer;
import serverfacade.commands.PollGamesCommand;
import serverfacade.commands.RegisterCommand;
import serverfacade.commands.StartGameCommand;

/**
 * The ServerProxy is the proxy on the client side that lets the server do the actual execution.
 */
public class ServerProxy implements IServer{
    private ClientCommunicator clientCommunicator = new ClientCommunicator();

    @Override
    public void login(String username, String password, String sessionID){
        LoginCommand commandData = new LoginCommand(username, password);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void register(String username, String password, String sessionID) {
        RegisterCommand commandData = new RegisterCommand(username, password);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void pollGameList(String username) {
        PollGamesCommand commandData = new PollGamesCommand(username);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void createGame(String username, String gameName, int playerNum) {
        CreateGameCommand commandData = new CreateGameCommand(username, gameName, playerNum);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void joinGame(String username, String gameName) {
        JoinGameCommand commandData = new JoinGameCommand(username, gameName);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void leaveGame(String username, String gameName) {
        LeaveGameCommand commandData = new LeaveGameCommand(username, gameName);
        clientCommunicator.doCommand(commandData);
    }

    @Override
    public void startGame(String username, String gameName) {
        StartGameCommand commandData = new StartGameCommand(username, gameName);
        clientCommunicator.doCommand(commandData);
    }

}
