package serverfacade.commands;

import interfaces.ICommand;
import serverfacade.ServerFacade;

public class RegisterCommand extends RegisterCommandData implements ICommand {
    public RegisterCommand(String username, String password) {
        super(username, password);
    }

    public void execute(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(username, password, sessionID);
    }
}
