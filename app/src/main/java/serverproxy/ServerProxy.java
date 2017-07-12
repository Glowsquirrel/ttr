package serverproxy;

import clientcommunicator.ClientCommunicator;
import clientfacade.ClientFacade;
import commandresults.CommandResult;
import commandresults.LoginResult;
import commandresults.PollGamesResult;
import commandresults.RegisterResult;
import commands.Command;
import commands.CreateGameCommandData;
import commands.JoinGameCommandData;
import commands.LeaveGameCommandData;
import commands.LoginCommandData;
import commands.RegisterCommandData;
import commands.StartGameCommandData;
import interfaces.IServer;

//TODO implement methods here...

/**
 * The ServerProxy is the proxy on the client side that lets the server do the actual execution.
 * On the client side it will start an AsyncTask and return null.
 */
public class ServerProxy implements IServer {
    private ClientCommunicator clientCommunicator = new ClientCommunicator();
    private ClientFacade clientFacade = new ClientFacade();

    @Override
    public LoginResult login(String username, String password) {
        Command commandData = new LoginCommandData(username, password);
        clientCommunicator.doCommand(commandData);
        return null; //
    }

    @Override
    public RegisterResult register(String username, String password) {
        Command commandData = new RegisterCommandData(username, password);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    @Override
    public PollGamesResult pollGameList(String username) { //this function unused on client
        return null;
    }

    @Override
    public CommandResult createGame(String username, String gameName, int numPlayers) {
        Command commandData = new CreateGameCommandData(username, gameName, numPlayers);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    @Override
    public CommandResult joinGame(String username, String gameName) {
        Command commandData = new JoinGameCommandData(username, gameName);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    @Override
    public CommandResult leaveGame(String username, String gameName) {
        Command commandData = new LeaveGameCommandData(username, gameName);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    @Override
    public CommandResult startGame(String gameName) {
        Command commandData = new StartGameCommandData(gameName);
        clientCommunicator.doCommand(commandData);
        return null;
    }
}
