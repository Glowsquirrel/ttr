package serverproxy;

import clientcommunicator.ClientCommunicator;
import clientfacade.ClientFacade;
import commands.LoginCommandData;
import interfaces.IProxy;

//TODO implement methods here...

/**
 * The ServerProxy is the proxy on the client side that lets the server do the actual execution.
 */
public class ServerProxy implements IProxy{
    private ClientCommunicator clientCommunicator = new ClientCommunicator();
    private ClientFacade clientFacade = new ClientFacade();

    public void login(String username, String password){

        LoginCommandData commandData = new LoginCommandData(username, password);
        clientCommunicator.doCommand(commandData);

    }
    public boolean register(String username, String password){
        return true;
    }

    @Override
    public void pollGameList(String username) {

    }

    @Override
    public void createGame(String username, String gameName, int playerNum) {

    }

    @Override
    public void joinGame(String username, String gameName) {

    }

    @Override
    public void leaveGame(String username) {

    }

    @Override
    public void startGame(String username) {

    }
}
