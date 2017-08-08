package serverfacade.commands.menu;

import commands.menu.RegisterCommand;
import interfaces.ICommandX;
import serverfacade.ServerFacade;

public class RegisterCommandX extends RegisterCommand implements ICommandX {
    private ServerFacade serverFacade;

    public RegisterCommandX(String username, String password) {
        super(username, password);
    }

    public boolean execute(){
        serverFacade = new ServerFacade();
        return serverFacade.register(username, password, sessionID);
    }

    @Override
    public void addToDatabase() {
        serverFacade.addUserToDatabase(super.username);
    }
}
