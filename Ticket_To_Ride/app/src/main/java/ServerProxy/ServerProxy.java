package ServerProxy;

import ClientCommunicator.ClientCommunicator;
import ClientFacade.ClientFacade;
import CommandResults.CommandResult;
import Commands.LoginCommandData;
import Interfaces.IProxy;

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
}
