package serverfacade.commands;

import interfaces.ICommand;
import serverfacade.ServerFacade;

public class LoginCommand extends LoginCommandData implements ICommand {

    public LoginCommand(String username, String password) {
        super(username, password);
    }

    public void execute(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.login(username, password, sessionID);
    }
}
