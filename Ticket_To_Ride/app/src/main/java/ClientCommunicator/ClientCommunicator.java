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

    public CommandResult doCommand(Command command){
        CommandResult commandResult = null;
        try {
            commandResult = new CommandTask(command, clientModel.getIp(), clientModel.getPort()).execute().get();
        }catch (ExecutionException | InterruptedException ex){
            //error
        }

        return commandResult;
    }
}
