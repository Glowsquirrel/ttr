package ServerProxy;

import ClientCommunicator.ClientCommunicator;
import ClientFacade.ClientFacade;
import CommandResults.CommandResult;
import Commands.LoginCommandData;
import Interfaces.IProxy;

public class ServerProxy implements IProxy{
    private ClientCommunicator clientCommunicator = new ClientCommunicator();
    private ClientFacade clientFacade = new ClientFacade();

    public boolean login(String username, String password){

        LoginCommandData commandData = new LoginCommandData(username, password);
        CommandResult commandResult = clientCommunicator.doCommand(commandData);

        if (commandResult.isSuccess()) {
            clientFacade.loginUser(commandResult);
            return true;
        }

        return false;
    }
    public boolean register(String username, String password){

        return true;
    }
}
