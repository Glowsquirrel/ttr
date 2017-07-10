package clientcommunicator;

import commands.Command;
import model.ClientModel;

/**
 *
 */
public class ClientCommunicator {
    private ClientModel clientModel = ClientModel.getMyClientModel();

    public void doCommand(Command command){
        new CommandTask(clientModel.getIp(), clientModel.getPort()).execute(command);
    }

}
