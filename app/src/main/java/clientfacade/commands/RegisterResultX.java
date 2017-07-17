package clientfacade.commands;

import clientfacade.ClientFacade;
import commandresults.CommandResult;
import interfaces.ICommand;

/**
 * Created by glowsquirrel on 7/17/17.
 */

public class RegisterResultX extends CommandResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.registerUser(super.username, null, super.message, null);
    }
}
