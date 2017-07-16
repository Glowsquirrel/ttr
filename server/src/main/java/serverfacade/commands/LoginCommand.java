package serverfacade.commands;

import commandresults.CommandResult;
import serverfacade.ServerFacade;

public class LoginCommand extends LoginCommandData implements ICommand {

    public LoginCommand(String username, String password) {
        super(username, password);
    }

    public CommandResult execute(){
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.login(username, password);
    }
}
