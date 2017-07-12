package serverfacade.commands;

import commandresults.CommandResult;
import serverfacade.ServerFacade;

public class RegisterCommand extends RegisterCommandData implements ICommand{
    public RegisterCommand(String username, String password) {
        super(username, password);
    }

    public CommandResult execute(){
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.register(username, password);
    }
}
