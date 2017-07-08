package ClientCommunicator;

import android.os.AsyncTask;

import ClientFacade.ClientFacade;
import CommandResults.CommandResult;
import CommandResults.LoginResultData;
import Commands.Command;

public class CommandTask extends AsyncTask<Void, Void, CommandResult> {
    private String ip;
    private String port;
    private Command command;

    public CommandTask(Command command, String ip, String port) {
        this.ip = ip;
        this.port = port;
        this.command = command;
    }

    @Override
    protected CommandResult doInBackground(Void... params) {






        return null;
    }
    protected void onPostExecute(CommandResult result){

        ClientFacade facade = new ClientFacade();

        switch (result.getType()) {
            case "login":
                facade.loginUser(result);
                break;
        }
        /*

        new clientfacade
        facade.upateModel(result);
         */

    }
}
