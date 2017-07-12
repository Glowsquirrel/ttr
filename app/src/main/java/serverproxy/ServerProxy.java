package serverproxy;

import clientcommunicator.ClientCommunicator;
import clientfacade.ClientFacade;
import commandresults.CommandResult;
import commandresults.LoginResult;
import commandresults.PollGamesResult;
import serverfacade.commands.CreateGameCommandData;
import serverfacade.commands.LoginCommandData;
import interfaces.IServer;

//TODO implement methods here...

/**
 * The ServerProxy is the proxy on the client side that lets the server do the actual execution.
 */
public class ServerProxy implements IServer{
    private ClientCommunicator clientCommunicator = new ClientCommunicator();
    private ClientFacade clientFacade = new ClientFacade();

    @Override
    public LoginResult login(String username, String password){

        LoginCommandData commandData = new LoginCommandData(username, password);
        clientCommunicator.doCommand(commandData);
        return null;
    }

    @Override
    public CommandResult register(String username, String password) {
        return null;
    }

    @Override
    public PollGamesResult pollGameList(String username) {
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
        return null;
    }

    @Override
    public CommandResult leaveGame(String username, String gameName) {
        return null;
    }

    @Override
    public CommandResult startGame(String gameName) {
        return null;
    }
}
