package clientcommunicator;

import android.os.AsyncTask;

import serverfacade.commands.Command;
import model.ClientModel;

/**
 *
 */
public class ClientCommunicator {
    private ClientModel clientModel = ClientModel.getMyClientModel();

    public void doCommand(Command command){
        new CommandTask(clientModel.getIp(), clientModel.getPort(), command).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
