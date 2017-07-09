package ClientCommunicator;

import java.util.concurrent.ExecutionException;

import CommandResults.CommandResult;
import Commands.Command;
import Model.ClientModel;

/**
 *
 */
public class ClientCommunicator {
    private ClientModel clientModel = ClientModel.getMyClientModel();

    public void doCommand(Command command){
        new CommandTask(clientModel.getIp(), clientModel.getPort()).execute(command);
    }

}
