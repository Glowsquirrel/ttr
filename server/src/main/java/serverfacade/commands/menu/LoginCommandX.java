package serverfacade.commands.menu;

import commands.menu.LoginCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class LoginCommandX extends LoginCommand implements ICommand {

    public LoginCommandX(String username, String password) {
        super(username, password);
    }

    public void execute(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.login(username, password, sessionID);
    }
}
