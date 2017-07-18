package serverfacade.commands;

import interfaces.ICommand;
import serverfacade.ServerFacade;

public class RegisterCommandX extends RegisterCommand implements ICommand {
    public RegisterCommandX(String username, String password) {
        super(username, password);
    }

    public void execute(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(username, password, sessionID);
    }
}
